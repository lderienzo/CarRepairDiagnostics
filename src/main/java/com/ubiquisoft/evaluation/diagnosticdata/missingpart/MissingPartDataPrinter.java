package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import java.util.Map;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticDataPrinter;
import com.ubiquisoft.evaluation.domain.PartType;

public class MissingPartDataPrinter implements DiagnosticDataPrinter {

    public static final String MISSING_PART_DETECTED_MSG = "Missing Part(s) Detected:";
    private static final String PART_TYPE_MUST_NOT_BE_NULL_MSG = "PartType must not be null";
    private final MissingPartData diagnosticData;


    public MissingPartDataPrinter(MissingPartData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public void printDiagnosticData() {
        diagnosticData.getMissingParts().entrySet().forEach(this::printOnlyIfNumberMissingGreaterThanZero);
    }

    private void printOnlyIfNumberMissingGreaterThanZero(Map.Entry<PartType, Integer> e) {
        if (missingCount(e) > 0) printMissingPart(partType(e), missingCount(e));
    }

    private PartType partType(Map.Entry<PartType, Integer> e) {
        return e.getKey();
    }

    private int missingCount(Map.Entry<PartType, Integer> e) {
        return e.getValue();
    }

    private void printMissingPart(PartType partType, Integer count) {
        if (partType == null) throw new IllegalArgumentException(PART_TYPE_MUST_NOT_BE_NULL_MSG);
        if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

        System.out.println(String.format(MISSING_PART_DETECTED_MSG + " %s - Count: %s", partType, count));
    }
}
