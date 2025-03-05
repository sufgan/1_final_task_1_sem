package edu.kit.kastel.game.monsters;

import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.game.types.Condition;
import edu.kit.kastel.game.types.Protection;
import edu.kit.kastel.game.actions.effects.ProtectionType;
import edu.kit.kastel.game.utils.Utility;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Monster {
    private final MonsterSample sample;
    private final Map<StatType,  Integer> scales;
    private final int index;

    private Condition condition;
    private Protection protection;
    private int health;

    public Monster(MonsterSample sample, int index) {
        this.sample = sample;
        scales = new HashMap<>();
        this.health = sample.getMaxHealth();
        this.index = index;
    }

    public void shiftScale(StatType stat, int shift) {
        scales.put(stat, Utility.limitValue(scales.getOrDefault(stat, 0) + shift, -5, 5));
    }

    public void shiftHealth(int shift) {
        health = Utility.limitValue(health + shift, 0, sample.getMaxHealth());
    }

    public MonsterSample getSample() {
        return sample;
    }

    public boolean isFainted() {
        return health == 0;
    }

    public double getStat(StatType stat) {
        double conditionFactor = condition == null ? 1 : condition.getStateFactor(stat);
        return Utility.scaleStat(stat, sample.getStat(stat), scales.getOrDefault(stat, 0)) * conditionFactor;
    }

    public int getHealth() {
        return health;
    }

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

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public Condition getCondition() {
        return condition;
    }

    public void updateProtection() {
        if (protection != null) {
            Protection lastProtection = protection;
            protection = protection.step();
            if (lastProtection != protection) {
                System.out.printf("%s's protection fades away...%n", getName());
            }
        }
    }

    public void setProtection(ProtectionType type, int duration) {
        this.protection = new Protection(type, duration);
    }

    public ProtectionType getProtectionType() {
        return protection != null ? protection.getType() : null;
    }

    public String getStatus() {
        if (isFainted()) {
            return "FAINTED";
        } else if (condition != null) {
            return condition.toString();
        } else {
            return "OK";
        }
    }

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
