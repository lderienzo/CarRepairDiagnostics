package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import static com.ubiquisoft.evaluation.domain.PartType.TIRE;

import java.util.*;

import com.ubiquisoft.evaluation.diagnosticdata.CarDiagnosticDataExtractor;
import com.ubiquisoft.evaluation.domain.*;

public class MissingPartDataExtractor implements CarDiagnosticDataExtractor {

    private List<Part> parts;


    @Override
    public MissingPartData extractDiagnosticData(Car car) {
        return new MissingPartData(findMissingParts(car));
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
}
