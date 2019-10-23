package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.domain.PartType.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

final class CarDiagnosticDataExtractorImpl implements CarDiagnosticDataExtractor {

    private List<Part> parts;

    @Override
    public DiagnosticData extractDiagnosticData(Car car) {
        List<String> missingFields = findMissingFields(car);
        Map<PartType, Integer> missingParts = findMissingParts(car);
        Map<PartType, ConditionType> damagedParts = findDamagedParts(car);
        return new DiagnosticData(missingFields, missingParts, damagedParts);
    }

    private List<String> findMissingFields(Car car) {
        List<String> fields = new ArrayList<>();
        if (fieldIsMissing(car.getMake()))
            fields.add("make");
        if (fieldIsMissing(car.getModel()))
            fields.add("model");
        if (fieldIsMissing(car.getYear()))
            fields.add("year");
        return fields;
    }

    private boolean fieldIsMissing(String field) {
        return Strings.isNullOrEmpty(field);
    }

    private Map<PartType, Integer> findMissingParts(Car car) {
        this.parts = car.getParts();
        return createMapOfMissingPartsUsingPartsList();
    }

    private Map<PartType, Integer> createMapOfMissingPartsUsingPartsList() {
        Map<PartType, Integer> m = new EnumMap<>(PartType.class);
        Arrays.stream(PartType.values()).forEach(partType -> {
            int shouldHave = (partType == TIRE ? 4 : 1);
            m.put(partType, determineNumberMissingOfSpecificPartType(shouldHave, partType));});
        return m;
    }

    private int determineNumberMissingOfSpecificPartType(int shouldHave, PartType partType) {
        return subtractNumberShouldHaveFromActual(shouldHave, partType);
    }

    private int subtractNumberShouldHaveFromActual(int shouldHave, PartType partType) {
        return Math.toIntExact(shouldHave - actualNumberPresentInList(partType));
    }

    private long actualNumberPresentInList(PartType partType) {
        return parts.stream().filter(p -> p.getType() == partType).count();
    }

    private Map<PartType, ConditionType> findDamagedParts(Car car) {
        Map<PartType, ConditionType> map = new EnumMap<>(PartType.class);
        for (Part part : car.getParts())
            addPartToMapIfNotWorking(part, map);
        return map;
    }

    private void addPartToMapIfNotWorking(Part part, Map<PartType, ConditionType> map) {
        if (part.isNotInWorkingCondition())
            map.put(part.getType(), part.getCondition());
    }
}
