package com.ubiquisoft.evaluation;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

class CommonTestMembers {

    static final CarDiagnosticDataExtractor CAR_DIAGNOSTIC_DATA_EXTRACTOR  = new CarDiagnosticDataExtractorImpl();
    static DiagnosticData diagnosticData;

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;

    void redirectSystemOutputToOutContent() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    void resetSystemOutput() {
        System.setOut(sysOut);
    }
}
