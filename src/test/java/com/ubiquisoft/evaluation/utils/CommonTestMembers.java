package com.ubiquisoft.evaluation.utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class CommonTestMembers {

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;

    protected void setUpSysOutStream() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    protected void revertSysOutStream() {
        System.setOut(sysOut);
    }
}
