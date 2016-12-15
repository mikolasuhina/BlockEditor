package com.flowcharts.kolas.labs_tpcs;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by mikola on 12.12.2016.
 */
public class BooleanFunctionNotAndOrTest {
    BooleanFunctionNotAndOr mBooleanFunctionNotAndOr;

    @Before
    public void setUp() throws Exception {
        String s = "  Y5 =((not(Q0)•Q1•not(Q2)•not(Q3)));\n" +
                "    Y1 =((Q0•not(Q1)•not(Q2)•not(Q3)));\n" +
                "    Y2 =((Q0•Q1•not(Q2)•not(Q3)));\n" +
                "    Y =((Q0•not(Q1)•Q2•not(Q3)));\n" +
                "    Y3 =((not(Q0)•not(Q1)•Q2•not(Q3)));\n" +
                "    R0 =((not(Q0)•not(Q1)•not(Q2)•not(Q3)));\n" +
                "    S0 =((Q0•not(Q1)•Q2•not(Q3)•not(Z))▼(Q0•Q1•not(Q2)•not(Q3))▼(Q0•not(Q1)•not(Q2)•Q3));\n" +
                "    R1 =((Q0•not(Q1)•not(Q2)•not(Q3)•not(X)));\n" +
                "    S1 =((not(Q0)•Q1•not(Q2)•not(Q3)));\n" +
                "    R2 =((not(Q0)•not(Q1)•not(Q2)•Q3)▼(Q0•not(Q1)•not(Q2)•not(Q3)•X•Z));\n" +
                "    S2 =((not(Q0)•not(Q1)•Q2•not(Q3)));\n" +
                "    R3 =((Q0•not(Q1)•not(Q2)•not(Q3)•X•not(Z)));\n" +
                "    S3 =((not(Q0)•not(Q1)•Q2•Q3));";
        mBooleanFunctionNotAndOr = new BooleanFunctionNotAndOr(s);
    }

    @Test
    public void cropString() throws Exception {
        String sf = "not(dwashbksadbsa)";
        String s = "(dwashbksadbsa)";
        String res = "dwashbksadbsa";


    }

    @Test
    public void createAndNor() throws Exception {
        ArrayList<String> pre = mBooleanFunctionNotAndOr.functions;
        assertEquals(pre, mBooleanFunctionNotAndOr.functions);
    }

   /*@Test
    public void normalizeAND() {
        String s = "not(a)•v•not(s)•g•w";
        String res = "(((not(a)•v)•(not(s)•g))•((w•w)•(w•w)))";
        assertEquals(res, mBooleanFunctionNotAndOr.normalizeAND(s));
    }

   @Test
    public void normalizeOR() {
       String s = "(not(a)•v•not(s)•g•w)▼(not(a)•v•not(s)•g•w)▼(not(a)•v•not(s)•g•w)";
       String res = "(((not(a)•v)•(not(s)•g))•((w•w)•(w•w)))";
        assertEquals(res, mBooleanFunctionNotAndOr.normalizeOR(s,""));
    }*/

}