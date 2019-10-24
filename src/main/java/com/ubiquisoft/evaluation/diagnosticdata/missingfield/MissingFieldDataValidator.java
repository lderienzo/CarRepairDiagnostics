package com.ubiquisoft.evaluation.diagnosticdata.missingfield;


import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticDataValidator;

public class MissingFieldDataValidator implements DiagnosticDataValidator {

    private final MissingFieldData diagnosticData;


    public MissingFieldDataValidator(MissingFieldData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public boolean isValid() {
        return diagnosticData.getMissingFields().isEmpty();
    }
}
