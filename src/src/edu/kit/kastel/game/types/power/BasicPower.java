package edu.kit.kastel.game.types.power;

import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.utils.RandomGenerator;
import edu.kit.kastel.game.utils.RegexConstructor;

public final class BasicPower extends Power {
    private static final String CRITICAl_HIT_MESSAGE = "Critical hit!";
    private static final String CRITICAl_HIT_DEBUG_MESSAGE = "critical hit";
    private static final String RANDOM_FACTOR_DEBUG_MESSAGE = "random factor";

    public static final String NAME = "base";

    public BasicPower(int value) {
        super(value, "b");
    }

    @Override
    public int getValue(Monster user, Monster target, Element actionElement) {
        double elementFactor = actionElement.getEfficiency(target.getSample().getElement()).getDamageScale();
        double statusFactor = user.getStat(StatType.ATK) / target.getStat(StatType.DEF);
        double criticalHitProbability = Math.pow(10, -target.getStat(StatType.SPD) / user.getStat(StatType.SPD)) * 100;
        int criticalHitFactor;
        if (RandomGenerator.probabilityGood(criticalHitProbability, CRITICAl_HIT_DEBUG_MESSAGE)) {
            System.out.println(CRITICAl_HIT_MESSAGE); // 8
            criticalHitFactor = 2;
        } else {
            criticalHitFactor = 1;
        }
        double sameElementFactor = user.getSample().getElement() == actionElement ? 1.5 : 1;
        double randomFactor = RandomGenerator.getRandomFactor(0.85, 1, RANDOM_FACTOR_DEBUG_MESSAGE);
        double normalFactor = 1 / 3.;
        return (int) Math.ceil(getValue() * elementFactor * statusFactor * criticalHitFactor * sameElementFactor * randomFactor * normalFactor);
    }


    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupAND(nameGroup ? BasicPower.class.getSimpleName() : null, RegexConstructor.REGEX_SPACE,
                NAME,
                ValueType.VALUE.toRegex(false)
        );
    }

}
