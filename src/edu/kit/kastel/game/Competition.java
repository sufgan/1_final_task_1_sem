package edu.kit.kastel.game;

import edu.kit.kastel.Application;
import edu.kit.kastel.game.actions.Action;
import edu.kit.kastel.game.actions.EffectQueue;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.monsters.MonsterSample;
import edu.kit.kastel.utils.Utility;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

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
    private static final String MONSTER_TABLE_FORMAT = "[%s%s] %d %s%s (%s)%n";
    private static final String ENTER_COMPETITION_FORMAT = "The %d monsters enter the competition!%n";
    private static final String ACTION_NEED_TARGET_MESSAGE = "this action need target monster.";
    private static final String MONSTER_NOT_FOUND_FORMAT = "monster %s wasn't found.";
    private static final String MONSTER_HEALTH_SIGN = "X";
    private static final String MONSTER_EMPTY_HEALTH_SIGN = "_";
    private static final String CURRENT_MONSTER_SIGN = "*";
    private static final int HEALTH_BAR_LENGTH = 20;


    private final List<Monster> monsters;
    private final List<EffectQueue> selectedActions;

    private int currentMonsterIndex = 0;

    /**
     * Creates a new {@code Competition} and initializes monsters from the provided samples.
     *
     * @param monstersSamples the list of monster samples to instantiate
     */
    public Competition(List<MonsterSample> monstersSamples) {
        Application.DEFAULT_OUTPUT_STREAM.printf(ENTER_COMPETITION_FORMAT, monstersSamples.size());
        monsters = new ArrayList<>();
        selectedActions = new LinkedList<>();
        MonsterSample.clearCreatedCounts();
        for (MonsterSample ms : monstersSamples) {
            Monster monster = ms.create();
            monsters.add(monster);
        }
    }

    /**
     * Selects an action for the current monster and adds it to the action queue.
     * The action may optionally require a target monster.
     *
     * @param action              the {@link Action} to be performed by the current monster
     * @param targetMonsterName   the name of the target monster, required if the action needs a target
     * @throws GameRuntimeException if the action requires a target but the target monster cannot be found
     */
    public void selectAction(Action action, String targetMonsterName) throws GameRuntimeException {
        Monster user = getCurrentMonster();
        Monster target = action.needTarget() ? selectTarget(user.getName(), targetMonsterName) : null;
        selectedActions.add(new EffectQueue(user, target, action));
        step();
    }

    private Monster selectTarget(String userMonsterName, String targetMonsterName) throws GameRuntimeException {
        if (targetMonsterName != null) {
            return findMonster(targetMonsterName);
        }
        List<Monster> aliveMonsters = getAliveMonsters(userMonsterName);
        if (aliveMonsters.size() == 1) {
            return aliveMonsters.get(0);
        }
        throw new GameRuntimeException(ACTION_NEED_TARGET_MESSAGE);
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
        Collections.sort(selectedActions);
        for (EffectQueue effectQueue : selectedActions) {
            effectQueue.apply();
        }
        selectedActions.clear();
    }

    private void updateProtections() {
        List<Monster> monsters = getAliveMonsters();
        Collections.sort(monsters);
        for (Monster monster : monsters) {
            monster.updateProtection();
        }
    }

    /**
     * Retrieves a list of all currently alive monsters, excluding those whose names are specified
     * in the provided parameter.
     *
     * @param exceptMonsterNames the names of monsters to be excluded from the result list
     * @return a list of {@link Monster} objects that are alive and not in the excluded list
     */
    public List<Monster> getAliveMonsters(String... exceptMonsterNames) {
        List<Monster> result = new LinkedList<>();
        List<String> exceptMonsterNamesList = List.of(exceptMonsterNames);
        for (Monster monster : monsters) {
            if (!monster.isFainted() && !exceptMonsterNamesList.contains(monster.getName())) {
                result.add(monster);
            }
        }
        return result;
    }

    /**
     * Finds a monster by its name from a predefined list of monsters.
     *
     * @param monsterName the name of the monster to be searched
     * @return the {@link Monster} with the specified name if found
     * @throws GameRuntimeException if no monster with the specified name is found
     */
    public Monster findMonster(String monsterName) throws GameRuntimeException {
        for (Monster monster : monsters) {
            if (monster.getName().equals(monsterName)) {
                return monster;
            }
        }
        throw new GameRuntimeException(MONSTER_NOT_FOUND_FORMAT.formatted(monsterName));
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
            int healthCount = Utility.ceilDiv(HEALTH_BAR_LENGTH * monster.getHealth(), monster.getSample().getMaxHealth());
            Application.DEFAULT_OUTPUT_STREAM.printf(MONSTER_TABLE_FORMAT,
                    MONSTER_HEALTH_SIGN.repeat(healthCount),
                    MONSTER_EMPTY_HEALTH_SIGN.repeat(HEALTH_BAR_LENGTH - healthCount),
                    i + 1,
                    i == currentMonsterIndex ? CURRENT_MONSTER_SIGN : "",
                    monster.getName(),
                    monster.getStatus()
            );
            i++;
        }
    }

}
