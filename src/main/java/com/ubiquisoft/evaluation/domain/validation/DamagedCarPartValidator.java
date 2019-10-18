package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.domain.Car.PART_TYPE_MUST_NOT_BE_NULL_MSG;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

public class DamagedCarPartValidator {

    private Map<PartType, ConditionType> damagedPartsMap = new EnumMap<>(PartType.class);
    public static final String DAMAGED_PART_DETECTED_MSG = "Damaged Part Detected:";

    public Map<PartType, ConditionType> getDamagedPartsMap(Car car) {
        findDamagedParts(car.getParts());
        return damagedPartsMap;
    }

    private void findDamagedParts(List<Part> parts) {
        for (Part part : parts)
            addPartToMapIfNotWorking(part);
    }

    private void addPartToMapIfNotWorking(Part part) {
        if (part.isNotInWorkingCondition())
            damagedPartsMap.put(part.getType(), part.getCondition());
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
