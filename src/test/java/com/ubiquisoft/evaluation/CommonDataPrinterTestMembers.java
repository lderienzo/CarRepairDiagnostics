package com.ubiquisoft.evaluation;

import java.io.*;

import org.junit.jupiter.api.*;

public class CommonDataPrinterTestMembers {

    protected final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;


    void redirectSystemOutputToOutContent() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    void resetSystemOutput() {
        System.setOut(sysOut);
    }

    @BeforeEach
    void setUpStreams() {
        redirectSystemOutputToOutContent();
    }

    @AfterEach
    void revertStreams() {
        resetSystemOutput();
    }
}
