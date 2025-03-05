package edu.kit.kastel.game.types;

import edu.kit.kastel.game.utils.RegexConstructor;
import edu.kit.kastel.game.utils.RegexProvider;

public enum StatType implements RegexProvider {
    ATK(2),
    DEF(2),
    SPD(2),
    PRC(3),
    AGL(3);

    private final double factor;

    StatType(double factor) {
        this.factor = factor;
    }

//    private static final Map<String, Map<BaseState, Integer>> stats = new HashMap<>();

//    public static void addBaseState(String monsterName, BaseState baseState, int value) {
//        if (!BaseState.stats.containsKey(monsterName)) {
//            BaseState.stats.put(monsterName, new HashMap<>());
//        }
//        BaseState.stats.get(monsterName).put(baseState, value);
//    }

//    public int getFor(String monsterName) {
//        return BaseState.stats.getOrDefault(monsterName, new HashMap<>()).getOrDefault(this, 1);
//    }

//    public int getFor(Monster monster) {
//        return getFor(monster.getName());
//    }
    public double getFactor() {
        return factor;
    }

//    private double getScaledFor(String monsterName, int scale) {
//        double value = getFor(monsterName);
//        if (scale >= 0){
//            value *= (scaleFactor + scale) / scaleFactor;
//        } else {
//            value *= scaleFactor / (scale - scaleFactor);
//        }
//        return value;
//    }

    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? StatType.class.getSimpleName() : null,
                false, StatType.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return name();
    }

}
