package com.ubiquisoft.evaluation.domain.output;

import static com.ubiquisoft.evaluation.domain.Car.PART_TYPE_MUST_NOT_BE_NULL_MSG;

import java.util.Map;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.PartType;

public final class MissingCarPartPrinter {

    public static final String MISSING_PART_DETECTED_MSG = "Missing Part(s) Detected:";
    private final Map<PartType, Integer> missingPartsMap;

    public MissingCarPartPrinter(Car car) {
        this.missingPartsMap = car.getMissingPartsMap();
    }

    public boolean partsMissing() {
        if (missingPartsMapIsNotEmpty())
            return sumOfMissingPartCountsIsGreaterThanZero();
        return false;
    }

    private boolean missingPartsMapIsNotEmpty() {
        return !missingPartsMap.isEmpty();
    }

    private boolean sumOfMissingPartCountsIsGreaterThanZero() {
        return sumOfAllValuesInMap() > 0;
    }

    private int sumOfAllValuesInMap() {
        return missingPartsMap.values().stream().reduce(0, Integer::sum);
    }

    public void printMissingParts() {
        if (partsMissing())
            printEachMissingPart();
    }

    private void printEachMissingPart() {
        for (Map.Entry<PartType, Integer> e : missingPartsMap.entrySet())
            onlyPrintCountsOfMissingParts(e);
    }

    private void onlyPrintCountsOfMissingParts(Map.Entry<PartType, Integer> e) {
        if (missingCount(e) > 0)
            printMissingPart(partType(e), missingCount(e));
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
