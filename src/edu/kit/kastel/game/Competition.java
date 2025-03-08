package edu.kit.kastel.game;

import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.actions.EffectQueue;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.utils.Utility;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Manages a competition among multiple monsters.
 * <p>
 * Provides methods to select actions for the current monster,
 * apply actions in turn order, and maintain states (e.g. health, protections).
 * </p>
 *
 * @author uyqbd
 */
public class Competition {
    private static final String MONSTERS_TURN_FORMAT = "%nIt's %s's turn.%n";
    private static final String MONSTER_TABLE_FORMAT = "[%s%s] %d %s%s (%s)%n";
    private static final String MONSTER_HEALTH_SIGN = "X";
    private static final String MONSTER_EMPTY_HEALTH_SIGN = "_";


    private final List<Monster> monsters;
//    private final List<EffectQueue> effectQueues;
    private final Map<Monster, EffectQueue> selectedActions;

    private int currentMonsterIndex = 0;

    /**
     * Creates a new {@code Competition} and initializes monsters from the provided samples.
     *
     * @param monstersSamples the list of monster samples to instantiate
     */
    public Competition(List<MonsterSample> monstersSamples) {
        System.out.printf("The %d monsters enter the competition!%n", monstersSamples.size());
//        effectQueues = new LinkedList<>();
        monsters = new ArrayList<>();
        selectedActions = new TreeMap<>();
        MonsterSample.clearCreatedCounts();
        for (MonsterSample ms : monstersSamples) {
            Monster monster = ms.create();
            monsters.add(monster);
        }
    }

    /**
     * Selects an action for the current monster, specifying an optional target.
     * <p>Automatically proceeds to the next monster.</p>
     *
     * @param action           the {@link Action} to be performed
     * @param targetMonsterName the name of the target monster (if required)
     * @throws GameRuntimeException if action need the monster and it was not given
     */
    public void selectAction(Action action, String targetMonsterName) throws GameRuntimeException {
        Monster user = getCurrentMonster();
        Monster target = findMonster(targetMonsterName);
        if (action.needTarget() && targetMonsterName == null) {
            List<Monster> aliveMonsters = getAliveMonsters();
            if (aliveMonsters.size() > 2) {
                throw new GameRuntimeException("this action need target monster");
            }
            aliveMonsters.remove(user); // ???
            target = aliveMonsters.get(0);
        }
        selectedActions.put(getCurrentMonster(), new EffectQueue(user, target, action));
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
            selectedActions.clear();
        }
    }

    private void applyActions() {
        for (Map.Entry<Monster, EffectQueue> entry : selectedActions.entrySet()) {
            if (!entry.getValue().getUser().isFainted()) {
                System.out.printf(MONSTERS_TURN_FORMAT, entry.getKey().getName());
                entry.getValue().apply();
            }
        }
    }

    private void updateProtections() {
        for (Monster monster : selectedActions.keySet()) {
            if (!monster.isFainted()) {
                monster.updateProtection();
            }
        }
    }

    /**
     * Retrieves a list of all monsters that are not fainted.
     *
     * @return a list of alive monsters
     */
    public List<Monster> getAliveMonsters() {
        List<Monster> result = new LinkedList<>();
        for (Monster monster : monsters) {
            if (!monster.isFainted()) {
                result.add(monster);
            }
        }
        return result;
    }

    /**
     * Searches for a monster by name in the competition.
     *
     * @param monsterName the name to match
     * @return the matching {@link Monster}, or {@code null} if not found
     */
    public Monster findMonster(String monsterName) {
        for (Monster monster : monsters) {
            if (monster.getName().equals(monsterName)) {
                return monster;
            }
        }
        return null;
    }

    /**
     * Retrieves the monster whose turn it currently is.
     *
     * @return the {@link Monster} at the current index
     */
    public Monster getCurrentMonster() {
        return monsters.get(currentMonsterIndex);
    }

    /**
     * Prints a summary of all monsters in the competition,
     * including their current health and status.
     */
    public void printMonsters() {
        int i = 0;
        for (Monster monster : monsters) {
            int healthCount = Utility.ceilDiv(20 * monster.getHealth(), monster.getSample().getMaxHealth());
            System.out.printf(MONSTER_TABLE_FORMAT,
                    MONSTER_HEALTH_SIGN.repeat(healthCount),
                    MONSTER_EMPTY_HEALTH_SIGN.repeat(20 - healthCount),
                    i + 1,
                    i == currentMonsterIndex ? "*" : "",
                    monster.getName(),
                    monster.getStatus()
            );
            i++;
        }
    }

}
