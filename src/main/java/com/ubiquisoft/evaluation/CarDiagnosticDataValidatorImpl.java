package com.ubiquisoft.evaluation;


final class CarDiagnosticDataValidatorImpl implements CarDiagnosticDataValidator {

    private final DiagnosticData diagnosticData;

    public CarDiagnosticDataValidatorImpl(DiagnosticData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public boolean hasMissingDataFields() {
        return missingDataFieldsListIsNotEmpty();
    }

    private boolean missingDataFieldsListIsNotEmpty() {
        return !diagnosticData.getMissingFields().isEmpty();
    }

    @Override
    public boolean hasMissingParts() {
        return sumOfMissingPartCountsIsGreaterThanZero();
    }

    private boolean sumOfMissingPartCountsIsGreaterThanZero() {
        return sumOfAllValuesInMap() > 0;
    }

    private int sumOfAllValuesInMap() {
        return diagnosticData.getMissingParts().values().stream().reduce(0, Integer::sum);
    }

    @Override
    public boolean hasDamagedParts() {
        return damagedPartsMapIsNotEmpty();
    }

    private boolean damagedPartsMapIsNotEmpty() {
        return !diagnosticData.getDamagedParts().isEmpty();
    }
}
