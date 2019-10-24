package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticDataValidator;

public class MissingPartDataValidator implements DiagnosticDataValidator {

    private final MissingPartData diagnosticData;


    public MissingPartDataValidator(MissingPartData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public boolean isValid() {
        return sumOfMissingPartCountsIsZero();
    }

    private boolean sumOfMissingPartCountsIsZero() {
        return sumOfAllMapValues() == 0;
    }

    private int sumOfAllMapValues() {
        return diagnosticData.getMissingParts().values().stream().reduce(0, Integer::sum);
    }
}
