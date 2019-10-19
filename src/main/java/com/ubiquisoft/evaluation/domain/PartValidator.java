package com.ubiquisoft.evaluation.domain;

import static com.ubiquisoft.evaluation.domain.Car.*;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

final class PartValidator {

    private static final String PART_TYPE_MUST_NOT_BE_NULL_MSG = "PartType must not be null";

    private final Map<PartType, ConditionType> damagedPartsMap = new EnumMap<>(PartType.class);
    private Map<PartType, Integer> missingPartsMap;


    public boolean thereAreMissingParts(Car car) {
        this.missingPartsMap = car.getMissingPartsMap();
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
        for (Map.Entry<PartType, Integer> e : missingPartsMap.entrySet())
            printOnlyIfMissingCountGreaterThanZero(e);
    }

    private void printOnlyIfMissingCountGreaterThanZero(Map.Entry<PartType, Integer> e) {
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

    public boolean thereAreDamagedParts(Car car) {
        findDamagedParts(car.getParts());
        return damagedPartsMapIsNotEmpty();
    }

    private void findDamagedParts(List<Part> parts) {
        for (Part part : parts)
            addPartToMapIfNotWorking(part);
    }

    private void addPartToMapIfNotWorking(Part part) {
        if (part.isNotInWorkingCondition())
            damagedPartsMap.put(part.getType(), part.getCondition());
    }

    private boolean damagedPartsMapIsNotEmpty() {
        return !damagedPartsMap.isEmpty();
    }

    public void printDamagedParts() {
        for (Map.Entry<PartType, ConditionType> e : damagedPartsMap.entrySet())
            printDamagedPart(e.getKey(), e.getValue());
    }

    private void printDamagedPart(PartType partType, ConditionType condition) {
        if (partType == null) throw new IllegalArgumentException(PART_TYPE_MUST_NOT_BE_NULL_MSG);
        if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

        System.out.println(String.format(DAMAGED_PART_DETECTED_MSG + " %s - Condition: %s", partType, condition));
    }
}
