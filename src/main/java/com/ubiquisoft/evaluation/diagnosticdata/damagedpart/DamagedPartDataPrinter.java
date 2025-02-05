package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import java.util.Collection;
import java.util.Map;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticDataPrinter;
import com.ubiquisoft.evaluation.domain.*;

public class DamagedPartDataPrinter implements DiagnosticDataPrinter {

    public static final String DAMAGED_PART_DETECTED_MSG = "Damaged Part Detected:";
    private static final String PART_TYPE_MUST_NOT_BE_NULL_MSG = "PartType must not be null";
    private final DamagedPartData diagnosticData;


    public DamagedPartDataPrinter(DamagedPartData diagnosticData) {
        this.diagnosticData = diagnosticData;
    }

    @Override
    public void printDiagnosticData() {
        diagnosticData.getDamagedParts().asMap().entrySet()
            .forEach(entry -> entry.getValue().forEach(
                conditionType -> printDamagedPart(partType(entry), conditionType))
            );
    }

    private PartType partType(Map.Entry<PartType, Collection<ConditionType>> e) {
        return e.getKey();
    }

    private void printDamagedPart(PartType partType, ConditionType condition) {
        if (partType == null) throw new IllegalArgumentException(PART_TYPE_MUST_NOT_BE_NULL_MSG);
        if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

        System.out.println(String.format(DAMAGED_PART_DETECTED_MSG + " %s - Condition: %s", partType, condition));
    }
}
