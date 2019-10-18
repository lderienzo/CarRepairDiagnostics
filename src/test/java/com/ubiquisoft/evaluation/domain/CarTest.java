package com.ubiquisoft.evaluation.domain;

import static com.ubiquisoft.evaluation.utils.TestConstants.CAR_CREATOR;
import static com.ubiquisoft.evaluation.utils.TestConstants.INVALID_XML_MISSING_PARTS;
import static com.ubiquisoft.evaluation.utils.TestConstants.VALID_XML;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class CarTest {

    @Test
    public void whenCarXmlContainsAllPartsThenAllValuesOfMissingPartsMapAreZero() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
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
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        // when/then
        assertThat(missingPartCounts(car)).containsExactlyInAnyOrder(1,1,2,0,0);
    }
}
