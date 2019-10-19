package com.ubiquisoft.evaluation.domain;

import static com.ubiquisoft.evaluation.domain.Car.DAMAGED_PART_DETECTED_MSG;
import static com.ubiquisoft.evaluation.domain.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.utils.CommonTestMembers;

class PartValidatorTest extends CommonTestMembers {

    private final PartValidator partValidator = new PartValidator();

    @Test
    void whenNoPartsMissingThenTheThereAreMissingPartsMethodReturnsFalse() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when/then
        assertThat(partValidator.thereAreMissingParts(car)).isFalse();
    }

    @Test
    void whenPartsMissingThenTheThereAreMissingPartsMethodReturnsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        // when/then
        assertThat(partValidator.thereAreMissingParts(car)).isTrue();
    }

    @Test
    void whenNoPartsMissingThenNothingPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        partValidator.thereAreMissingParts(car);
        // when
        partValidator.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo("");

        revertSysOutStream();
    }

    @Test
    void whenPartsMissingThenMissingPartsAndCountsPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        partValidator.thereAreMissingParts(car);
        // when
        partValidator.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_PARTS);

        revertSysOutStream();
    }

    @Test
    void whenNoDamagedPartsThenTheThereAreDamagedPartsMethodReturnsFalse() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when/then
        assertThat(partValidator.thereAreDamagedParts(car)).isFalse();
    }

    @Test
    void whenDamagedPartsThenTheThereAreDamagedPartsMethodReturnsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        // when/then
        assertThat(partValidator.thereAreDamagedParts(car)).isTrue();
    }

    @Test
    void whenNoDamagedPartsThenNoPartsPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        partValidator.thereAreDamagedParts(car);
        // when
        partValidator.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEmpty();

        revertSysOutStream();
    }

    @Test
    void whenDamagedPartsThenPartsPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        partValidator.thereAreDamagedParts(car);
        // when
        partValidator.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEqualTo(DAMAGED_PART_DETECTED_MSG + " ENGINE - Condition: USED\n" +
                DAMAGED_PART_DETECTED_MSG + " ELECTRICAL - Condition: NO_POWER\n" +
                DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: FLAT\n" +
                DAMAGED_PART_DETECTED_MSG + " OIL_FILTER - Condition: CLOGGED\n");

        revertSysOutStream();
    }
}
