package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.TestConstants.CAR_CREATOR;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import com.ubiquisoft.evaluation.domain.Car;

class CommonTestMembers {

    static final CarDiagnosticDataExtractor CAR_DIAGNOSTIC_DATA_EXTRACTOR  = new CarDiagnosticDataExtractorImpl();
    static DiagnosticData diagnosticData;

    final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;

    void extractDiagnosticData(String xmlToUse) {
        Car car = CAR_CREATOR.createFromXml(xmlToUse);
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
    }

    void redirectSystemOutputToOutContent() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    void resetSystemOutput() {
        System.setOut(sysOut);
    }
}
