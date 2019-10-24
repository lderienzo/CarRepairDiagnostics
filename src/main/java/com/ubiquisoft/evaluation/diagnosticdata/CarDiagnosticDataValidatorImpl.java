package com.ubiquisoft.evaluation.diagnosticdata;


import com.ubiquisoft.evaluation.diagnosticdata.damagedpart.DamagedPartDataValidator;
import com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldDataValidator;
import com.ubiquisoft.evaluation.diagnosticdata.missingpart.MissingPartDataValidator;

public final class CarDiagnosticDataValidatorImpl implements CarDiagnosticDataValidator {

    private final MissingFieldDataValidator missingFieldDataValidator;
    private final MissingPartDataValidator missingPartDataValidator;
    private final DamagedPartDataValidator damagedPartDataValidator;

    public CarDiagnosticDataValidatorImpl(DiagnosticData diagnosticData) {
        this.missingFieldDataValidator = new MissingFieldDataValidator(diagnosticData.getMissingFieldData());
        this.missingPartDataValidator = new MissingPartDataValidator(diagnosticData.getMissingPartData());
        this.damagedPartDataValidator = new DamagedPartDataValidator(diagnosticData.getDamagedPartData());
    }

    @Override
    public boolean hasMissingDataFields() {
        return missingFieldDataValidatorReportsNotValid();
    }

    private boolean missingFieldDataValidatorReportsNotValid() {
        return !missingFieldDataValidator.isValid();
    }

    @Override
    public boolean hasMissingParts() {
        return missingPartDataValidatorReportsNotValid();
    }

    private boolean missingPartDataValidatorReportsNotValid() {
        return !missingPartDataValidator.isValid();
    }

    @Override
    public boolean hasDamagedParts() {
        return damagedPartDataValidatorReportsNotValid();
    }

    private boolean damagedPartDataValidatorReportsNotValid() {
        return !damagedPartDataValidator.isValid();
    }
}
