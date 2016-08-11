package nl.ivonet.dzone;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
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
}