package com.ubiquisoft.evaluation.domain;

import static com.ubiquisoft.evaluation.domain.Car.MISSING_DATA_FIELD_MSG;
import static com.ubiquisoft.evaluation.domain.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.Collection;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.utils.CommonTestMembers;

class CarTest extends CommonTestMembers {

    @Test
    void whenCarXmlContainsAllPartsThenAllValuesOfMissingPartsMapAreZero() {
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
    void whenCarXmlIsMissingPartsThenMissingPartsMapContainsNonZeroValuesForPartsMissing() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        // when/then
        assertThat(missingPartCounts(car)).containsExactlyInAnyOrder(1,1,2,0,0);
    }

    @Test
    void whenAllFieldsPresentThenHasMissingDataFieldsReturnsFalse() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when/then
        assertThat(car.hasMissingDataFields()).isFalse();
    }

    @Test
    void whenMissingFieldThenHasMissingDataFieldsReturnsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        // when/then
        assertThat(car.hasMissingDataFields()).isTrue();
    }

    @Test
    void whenNoMissingFieldsThenNonePrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        car.hasMissingDataFields();
        // when
        car.printMissingDataFields();
        // then
        assertThat(outContent.toString()).isEmpty();

        revertSysOutStream();
    }

    @Test
    void whenMissingFieldsThenFieldsToPrint() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        car.hasMissingDataFields();
        // when
        car.printMissingDataFields();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_DATA_FIELD_MSG + " make\n");

        revertSysOutStream();
    }
}
