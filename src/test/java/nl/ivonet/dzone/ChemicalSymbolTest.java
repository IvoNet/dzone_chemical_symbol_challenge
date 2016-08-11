package nl.ivonet.dzone;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Unit test for {@link ChemicalSymbol} class.
 */
public class ChemicalSymbolTest {

    private ChemicalSymbol chemicalSymbol;

    @Before
    public void setUp() throws Exception {
        chemicalSymbol = new ChemicalSymbol();
    }

    @Test
    public void testIfElementIsExactlyTwoLettersAndCamelCased() throws Exception {
        assertTrue(chemicalSymbol.validFormat("Aa"));
        assertFalse(chemicalSymbol.validFormat("A"));
        assertFalse(chemicalSymbol.validFormat("1"));
        assertFalse(chemicalSymbol.validFormat("12"));
        assertFalse(chemicalSymbol.validFormat("A1"));
        assertFalse(chemicalSymbol.validFormat("aa"));
        assertFalse(chemicalSymbol.validFormat("Aaa"));
        assertFalse(chemicalSymbol.validFormat(null));
    }

    @Test
    public void testIfWrongSymbolIsRejected() throws Exception {
        assertFalse(chemicalSymbol.validSymbol("Boron", "B"));
        assertFalse(chemicalSymbol.validSymbol("Boron", "Bor"));
    }

    @Test
    public void testIfBothLettersExistInElement() throws Exception {
        assertTrue(chemicalSymbol.validSymbol("Boron", "Bo"));
        assertTrue(chemicalSymbol.validSymbol("Boron", "Oo"));
        assertTrue(chemicalSymbol.validSymbol("Boron", "On"));
        assertTrue(chemicalSymbol.validSymbol("Boron", "Or"));
        assertFalse(chemicalSymbol.validSymbol("Boron", "No"));
        assertFalse(chemicalSymbol.validSymbol("Mercury", "Hg"));
        assertTrue(chemicalSymbol.validSymbol("Mercury", "Cy"));
        assertTrue(chemicalSymbol.validSymbol("Xenon", "Nn"));
    }

    @Test(expected = IllegalStateException.class)
    public void elementMustBeProvidedWhenAskingForSymbol() throws Exception {
        chemicalSymbol.firstSymbolAlphabeticaly(null);
    }

    @Test
    public void validElementNameShouldBeLongerThanOneLetter() throws Exception {
        assertTrue(chemicalSymbol.validElement("Boron"));
        assertFalse(chemicalSymbol.validElement("B"));

    }

    /**
     * Bonus Challenge 1
     * <p>
     * Given an element name, find the valid symbol for that name that's first in alphabetical order.
     * E.g.
     * Gozerium -> Ei
     * Slimyrine -> Ie
     */
    @Test
    public void testFirstAlfabeticalSymbol() throws Exception {
        assertThat(chemicalSymbol.firstSymbolAlphabeticaly("Gozerium"), is("Ei"));
        assertThat(chemicalSymbol.firstSymbolAlphabeticaly("Slimyrine"), is("Ie"));
        assertThat(chemicalSymbol.firstSymbolAlphabeticaly("Aaaaa"), is("Aa"));
        assertThat(chemicalSymbol.firstSymbolAlphabeticaly("Aa"), is("Aa"));
    }

    /**
     * Bonus Challenge 2
     * <p>
     * Given an element name, find the number of distinct valid symbols for that name.
     * E.g. Zuulon -> 11
     */
    @Test
    public void testAllPossibleCombinationsInElement() throws Exception {
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Zuulon"), is(11));
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Aa"), is(1));
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Gozerium"), is(28));
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Ab"), is(1));
    }

}