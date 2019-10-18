package com.ubiquisoft.evaluation.domain.validation;

import java.util.ArrayList;
import java.util.List;

import com.google.common.base.Strings;
import com.ubiquisoft.evaluation.domain.Car;

public final class MissingCarDataFieldValidator {

    public static final String MISSING_DATA_FIELD_MSG = "Missing Data Field(s) Detected:";
    private final List<String> missingFields = new ArrayList<>();

    public List<String> findMissingFields(Car car) {
        if (fieldIsMissing(car.getMake()))
            missingFields.add("make");
        if (fieldIsMissing(car.getModel()))
            missingFields.add("model");
        if (fieldIsMissing(car.getYear()))
            missingFields.add("year");
        return missingFields;
    }

    private boolean fieldIsMissing(String field) {
        return Strings.isNullOrEmpty(field);
    }

    public void printMissingFields() {
            missingFields.forEach(field -> System.out.println(
                        String.format(MISSING_DATA_FIELD_MSG + " %s", field)));
    }
}
