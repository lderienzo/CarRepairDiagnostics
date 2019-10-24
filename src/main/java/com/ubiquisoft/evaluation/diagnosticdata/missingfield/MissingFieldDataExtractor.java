package com.ubiquisoft.evaluation.diagnosticdata.missingfield;

import java.util.*;

import com.google.common.base.Strings;
import com.ubiquisoft.evaluation.diagnosticdata.CarDiagnosticDataExtractor;
import com.ubiquisoft.evaluation.domain.Car;

public class MissingFieldDataExtractor implements CarDiagnosticDataExtractor {

    @Override
    public MissingFieldData extractDiagnosticData(Car car) {
        return new MissingFieldData(findMissingFields(car));
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
}
