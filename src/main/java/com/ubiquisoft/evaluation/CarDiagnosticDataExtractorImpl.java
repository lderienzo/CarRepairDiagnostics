package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.domain.PartType.*;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;

final class CarDiagnosticDataExtractorImpl implements CarDiagnosticDataExtractor {

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
        return createMapOfMissingPartsFromPartsList(car.getParts());
    }

    private Map<PartType, Integer> createMapOfMissingPartsFromPartsList(List<Part> parts) {
        Map<PartType, Integer> m = new EnumMap<>(PartType.class);
        m.put(ENGINE, determineNumberMissingOfSpecificPartType(1, ENGINE, parts));
        m.put(ELECTRICAL, determineNumberMissingOfSpecificPartType(1, ELECTRICAL, parts));
        m.put(FUEL_FILTER, determineNumberMissingOfSpecificPartType(1, FUEL_FILTER, parts));
        m.put(OIL_FILTER, determineNumberMissingOfSpecificPartType(1, OIL_FILTER, parts));
        m.put(TIRE, determineNumberMissingOfSpecificPartType(4, TIRE, parts));
        return m;
    }

    private int determineNumberMissingOfSpecificPartType(int shouldHave, PartType partType, List<Part> parts) {
        return subtractNumberShouldHaveFromActual(shouldHave, partType, parts);
    }

    private int subtractNumberShouldHaveFromActual(int shouldHave, PartType partType, List<Part> parts ) {
        return Math.toIntExact(shouldHave - actualNumberPresentInList(partType, parts));
    }

    private long actualNumberPresentInList(PartType partType, List<Part> parts ) {
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
