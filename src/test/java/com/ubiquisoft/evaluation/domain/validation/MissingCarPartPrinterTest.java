package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.utils.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.output.MissingCarPartPrinter;
import com.ubiquisoft.evaluation.utils.CommonTestMembers;

class MissingCarPartPrinterTest extends CommonTestMembers {

    private MissingCarPartPrinter missingCarPartPrinter;

    @Test
    void whenNoPartsMissingThenPartsMissingReturnsFalse() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when/then
        assertThat(missingCarPartPrinter.partsMissing()).isFalse();
    }

    @Test
    void whenPartsMissingThenPartsMissingReturnsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when/then
        assertThat(missingCarPartPrinter.partsMissing()).isTrue();
    }

    @Test
    void whenNoPartsMissingThenNothingPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when
        missingCarPartPrinter.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo("");

        revertSysOutStream();
    }

    @Test
    void whenPartsMissingThenMissingPartsAndCountPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when
        missingCarPartPrinter.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_PARTS);

        revertSysOutStream();
    }
}
