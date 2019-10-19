package com.ubiquisoft.evaluation.domain;


import static com.ubiquisoft.evaluation.domain.Car.MISSING_PART_DETECTED_MSG;

import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

public final class TestConstants {

    public static final String INVALID_XML_MISSING_DATA_FIELDS = "SampleCar-missing-data-fields.xml";
    public static final String INVALID_XML_MISSING_PARTS = "SampleCar-incomplete-parts-list.xml";
    public static final String INVALID_XML_DAMAGED_PARTS = "SampleCar.xml";
    public static final String VALID_XML = "SampleCar-all-diagnostics-valid.xml";
    public static final String MISSING_PARTS = MISSING_PART_DETECTED_MSG + " ENGINE - Count: 1\n" +
            MISSING_PART_DETECTED_MSG + " TIRE - Count: 2\n" + MISSING_PART_DETECTED_MSG + " FUEL_FILTER - Count: 1\n";
    public static final CarCreator CAR_CREATOR = new CarCreator();

}
