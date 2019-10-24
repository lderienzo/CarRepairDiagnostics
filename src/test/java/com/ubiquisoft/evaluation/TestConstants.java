package com.ubiquisoft.evaluation;


import static com.ubiquisoft.evaluation.diagnosticdata.damagedpart.DamagedPartDataPrinter.DAMAGED_PART_DETECTED_MSG;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldDataPrinter.MISSING_DATA_FIELD_DETECTED_MSG;
import static com.ubiquisoft.evaluation.diagnosticdata.missingpart.MissingPartDataPrinter.MISSING_PART_DETECTED_MSG;

import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

public final class TestConstants {

    public static final String INVALID_XML_MISSING_DATA_FIELDS = "SampleCar-missing-data-fields.xml";
    public static final String INVALID_XML_MISSING_ALL_DATA_FIELDS = "SampleCar-all-data-fields-missing.xml";
    public static final String INVALID_XML_MISSING_PARTS = "SampleCar-missing-parts.xml";
    public static final String INVALID_XML_MISSING_ALL_PARTS = "SampleCar-missing-all-parts.xml";
    public static final String INVALID_XML_DAMAGED_PARTS = "SampleCar.xml";
    public static final String INVALID_XML_ALL_PARTS_DAMAGED = "SampleCar-all-parts-damaged.xml";
    public static final String VALID_XML = "SampleCar-all-diagnostics-valid.xml";
    public static final String EXPECTED_MISSING_ALL_DATA_FIELDS =
            MISSING_DATA_FIELD_DETECTED_MSG + " make\n" +
            MISSING_DATA_FIELD_DETECTED_MSG + " model\n" +
            MISSING_DATA_FIELD_DETECTED_MSG + " year\n";
    public static final String EXPECTED_MISSING_PARTS =
            MISSING_PART_DETECTED_MSG + " ENGINE - Count: 1\n" +
            MISSING_PART_DETECTED_MSG + " TIRE - Count: 2\n" +
            MISSING_PART_DETECTED_MSG + " FUEL_FILTER - Count: 1\n";
    public static final String EXPECTED_MISSING_ALL_PARTS =
            MISSING_PART_DETECTED_MSG + " ENGINE - Count: 1\n" +
            MISSING_PART_DETECTED_MSG + " ELECTRICAL - Count: 1\n" +
            MISSING_PART_DETECTED_MSG + " TIRE - Count: 4\n" +
            MISSING_PART_DETECTED_MSG + " FUEL_FILTER - Count: 1\n" +
            MISSING_PART_DETECTED_MSG + " OIL_FILTER - Count: 1\n";
    public static final String EXPECTED_DAMAGED_PARTS =
            DAMAGED_PART_DETECTED_MSG + " ENGINE - Condition: USED\n" +
            DAMAGED_PART_DETECTED_MSG + " ELECTRICAL - Condition: NO_POWER\n" +
            DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: FLAT\n" +
            DAMAGED_PART_DETECTED_MSG + " OIL_FILTER - Condition: CLOGGED\n";
    public static final String EXPECTED_ALL_PARTS_DAMAGED =
            DAMAGED_PART_DETECTED_MSG + " ENGINE - Condition: USED\n" +
            DAMAGED_PART_DETECTED_MSG + " ELECTRICAL - Condition: USED\n" +
            DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: WORN\n" +
            DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: USED\n" +
            DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: FLAT\n" +
            DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: DAMAGED\n" +
            DAMAGED_PART_DETECTED_MSG + " FUEL_FILTER - Condition: USED\n" +
            DAMAGED_PART_DETECTED_MSG + " OIL_FILTER - Condition: USED\n";
    public static final CarCreator CAR_CREATOR = new CarCreator();
}
