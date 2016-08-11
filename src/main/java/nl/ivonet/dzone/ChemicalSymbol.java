package nl.ivonet.dzone;

/**
 * Java Code Challenge is a new regular segment taking the best challenge from Reddit's dailyprogrammer. Things are a
 * little different here as we're focused on Java. A working solution is not enough; we're looking for the cleanest Java
 * code with tests. 3rd party libraries are welcome but if you can do it without it will be easier for others to
 * comprehend.
 * <p>
 * If you can fit your solution in the comments then go for it, but preferably put your answer in GitHub and link in the
 * comments. Next week we'll be sharing the best solutions and sharing the best code practices we see!
 * <p>
 * This is a beefier 2-part challenge and may take a bit longer to do- I'm really excited to see what solutions you come
 * up with!
 * <p>
 * Description The inhabitants of the planet Splurth are building their own periodic table of the elements. Just like
 * Earth's periodic table has a chemical symbol for each element (H for Hydrogen, Li for Lithium, etc.), so does
 * Splurth's. However, their chemical symbols must follow certain rules:
 * <p>
 * All chemical symbols must be exactly two letters, so B is not a valid symbol for Boron. Both letters in the symbol
 * must appear in the element name, but the first letter of the element name does not necessarily need to appear in the
 * symbol. So Hg is not valid for Mercury, but Cy is. The two letters must appear in order in the element name. So Vr is
 * valid for Silver, but Rv is not. To be clear, both Ma and Am are valid for Magnesium, because there is both an a that
 * appears after an m, and an m that appears after an a. If the two letters in the symbol are the same, it must appear
 * twice in the element name. So Nn is valid for Xenon, but Xx and Oo are not. As a member of the Splurth Council of
 * Atoms and Atom-Related Paraphernalia, you must determine whether a proposed chemical symbol fits these rules.
 * <p>
 * Details Write a function that, given two strings, one an element name and one a proposed symbol for that element,
 * determines whether the symbol follows the rules. If you like, you may parse the program's input and output the
 * result, but this is not necessary.
 * <p>
 * The symbol will have exactly two letters. Both element name and symbol will contain only the letters a-z. Both the
 * element name and the symbol will have their first letter capitalized, with the rest lowercase. (If you find that too
 * challenging, it's okay to instead assume that both will be completely lowercase.)
 * <p>
 * Examples Spenglerium, Ee -> true Zeddemorium, Zr -> true Venkmine, Kn -> true Stantzon, Zt -> false Melintzum, Nn ->
 * false Tullium, Ty -> false Optional Bonus Challenges Given an element name, find the valid symbol for that name
 * that's first in alphabetical order. E.g.Gozerium -> Ei, Slimyrine -> Ie. Given an element name, find the number of
 * distinct valid symbols for that name. E.g. Zuulon -> 11.
 */
public class ChemicalSymbol {

    /**
     * All chemical symbols must be exactly two letters, so B is not a valid symbol for Boron.
     *
     * @param symbol the symbol to validate against the business rule
     * @return true if valid else false
     */
    public boolean validFormat(final String symbol) {
        return (symbol != null) && "true".equals(symbol.replaceAll("[A-Z][a-z]", "true"));
    }

    /**
     * <ul>
     * <li>
     * Both letters in the symbol must appear in the element name, but the first letter of the element name does not
     * necessarily need to appear in the symbol. So Hg is not valid for Mercury, but Cy is.
     * </li>
     * <li>
     * The two letters must appear in order in the element name. So Vr is valid for Silver, but Rv is not. To be clear,
     * both Ma and Am are valid for Magnesium, because there is both an a that appears after an m, and an m that
     * appears
     * after an a.
     * </li>
     * <li>
     * If the two letters in the symbol are the same, it must appear twice in the element name. So Nn is valid for
     * Xenon, but Xx and Oo are not.
     * </li>
     * </ul>
     *
     * @param element the name of the element
     * @param symbol  the symbol belonging to the element
     * @return true if the symbol is correct for this element
     */
    public boolean validSymbol(final String element, final String symbol) {
        if (!validFormat(symbol)) {
            return false;
        }
        final String elementLowerCase = element.toLowerCase();
        final String symbolLowerCase = symbol.toLowerCase();
        final int one = elementLowerCase.indexOf(symbolLowerCase.charAt(0));
        final int two = elementLowerCase.lastIndexOf(symbolLowerCase.charAt(1));
        return existsInElement(one) && existsInElement(two) && firstelementBeforeSecond(one, two);
    }

    private boolean firstelementBeforeSecond(final int one, final int two) {
        return one < two;
    }

    private boolean existsInElement(final int result) {
        return result >= 0;
    }
}
