package com.ubiquisoft.evaluation.domain;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

public class CarTest {
    private static final String INVALID_XML_FILE = "SampleCar-incomplete-parts-list.xml";

    // TODO - BELOW REPEATED
    private static final String VALID_XML_FILE = "SampleCar.xml";
    private static CarCreator carCreator = new CarCreator();

    @Test
    public void whenCarXmlContainsAllPartsThenAllValuesOfMissingPartsMapAreZero() {
        // given
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        // when/then
        assertThat(missingPartCounts(car)).containsExactly(0,0,0,0,0);
    }

    private Collection<Integer> missingPartCounts(Car car) {
        Map<PartType, Integer> missingPartsMap = car.getMissingPartsMap();
        return missingPartsMap.values();
    }

    @Test
    public void whenCarXmlIsMissingPartsThenMissingPartsMapContainsNonZeroValuesForPartsMissing() {
        // given
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        // when/then
        assertThat(missingPartCounts(car)).containsExactlyInAnyOrder(1,1,2,0,0);
    }
}
