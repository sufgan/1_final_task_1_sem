package edu.kit.kastel.game.actions;

import edu.kit.kastel.game.actions.effects.ApplyableEffect;
import edu.kit.kastel.game.actions.effects.BurnDamageEffect;
import edu.kit.kastel.game.monsters.Monster;
import edu.kit.kastel.game.types.StatType;

import java.util.Collection;
import java.util.LinkedList;

public class EffectQueue implements Comparable<EffectQueue> {
    private static final String USE_ACTION_MESSAGE_FORMAT = "%s uses %s action!%n";
    private static final String ACTION_FAIL_MESSAGE = "The action failed...";
    private static final String PASS_MESSAGE_FORMAT = "%s passes%n";

    private final LinkedList<ApplyableEffect> actionEffects = new LinkedList<>();
    private final LinkedList<ApplyableEffect> constantEffects = new LinkedList<>();
    private final String actionName;
    private final Monster user;
    private final Monster target;

    public EffectQueue(String actionName, Monster user, Monster target) {
        this.actionName = actionName;
        this.user = user;
        this.target = target;
    }

    public void add(ApplyableEffect effect, boolean actionEffect) {
        if (actionEffect) {
            actionEffects.add(effect);
        } else {
            constantEffects.add(effect);
        }
    }

    public void addAll(Collection<ApplyableEffect> effect, boolean actionEffect) {
        if (actionEffect) {
            actionEffects.addAll(effect);
        } else {
            constantEffects.addAll(effect);
        }
    }

    public void apply() {
        printMessage();
        if (user.getCondition() != null) {
            switch (user.getCondition()) {
                case SLEEP -> actionEffects.clear();
                case BURN -> constantEffects.add(new BurnDamageEffect());
            }
        }

        applyActionEffects();
        applyConstantEffects();
    }

    private void printMessage() {
        if (!actionName.isEmpty()) { // else is empty action that is needed for pass command
            System.out.printf(USE_ACTION_MESSAGE_FORMAT, user.getName(), actionName);
        } else {
            System.out.printf(PASS_MESSAGE_FORMAT, user.getName());
        }
    }

    private void applyActionEffects() {
        if (!actionEffects.isEmpty()) {
            boolean firstEffectResult = actionEffects.pollFirst().apply(user, target);
            if (!firstEffectResult) {
                System.out.println(ACTION_FAIL_MESSAGE);
                return;
            }
            while (!actionEffects.isEmpty()) {
                actionEffects.pollFirst().apply(user, target);
            }
        }
    }

    private void applyConstantEffects() {
        for (ApplyableEffect effect : constantEffects) {
            effect.apply(user, target);
        }
    }

    public Monster getUser() {
        return user;
    }

    @Override
    public int compareTo(EffectQueue o) {
        return Double.compare(user.getStat(StatType.AGL), o.user.getStat(StatType.AGL));
    }

    @Override
    public String toString() {
        return "%s:%nAE -> %s%nCE -> %s".formatted(actionName, actionEffects, constantEffects);
    }
}
