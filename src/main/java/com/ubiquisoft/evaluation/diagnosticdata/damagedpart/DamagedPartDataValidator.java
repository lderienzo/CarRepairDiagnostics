package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticDataValidator;

public class DamagedPartDataValidator implements DiagnosticDataValidator {

    private final DamagedPartData diagnosticData;


    public DamagedPartDataValidator(DamagedPartData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public boolean isValid() {
        return diagnosticData.getDamagedParts().isEmpty();
    }
}
