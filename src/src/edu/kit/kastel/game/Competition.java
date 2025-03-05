package edu.kit.kastel.game;

import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.actions.EffectQueue;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.monsters.MonsterSample;

import java.util.*;

public class Competition {
    private static final String MONSTER_DONT_HAVE_ACTION_MESSAGE_FORMAT = "%s doesn't have this action%n";

    private final List<Monster> monsters;
    private final List<EffectQueue> effectQueues;

    private int currentMonsterIndex = 0;

    public Competition(List<MonsterSample> monstersSamples) {
        System.out.printf("The %d monsters enter the competition!%n", monstersSamples.size());
        effectQueues = new LinkedList<>(); // TODO: add comparator, вроде сделано
        monsters = new ArrayList<>();
        MonsterSample.clearCreatedCounts();
        for (MonsterSample ms : monstersSamples) {
            Monster monster = ms.create();
            monsters.add(monster);
        }
    }

    public void selectAction(Action action) {
        selectAction(action, null);
    }

    public void selectAction(Action action, String targetMonsterName) {
        Monster targetMonster = findMonster(targetMonsterName);
        if (action.needTarget() && targetMonsterName == null) {
            List<Monster> aliveMonsters = getAliveMonsters();
            if (aliveMonsters.size() > 2) {
                throw new GameRuntimeException("this action need target monster");
            }
            aliveMonsters.remove(getCurrentMonster());
            targetMonster = aliveMonsters.get(0);
        }
        effectQueues.add(action.createEffectsQueue(monsters.get(currentMonsterIndex), targetMonster));
        step();
    }

    private void step() {
        int lastMonsterIndex = currentMonsterIndex;
        do {
            currentMonsterIndex = (currentMonsterIndex + 1) % monsters.size();
        } while (monsters.get(currentMonsterIndex).isFainted());
        if (currentMonsterIndex < lastMonsterIndex) {
            applyActions();
            updateProtections();
        }
    }

    private void applyActions() {
        Collections.sort(effectQueues);
        for (EffectQueue effectQueue : effectQueues) {
            if (!effectQueue.getUser().isFainted()) {
                System.out.printf("%nIt’s %s’s turn.%n", effectQueue.getUser().getName());
                effectQueue.getUser().updateCondition();
                effectQueue.apply();
            }
        }
        effectQueues.clear();
    }

    private void updateProtections() {
        for (Monster monster : monsters) {
            monster.updateProtection();
        }
    }

    public List<Monster> getAliveMonsters() {
        List<Monster> result = new LinkedList<>();
        for (Monster monster : monsters) {
            if (!monster.isFainted()) {
                result.add(monster);
            }
        }
        return result;
    }

    public Monster findMonster(String monsterName) {
        for (Monster monster : monsters) {
            if (monster.getName().equals(monsterName)) {
                return monster;
            }
        }
        return null;
    }

    public Monster getCurrentMonster() {
        return monsters.get(currentMonsterIndex);
    }

    public void printMonsters() {
        int i = 0;
        for (Monster monster : monsters) {
            int healthCount = -Math.floorDiv(20 * -monster.getHealth(), monster.getSample().getMaxHealth());
            System.out.printf("[%s%s] %d %s%s (%s)%n",
                    "#".repeat(healthCount),
                    "_".repeat(20 - healthCount),
                    i + 1,
                    i == currentMonsterIndex ? "*" : "",
                    monster.getName(),
                    monster.getStatus()
            );
            i++;
        }
    }

}
