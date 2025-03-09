package edu.kit.kastel.game.monsters;

import edu.kit.kastel.game.actions.effects.ValueType;
import edu.kit.kastel.game.types.element.Element;
import edu.kit.kastel.game.types.StatType;
import edu.kit.kastel.utils.RegexConstructor;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * A template defining base stats, element type, and available actions for a monster.
 * <p>
 * Once created, a {@code MonsterSample} can generate actual {@link Monster} instances.
 * </p>
 *
 * @author uyqbd
 */
public class MonsterSample {
    private static final String PRINT_FORMAT = "%s: ELEMENT %s, HP %d, ATK %d, DEF %d, SPD %d";
    private static final Map<String, MonsterSample> SAMPLES = new LinkedHashMap<>();

    private final Map<StatType, Integer> stats;
    private final List<String> actions;
    private final String name;
    private final Element element;
    private final int maxHealth;

    private int createdCount = 0;

    /**
     * Constructs a new {@code MonsterSample} with specified stats, element, and actions.
     *
     * @param name      the name of the monster
     * @param element   the elemental type of the monster
     * @param maxHealth the maximum health
     * @param atk       the attack stat
     * @param def       the defense stat
     * @param spd       the speed stat
     * @param actions   the names of actions available to this monster
     */
    public MonsterSample(String name, Element element, int maxHealth, int atk, int def, int spd, String... actions) {
        this.stats = Map.of(StatType.ATK, atk, StatType.DEF, def, StatType.SPD, spd);
        this.actions = List.of(actions);
        this.name = name;
        this.element = element;
        this.maxHealth = maxHealth;
        SAMPLES.put(name, this);
    }

    /**
     * Finds a {@code MonsterSample} by name.
     *
     * @param name the name of the sample to find
     * @return the {@code MonsterSample} if found, or {@code null} otherwise
     */
    public static MonsterSample find(String name) {
        return SAMPLES.getOrDefault(name, null);
    }

    /**
     * Clears all registered monster samples.
     */
    public static void clearSamples() {
        SAMPLES.clear();
    }

    /**
     * Resets the created count for every {@code MonsterSample} in the registry to 0.
     */
    public static void clearCreatedCounts() {
        for (MonsterSample sample : SAMPLES.values()) {
            sample.createdCount = 0;
        }
    }

    /**
     * Creates a new {@link Monster} instance from this sample.
     *
     * @return the newly created monster
     */
    public Monster create() {
        return new Monster(this, ++createdCount);
    }

    /**
     * Retrieves the name of this monster sample.
     *
     * @return the monster's name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves the maximum health of this monster sample.
     *
     * @return the maximum health as an integer
     */
    public int getMaxHealth() {
        return maxHealth;
    }

    /**
     * Retrieves the elemental type of this monster sample.
     *
     * @return the {@link Element} for this monster
     */
    public Element getElement() {
        return element;
    }

    /**
     * Gets a specific stat's base value (e.g. ATK, DEF, SPD).
     *
     * @param type the stat type to look up
     * @return the integer value of the requested stat
     */
    public int getStat(StatType type) {
        return stats.getOrDefault(type, 1);
    }

    /**
     * Retrieves an unmodifiable list of action names available to this monster.
     *
     * @return a list of action names
     */
    public List<String> getActions() {
        return List.copyOf(actions);
    }

    /**
     * Gets the number of {@link Monster} instances already created from this sample.
     *
     * @return the count of created monsters
     */
    public int getCreatedCount() {
        return createdCount;
    }

    /**
     * Builds a regex pattern to match a monster sample definition in the config.
     *
     * @param nameGroup    {@code true} to include a named group for the entire pattern
     * @param nameSubGroup {@code true} to include named groups for sub-parts like name or actions
     * @return a string representing the constructed regex
     */
    public static String getRegex(boolean nameGroup, boolean nameSubGroup) {
        return RegexConstructor.groupAND(
                nameGroup ? Monster.class.getSimpleName() : null, "",
                RegexConstructor.groupAND(null, RegexConstructor.REGEX_SPACE,
                        "monster",
                        "(?%s\\w+)".formatted(nameSubGroup ? "<name>" : ":"),
                        Element.getRegex(nameSubGroup),
                        ValueType.HEALTH.toRegex(nameSubGroup),
                        ValueType.ATK.toRegex(nameSubGroup),
                        ValueType.DEF.toRegex(nameSubGroup),
                        ValueType.SPD.toRegex(nameSubGroup),
                        "(?%s\\w+(?:%s\\w+){0,3})".formatted(
                                nameSubGroup ? "<actions>" : ":",
                                RegexConstructor.REGEX_SPACE
                        )
                ),
                RegexConstructor.REGEX_MULTI_NEW_LINE
        );
    }

    /**
     * Produces a string representation of all registered {@code MonsterSample} objects.
     *
     * @return a string with one sample description per line
     */
    public static String allToString() {
        List<String> list = new LinkedList<>();
        for (MonsterSample sample : SAMPLES.values()) {
            list.add(sample.toString());
        }
        return String.join("\n", list);
    }

    @Override
    public String toString() {
        return String.format(PRINT_FORMAT,
                name,
                element,
                maxHealth,
                stats.get(StatType.ATK),
                stats.get(StatType.DEF),
                stats.get(StatType.SPD)
        );
    }

}
