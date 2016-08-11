package nl.ivonet.dzone;

import java.util.Arrays;
import java.util.Set;
import java.util.TreeSet;

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
     * Both letters in the proposedSymbol must appear in the elementName name, but the first letter of the elementName
     * name does not
     * necessarily need to appear in the proposedSymbol. So Hg is not valid for Mercury, but Cy is.
     * </li>
     * <li>
     * The two letters must appear in order in the elementName name. So Vr is valid for Silver, but Rv is not. To be
     * clear,
     * both Ma and Am are valid for Magnesium, because there is both an a that appears after an m, and an m that
     * appears
     * after an a.
     * </li>
     * <li>
     * If the two letters in the proposedSymbol are the same, it must appear twice in the elementName name. So Nn is
     * valid for
     * Xenon, but Xx and Oo are not.
     * </li>
     * </ul>
     *
     * @param elementName    the name of the elementName
     * @param proposedSymbol the proposedSymbol belonging to the elementName
     * @return true if the proposedSymbol is correct for this elementName
     */
    public boolean validSymbol(final String elementName, final String proposedSymbol) {
        if (!validFormat(proposedSymbol)) {
            return false;
        }
        final String element = elementName.toLowerCase();
        final String symbol = proposedSymbol.toLowerCase();
        final int one = element.indexOf(symbol.charAt(0));
        final int two = element.lastIndexOf(symbol.charAt(1));
        return existsInElement(one) && existsInElement(two) && firstCharBeforeSecond(one, two);
    }

    /**
     * Given an element name, find the valid symbol for that name that's first in alphabetical order.
     * E.g.
     * Gozerium -> Ei
     * Slimyrine -> Ie
     *
     * @param elementName the element to get the symbol from
     * @return the alphabetically first symbol
     */
    public String firstSymbolAlphabeticaly(final String elementName) {
        if (!validElement(elementName)) {
            throw new IllegalStateException("Element must be provided and at least two chars long");
        }
        final String element = elementName.toLowerCase();

        final char[] sortedChars = sortedChars(element);

        String firstChar = String.valueOf(sortedChars[0]);

        if (firstAphabeticallyIsLastLetterInElement(element, firstChar)) {
            firstChar = String.valueOf(sortedChars[1]);
        }

        final char[] lettersInElementAfterFirstChar = sortedChars(restOfElement(element, firstChar));
        return firstChar.toUpperCase() + lettersInElementAfterFirstChar[0];
    }

    /**
     * Given an element name, find the number of distinct valid symbols for that name.
     * E.g. Zuulon -> 11
     *
     * @param elementName the element to get the count from
     * @return the number of distinct valid symbols
     */
    public int numberOfDistinctSymbols(final String elementName) {
        return possibleSymbols(elementName).size();
    }

    private Set<String> possibleSymbols(final String elementName) {
        final Set<String> symbols = new TreeSet<>();
        for (int i = 1; i < elementName.length(); i++) {
            final String element = elementName.toLowerCase();
            final String firstChar = String.valueOf(element.charAt(i - 1))
                                           .toUpperCase();
            element.substring(i)
                   .chars()
                   .mapToObj(ch -> firstChar + (char) ch)
                   .forEach(symbols::add);
        }
        return symbols;
    }

    private boolean firstCharBeforeSecond(final int one, final int two) {
        return one < two;
    }

    private boolean existsInElement(final int result) {
        return result >= 0;
    }

    private String restOfElement(final String element, final String firstChar) {
        return element.substring(element.indexOf(firstChar) + 1);
    }

    private char[] sortedChars(final String element) {
        final char[] allLettersInElement = element.toCharArray();
        Arrays.sort(allLettersInElement);
        return allLettersInElement;
    }

    private boolean firstAphabeticallyIsLastLetterInElement(final String element, final String firstChar) {
        return element.indexOf(firstChar) == (element.length() - 1);
    }

    public boolean validElement(final String elementName) {
        return (elementName != null) && "true".equals(elementName.replaceAll("[A-Z][a-z]+", "true"));
    }
}
