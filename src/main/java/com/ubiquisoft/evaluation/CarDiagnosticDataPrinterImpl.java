package com.ubiquisoft.evaluation;

import java.util.Map;

import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;

final class CarDiagnosticDataPrinterImpl implements CarDiagnosticDataPrinter {

    static final String MISSING_DATA_FIELD_DETECTED_MSG = "Missing Data Field(s) Detected:";
    static final String MISSING_PART_DETECTED_MSG = "Missing Part(s) Detected:";
    static final String DAMAGED_PART_DETECTED_MSG = "Damaged Part Detected:";
    private static final String PART_TYPE_MUST_NOT_BE_NULL_MSG = "PartType must not be null";
    private final DiagnosticData diagnosticData;


    public CarDiagnosticDataPrinterImpl(DiagnosticData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public void printMissingDataFields() {
        diagnosticData.getMissingFields().forEach(
                field -> System.out.println(
                        String.format(MISSING_DATA_FIELD_DETECTED_MSG + " %s", field)));
    }

    @Override
    public void printMissingParts() {
        for (Map.Entry<PartType, Integer> e : diagnosticData.getMissingParts().entrySet())
            printOnlyIfMissingCountGreaterThanZero(e);
    }

    private void printOnlyIfMissingCountGreaterThanZero(Map.Entry<PartType, Integer> e) {
        if (missingCount(e) > 0)
            printMissingPart(partType(e), missingCount(e));
    }

    private int missingCount(Map.Entry<PartType, Integer> e) {
        return e.getValue();
    }

    private void printMissingPart(PartType partType, Integer count) {
        if (partType == null) throw new IllegalArgumentException(PART_TYPE_MUST_NOT_BE_NULL_MSG);
        if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

        System.out.println(String.format(MISSING_PART_DETECTED_MSG + " %s - Count: %s", partType, count));
    }

    private PartType partType(Map.Entry<PartType, Integer> e) {
        return e.getKey();
    }

    @Override
    public void printDamagedParts() {
        for (Map.Entry<PartType, ConditionType> e : diagnosticData.getDamagedParts().entrySet())
            printDamagedPart(e.getKey(), e.getValue());
    }

    private void printDamagedPart(PartType partType, ConditionType condition) {
        if (partType == null) throw new IllegalArgumentException(PART_TYPE_MUST_NOT_BE_NULL_MSG);
        if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

        System.out.println(String.format(DAMAGED_PART_DETECTED_MSG + " %s - Condition: %s", partType, condition));
    }
}
