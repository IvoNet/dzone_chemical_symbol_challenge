package nl.ivonet.dzone;

/**
 * Checks if symbols are valid in the Splurth periodic table of elements.
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
