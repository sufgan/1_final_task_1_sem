package edu.kit.kastel.game.types;

import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a status condition that can affect a monster.
 * Each condition may modify certain stats and can end with a fixed probability per turn.
 *
 * @author uyqbd
 */
public enum Condition implements RegexProvider {
    WET(new String[] {
            "%s becomes soaking wet!",
            "%s is soaking wet!",
            "%s dried up!"},
            new StateFactor(StatType.DEF, 0.75)),

    BURN(new String[] {
            "%s caught on fire!",
            "%s is burning!",
            "%s’s burning has faded!"},
            new StateFactor(StatType.ATK, 0.75)),

    QUICKSAND(new String[] {
            "%s gets caught by quicksand!",
            "%s is caught in quicksand!",
            "%s escaped the quicksand!"},
            new StateFactor(StatType.SPD, 0.75)),

    SLEEP(new String[] {
            "%s falls asleep!",
            "%s is asleep!",
            "%s woke up!"});

    private static final double FINISH_PROBABILITY = 1.0 / 3 * 100;
    private static final String END_CONDITION_DEBUG_MESSAGE = "end of condition";

    public static final int CREATING_MESSAGE = 0;
    public static final int EXISTING_MESSAGE = 1;
    public static final int FINISHING_MESSAGE = 2;

    private final String[] messages;
    private final Map<StatType, Double> stateFactor;

    Condition(String[] messages, StateFactor... stateFactors) {
        this.messages = messages;
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
     * Provides the message associated with this condition at a given index.
     *
     * @param messageIndex an index for condition messages
     * @return the formatted message string
     */
    public String getMessage(int messageIndex) {
        return messages[messageIndex] + "%n";
    }

    /**
     * Advances the condition, potentially removing it with a certain probability.
     *
     * @return this condition if it remains; {@code null} if it ends
     */
    public Condition step() {
        return RandomGenerator.probabilityGood(FINISH_PROBABILITY, END_CONDITION_DEBUG_MESSAGE) ?
                null : this;
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
     * Represents a factor associated with a specific stat type.
     * This immutable record is used to encapsulate a {@link StatType} and a corresponding
     * scaling factor, which can be used in various computations or adjustments
     * within the game, such as stat calculations or scaling mechanisms.
     *
     * The {@link StatType} indicates the specific statistic type (e.g., attack, defense),
     * while the factor represents a multiplier or adjustment value for that type.
     */
    private record StateFactor(StatType state, double factor) {

    }

}
