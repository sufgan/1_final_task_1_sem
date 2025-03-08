package edu.kit.kastel.game.monsters;

import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.game.types.Condition;
import edu.kit.kastel.game.types.Protection;
import edu.kit.kastel.game.actions.effects.ProtectionType;
import edu.kit.kastel.utils.Utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Represents an active monster with modifiable health, stats, conditions, and protections.
 * @author uyqbd
 */
public class Monster {
    private final MonsterSample sample;
    private final Map<StatType,  Integer> scales;
    private final int index;

    private Condition condition;
    private Protection protection;
    private int health;

    /**
     * Creates a new monster instance based on a given sample and index.
     *
     * @param sample the monster template
     * @param index  the instance number if multiple monsters of the same type exist
     */
    public Monster(MonsterSample sample, int index) {
        this.sample = sample;
        scales = new HashMap<>();
        this.health = sample.getMaxHealth();
        this.index = index;
    }

    /**
     * Modifies the scaling value of a specific stat, within [-5, 5].
     *
     * @param stat  the stat to change
     * @param shift the amount to add (positive or negative)
     */
    public void shiftScale(StatType stat, int shift) {
        scales.put(stat, Utility.limitValue(scales.getOrDefault(stat, 0) + shift, -5, 5));
    }

    /**
     * Adjusts the monster's health by a certain amount, respecting min (0) and max (sample's max health).
     *
     * @param shift the amount to add (positive or negative)
     */
    public void shiftHealth(int shift) {
        health = Utility.limitValue(health + shift, 0, sample.getMaxHealth());
    }

    /**
     * Retrieves the underlying monster sample from which this monster was created.
     *
     * @return the associated {@link MonsterSample}
     */
    public MonsterSample getSample() {
        return sample;
    }

    /**
     * Checks if the monster's health is at 0.
     *
     * @return {@code true} if fainted, {@code false} otherwise
     */
    public boolean isFainted() {
        return health == 0;
    }

    /**
     * Returns the monster's effective stat value, considering scaling and any condition effects.
     *
     * @param stat the stat to retrieve (e.g., ATK, DEF)
     * @return the modified stat value as a double
     */
    public double getStat(StatType stat) {
        double conditionFactor = condition == null ? 1 : condition.getStateFactor(stat);
        return Utility.scaleStat(stat, sample.getStat(stat), scales.getOrDefault(stat, 0)) * conditionFactor;
    }

    /**
     * Gets the current health of the monster.
     *
     * @return the current health as an integer
     */
    public int getHealth() {
        return health;
    }

    /**
     * Advances the monster's status condition by one turn or removes it if it finishes.
     */
    public void updateCondition() {
        Condition lastCondition = condition;
        if (condition != null) {
            condition = condition.step();
            System.out.printf(
                    lastCondition.getMessage(lastCondition != condition ? Condition.FINISHING_MESSAGE : Condition.EXISTING_MESSAGE),
                    sample.getName()
            );
        }
    }

    /**
     * Sets the monster's condition to a new value.
     *
     * @param condition a {@link Condition} (e.g., POISON, SLEEP)
     */
    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    /**
     * Retrieves the monster's current condition, if any.
     *
     * @return the active {@link Condition}, or {@code null} if none is set
     */
    public Condition getCondition() {
        return condition;
    }

    /**
     * Advances and potentially removes any active protection.
     */
    public void updateProtection() {
        if (protection != null) {
            if (protection.step() == null) {
                protection = null;
                System.out.printf("%s's protection fades away...%n", getName());
            }
        }
    }

    /**
     * Sets a protection type for a certain duration.
     *
     * @param type     the type of protection (e.g., HEALTH, STATS)
     * @param duration how many turns the protection lasts
     */
    public void setProtection(ProtectionType type, int duration) {
        this.protection = new Protection(type, duration);
        System.out.printf("%s is now protected against %s!%n" + duration,
                getName(),
                type == ProtectionType.HEALTH ? "damage" : "status changes");
    }

    /**
     * Retrieves the current protection type.
     *
     * @return the {@link ProtectionType} if set, otherwise {@code null}
     */
    public ProtectionType getProtectionType() {
        return protection != null ? protection.getType() : null;
    }

    /**
     * Returns a string describing the monster's current status: FAINTED, a condition name, or OK.
     *
     * @return the status as a string
     */
    public String getStatus() {
        if (isFainted()) {
            return "FAINTED";
        } else if (condition != null) {
            return condition.toString();
        } else {
            return "OK";
        }
    }

    /**
     * Gets a display name for the monster. Appends {@code #index} if there are multiple identical samples.
     *
     * @return the monster's display name
     */
    public String getName() {
        if (sample.getCreatedCount() > 1) {
            return "%s#%d".formatted(sample.getName(), index);
        } else {
            return sample.getName();
        }
    }

    @Override
    public String toString() {
        List<String> stats = new LinkedList<>();
        stats.add("HP %d/%d".formatted(health, getSample().getMaxHealth()));
        for (StatType stat : StatType.values()) {
            stats.add("%s %s%s".formatted(
                    stat,
                    sample.getStat(stat),
                    scales.getOrDefault(stat, 0) != 0 ? "(%+d)".formatted(scales.get(stat)) : ""
            ));
        }
        return String.join(", ", stats);
    }
}
