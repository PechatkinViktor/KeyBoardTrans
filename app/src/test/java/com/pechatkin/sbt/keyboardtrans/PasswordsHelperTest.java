package com.pechatkin.sbt.keyboardtrans;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PasswordsHelperTest {

    static final String[] RUS = {"й","ц","у","к","е"};
    static final String[] ENG = {"q","w","e","r","t"};

    public static final String[] SOURSES = {
           "",
           "йцуке",
            "ЙЦУКЕ",
            "="


    };
    public static final String[] RESULTS = {
            "",
            "qwert",
            "QWERT",
            "="


    } ;
    private PasswordsHelper helper;

    @Before
    public void setUp() throws Exception {
        helper = new PasswordsHelper(RUS, ENG);
    }


    @Test
    public void testConvert() {
        assertTrue("Error is test case", SOURSES.length == RESULTS.length);

        for (int i=0; i<SOURSES.length;i++) {
            assertEquals(RESULTS[i], helper.convert(SOURSES[i]));
        }
    }

    @Test
    public void convertIsThrows() {

    }

}