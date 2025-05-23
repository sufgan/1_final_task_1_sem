package edu.kit.kastel.game.types;

import edu.kit.kastel.utils.RandomGenerator;
import edu.kit.kastel.utils.RegexConstructor;
import edu.kit.kastel.utils.RegexProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a status condition that can affect a monster.
 * Each condition may modify certain stats and can end with a fixed probability per turn.
 *
 * @author uyqbd
 */
public enum Condition implements RegexProvider {
    /**
     * Represents a specific effect state named "WET" that applies modifications to an entity.
     * This state includes descriptions for how it impacts the entity and an associated
     * factor that adjusts the entity's defense stat.
     *
     * <p>
     * State Description:
     * - "%s becomes soaking wet!": Indicates the initiation of the "WET" state.
     * - "%s is soaking wet!": Describes the ongoing "WET" state.
     * - "%s dried up!": Represents the end of the "WET" state.
     * </p>
     *
     * <p>
     * Stat Impact:
     * - Affects the defense stat (DEF), reducing it by 25% (scaling factor of 0.75).
     * </p>
     */
    WET(
        "%s becomes soaking wet!",
        "%s is soaking wet!",
        "%s dried up!",
        new StateFactor(StatType.DEF, 0.75)
    ),

    /**
     * Represents the "BURN" status effect in the game, which is associated with fire-related damage
     * and affects the attack capability of an entity while active.
     *
     * <p>
     * The status includes the following:
     * - Text descriptions for when the effect is applied, ongoing, or removed:
     *   1. "%s caught on fire!" (when applied)
     *   2. "%s is burning!" (while active)
     *   3. "%s’s burning has faded!" (when removed)
     * - A {@code StateFactor} that reduces the entity's Attack (ATK) stat by 25% (factor of 0.75).
     * </p>
     */
    BURN(
        "%s caught on fire!",
        "%s is burning!",
        "%s's burning has faded!",
        new StateFactor(StatType.ATK, 0.75)
    ),

    /**
     * Represents a quicksand effect that impacts an entity in the game.
     * This effect has associated messages and a speed reduction factor.
     *
     * <p>
     * The quicksand effect provides the following:
     * - Messages to describe the state of an entity affected by quicksand.
     * - A reduction in speed through the specified {@link StatType#SPD} modifier.
     * </p>
     * <p>
     * The messages included in this effect may be used to inform players
     * about the interaction, such as being caught in or escaping the quicksand.
     * </p>
     * <p>
     * Associated with:
     * - {@link StatType#SPD}: Reduces the speed stat by a factor of 0.75.
     * </p>
     */
    QUICKSAND(
        "%s gets caught by quicksand!",
        "%s is caught in quicksand!",
        "%s escaped the quicksand!",
        new StateFactor(StatType.SPD, 0.75)
    ),

    /**
     * Represents a state change for an entity related to sleep status, providing
     * localized messages for transitioning into sleep, being asleep, or waking up.
     *
     * <p>
     * The localized messages are structured using placeholders (%s) for dynamic
     * formatting, depending on the entity undergoing the state change:
     * - Message for falling asleep.
     * - Message for being in the asleep state.
     * - Message for waking up.
     * </p>
     */
    SLEEP(
        "%s falls asleep!",
        "%s is asleep!",
        "%s woke up!"
    );

    private static final double FINISH_PROBABILITY = 1.0 / 3 * 100;
    private static final String END_CONDITION_DEBUG_MESSAGE = "end of condition";

    private final Map<StatType, Double> stateFactor;
    private final String creationMessage;
    private final String existingMessage;
    private final String finishingMessage;

    Condition(String creationMessage, String existingMessage, String finishingMessage, StateFactor... stateFactors) {
        this.creationMessage = creationMessage;
        this.existingMessage = existingMessage;
        this.finishingMessage = finishingMessage;
        this.stateFactor = new HashMap<>();
        for (StateFactor stateFactor : stateFactors) {
            this.stateFactor.put(stateFactor.state(), stateFactor.factor());
        }
    }

    /**
     * Retrieves the factor by which this condition modifies a specified stat.
     *
     * @param state the stat being checked
     * @return a multiplier for the stat's value
     */
    public double getStateFactor(StatType state) {
        return stateFactor.getOrDefault(state, 1.0);
    }

    /**
     * Advances the condition, potentially removing it with a certain probability.
     *
     * @return this condition if it remains; {@code null} if it ends
     */
    public Condition step() {
        return RandomGenerator.probabilityGood(FINISH_PROBABILITY, END_CONDITION_DEBUG_MESSAGE) ? null : this;
    }

    /**
     * Builds a regex pattern to match any valid {@code Condition} constant.
     *
     * @param nameGroup {@code true} to include a named group
     * @return a regex pattern representing all conditions
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? Condition.class.getSimpleName() : null,
                false, Condition.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return name();
    }

    /**
     * Retrieves the creation message associated with this instance.
     *
     * @return a string containing the creation message
     */
    public String getCreationMessage() {
        return creationMessage;
    }

    /**
     * Retrieves the existing message.
     *
     * @return the existing message as a {@code String}
     */
    public String getExistingMessage() {
        return existingMessage;
    }

    /**
     * Retrieves the finishing message associated with a specific operation or process.
     *
     * @return the finishing message as a string
     */
    public String getFinishingMessage() {
        return finishingMessage;
    }

    /**
     * Represents a factor associated with a specific stat type.
     * This immutable record is used to encapsulate a {@link StatType} and a corresponding
     * scaling factor, which can be used in various computations or adjustments
     * within the game, such as stat calculations or scaling mechanisms.
     *
     * <p>
     * The {@link StatType} indicates the specific statistic type (e.g., attack, defense),
     * while the factor represents a multiplier or adjustment value for that type.
     * </p>
     */
    private record StateFactor(StatType state, double factor) {

    }

}
