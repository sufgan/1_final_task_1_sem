package edu.kit.kastel.game.types;

import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

import java.util.HashMap;
import java.util.Map;

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
            this.stateFactor.put(stateFactor.getState(), stateFactor.getFactor());
        }
    }

    public double getStateFactor(StatType state) {
        return stateFactor.getOrDefault(state, 1.0);
    }

    public String getMessage(int messageIndex) {
        return messages[messageIndex] + "%n";
    }

    private static class StateFactor {
        private final StatType state;
        private final double factor;

        StateFactor(StatType state, double factor) {
            this.state = state;
            this.factor = factor;
        }

        public StatType getState() {
            return state;
        }

        public double getFactor() {
            return factor;
        }

    }

    public Condition step() {
        return RandomGenerator.probabilityGood(FINISH_PROBABILITY, END_CONDITION_DEBUG_MESSAGE) ?
                null : this;
    }

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
}
