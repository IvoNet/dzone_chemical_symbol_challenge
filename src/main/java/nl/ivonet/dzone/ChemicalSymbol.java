/*
 * Copyright 2016 Ivo Woltring <WebMaster@ivonet.nl>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package nl.ivonet.dzone;

import java.util.SortedSet;
import java.util.TreeSet;
import java.util.regex.Pattern;

/**
 * Checks if symbols are valid in the Splurth periodic table of elements.
 * <p>
 * <a href="https://dzone.com/articles/java-code-challenge-chemical-symbol-naming-part-on">D-Zone</a>
 */
public class ChemicalSymbol {

    private static final Pattern TWO_LETTERS = Pattern.compile("[A-Z][a-z]");
    private static final Pattern ELEMENT_NAME = Pattern.compile("[A-Z][a-z]+");

    /**
     * All chemical symbols must be exactly two letters, so B is not a valid symbol for Boron.
     * The first letter must be a capital cased letter
     *
     * @param symbol the symbol to validate against the business rule
     * @return true if valid else false
     */
    public boolean validFormat(final String symbol) {
        return (symbol != null) && exactlyTwoCharactersCamelCased(symbol);
    }

    /**
     * Validates a symbol given the following rulez:
     * <ul>
     * <li>
     * Both letters in the proposedSymbol must appear in the elementName name, but the first letter of the elementName
     * name does not necessarily need to appear in the proposedSymbol. So Hg is not valid for Mercury, but Cy is.
     * </li>
     * <li>
     * The two letters must appear in order in the elementName name. So Vr is valid for Silver, but Rv is not. To be
     * clear, both Ma and Am are valid for Magnesium, because there is both an a that appears after an m, and an m that
     * appears after an a.
     * </li>
     * <li>
     * If the two letters in the proposedSymbol are the same, it must appear twice in the elementName name. So Nn is
     * valid for Xenon, but Xx and Oo are not.
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
        validateElement(elementName);
        return possibleSymbols(elementName).first();
    }

    /**
     * Given an element name, find the number of distinct valid symbols for that name.
     * E.g. Zuulon -> 11
     * <p>
     * This version utilizes an optimized technique.
     *
     * @param elementName the element to get the count from
     * @return the number of distinct valid symbols
     */
    public int numberOfDistinctSymbols(final String elementName) {
        return possibleSymbols(elementName).size();
    }

    /**
     * Given an element name, find the number of distinct valid symbols for that name.
     * E.g. Zuulon -> 11
     * <p>
     * This version utilizes the Brute Froce technique.
     *
     * @param elementName the element to get the count from
     * @return the number of distinct valid symbols
     */
    public int numberOfDistinctSymbolsBruteForce(final String elementName) {
        return possibleSymbolsBruteForce(elementName).size();
    }

    /*
     * Brute force version for getting all possible combinations of symbols in an element.
     * Just try all permutations of two letters against the validSymbol method.
     * The validSymbol method was already written and tested, so writing this is a no brainer.
     * The downside is that it tries all combinations of two letters which is not very efficient.
     */
    private SortedSet<String> possibleSymbolsBruteForce(final String elementName) {
        final SortedSet<String> symbols = new TreeSet<>();
        for (int firstLetter = 'A'; firstLetter < ('Z' + 1); firstLetter++) {
            for (int seccondLetter = 'a'; seccondLetter < ('z' + 1); seccondLetter++) {
                final String symbol = "" + (char) firstLetter + (char) seccondLetter;
                if (validSymbol(elementName, symbol)) {
                    symbols.add(symbol);
                }
            }
        }
        return symbols;
    }


    /*
     * More optimized version of getting all possible combinations of symbols in an element.
     * This done by making the elementName leading in getting the permutations.
     * Downside is that it does not use already tested code.
     * Upside is that it does only the bare minimum to get the results.
     */
    private SortedSet<String> possibleSymbols(final String elementName) {
        validateElement(elementName);

        final String element = elementName.toLowerCase();

        final SortedSet<String> symbols = new TreeSet<>();
        for (int i = 1; i < elementName.length(); i++) {
            final String firstChar = String.valueOf(element.charAt(i - 1))
                                           .toUpperCase();
            element.substring(i)
                   .chars()
                   .mapToObj(ch -> firstChar + (char) ch)
                   .forEach(symbols::add);
        }
        return symbols;
    }

    private boolean firstCharBeforeSecond(final int first, final int second) {
        return first < second;
    }

    private boolean existsInElement(final int result) {
        return result >= 0;
    }

    private void validateElement(final String elementName) {
        if (!validElement(elementName)) {
            throw new IllegalArgumentException("Element must be provided and at least two chars long");
        }
    }

    public boolean validElement(final String elementName) {
        return (elementName != null) && camelCasedWordOfAtLeastTwoCharacters(elementName);
    }

    private boolean exactlyTwoCharactersCamelCased(final String symbol) {
        return TWO_LETTERS.matcher(symbol).matches();
    }

    private boolean camelCasedWordOfAtLeastTwoCharacters(final String elementName) {
        return ELEMENT_NAME.matcher(elementName).matches();
    }
}
