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
        assertTrue(chemicalSymbol.validSymbol("Spenglerium", "Ee"));
        assertTrue(chemicalSymbol.validSymbol("Zeddemorium", "Zr"));
        assertTrue(chemicalSymbol.validSymbol("Venkmine", "Kn"));
        assertFalse(chemicalSymbol.validSymbol("Stantzon", "Zt"));
        assertFalse(chemicalSymbol.validSymbol("Melintzum", "Nn"));
        assertFalse(chemicalSymbol.validSymbol("Tullium", "Ty"));
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
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Aaa"), is(1));
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Gozerium"), is(28));
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Ab"), is(1));
        assertThat(chemicalSymbol.numberOfDistinctSymbols("Tullium"), is(14));
    }

}