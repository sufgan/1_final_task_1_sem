package edu.kit.kastel.game.monsters;

import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.Element;
import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.game.utils.RegexConstructor;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MonsterSample {
    private static final Map<String, MonsterSample> SAMPLES = new HashMap<>();

    private int createdCount = 0;

    private final Map<StatType, Integer> stats;
    private final List<String> actions;
    private final String name;
    private final Element element;
    private final int maxHealth;

    public MonsterSample(String name, Element element, int maxHealth, int atk, int def, int spd, String... actions) {
        this.stats = Map.of(StatType.ATK, atk, StatType.DEF, def, StatType.SPD, spd);
        this.actions = List.of(actions);
        this.name = name;
        this.element = element;
        this.maxHealth = maxHealth;
        SAMPLES.put(name, this);
    }

    public static MonsterSample find(String name) {
        return SAMPLES.getOrDefault(name, null);
    }

    public static void clearSamples() {
        SAMPLES.clear();
    }

    public static void clearCreatedCounts() {
        for (MonsterSample sample : SAMPLES.values()) {
            sample.createdCount = 0;
        }
    }

    public Monster create() {
        return new Monster(this, ++createdCount);
    }

    public String getName() {
        return name;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public Element getElement() {
        return element;
    }

    public int getStat(StatType type) {
        return stats.getOrDefault(type, 1);
    }

    public List<String> getActions() {
        return List.copyOf(actions);
    }

    public boolean hasAction(String actionName) {
        return actions.contains(actionName);
    }

    public int getCreatedCount() {
        return createdCount;
    }

    public static String getRegex(boolean nameGroup, boolean nameSubGroup) {
        return RegexConstructor.groupAND(
                nameGroup ? Monster.class.getSimpleName() : null, "",
                RegexConstructor.groupAND(null, RegexConstructor.REGEX_SPACE,
                        "monster",
                        "(?%s\\w+)".formatted(nameSubGroup ? "<name>" : ":"), // name
                        Element.getRegex(nameSubGroup),
                        ValueType.HEALTH.toRegex(nameSubGroup),
                        ValueType.ATK.toRegex(nameSubGroup),
                        ValueType.DEF.toRegex(nameSubGroup),
                        ValueType.SPD.toRegex(nameSubGroup),
                        "(?%s\\w+(?:%s\\w+){0,3})".formatted( // action names
                                nameSubGroup ? "<actions>" : ":",
                                RegexConstructor.REGEX_SPACE
                        )
                ),
                RegexConstructor.REGEX_MULTI_NEW_LINE
        );
    }

    public static String allToString() {
        List<String> list = new LinkedList<>();
        for (MonsterSample sample : SAMPLES.values()) {
            list.add(sample.toString());
        }
        return String.join("\n", list);
    }

    @Override
    public String toString() {
        return String.format("%s: Element %s, HP %d, ATK %d, DEF %d, SPD %d",
                name,
                element,
                maxHealth,
                stats.get(StatType.ATK),
                stats.get(StatType.DEF),
                stats.get(StatType.SPD)
        );
    }
}
