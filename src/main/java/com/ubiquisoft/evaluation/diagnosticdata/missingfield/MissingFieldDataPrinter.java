package com.ubiquisoft.evaluation.diagnosticdata.missingfield;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticDataPrinter;

public class MissingFieldDataPrinter implements DiagnosticDataPrinter {

    public static final String MISSING_DATA_FIELD_DETECTED_MSG = "Missing Data Field(s) Detected:";
    private final MissingFieldData diagnosticData;


    public MissingFieldDataPrinter(MissingFieldData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public void printDiagnosticData() {
        diagnosticData.getMissingFields().forEach(field -> System.out.println(
                String.format(MISSING_DATA_FIELD_DETECTED_MSG + " %s", field)));
    }
}
