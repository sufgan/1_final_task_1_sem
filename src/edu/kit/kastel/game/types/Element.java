package edu.kit.kastel.game.types;

import edu.kit.kastel.utils.RegexConstructor;
import edu.kit.kastel.utils.RegexProvider;

/**
 * Defines elemental types and their effectiveness relationships.
 *
 * @author uyqbd
 */
public enum Element implements RegexProvider {
    /**
     * Represents the "NORMAL" elemental type without any dominant or yielding
     * elemental relationships. The NORMAL type is neutral and does not exhibit
     * effectiveness or weaknesses against other elemental types.
     */
    NORMAL(null,    null),
    /**
     * Represents the WATER element within the elemental system, which has
     * specific interactions with other elements.
     *
     * Dominates the EARTH element, making it very effective against it.
     * Yields to the FIRE element, making it less effective in interactions with it.
     */
    WATER("EARTH", "FIRE"),
    /**
     * Represents the FIRE element in the elemental effectiveness system.
     * FIRE is dominant over WATER and yielding to EARTH.
     */
    FIRE("WATER", "EARTH"), // и начал народ огня войну
    /**
     * Represents the EARTH element and its effectiveness against other elements.
     * EARTH dominates FIRE and yields to WATER.
     */
    EARTH("FIRE",  "WATER");

    private final String dominant;
    private final String yielding;

    Element(String dominant, String yielding) {
        this.dominant = dominant;
        this.yielding = yielding;
    }

    /**
     * Determines how this element interacts with the specified element
     * (very effective, normal, or not very effective).
     *
     * @param element the element to compare against
     * @return the resulting {@link ElementEfficiency}
     */
    public ElementEfficiency getEfficiency(Element element) {
        if (element.name().equals(this.dominant)) {
            System.out.println("It is not very effective...");
            return ElementEfficiency.POWERLESS;
        } else if (element.name().equals(this.yielding)) {
            System.out.println("It is very effective!");
            return ElementEfficiency.POWERFUL;
        }
        return ElementEfficiency.NORMAL;

    }

    /**
     * Returns a regex pattern that matches any {@code Element} value.
     *
     * @param nameGroup {@code true} to include a named group in the regex
     * @return a regex pattern for elements
     */
    public static String getRegex(boolean nameGroup) {
        return RegexConstructor.groupOR(
                nameGroup ? Element.class.getSimpleName() : null,
                false, Element.values()
        );
    }

    @Override
    public String toRegex(boolean nameGroup) {
        return name();
    }

}