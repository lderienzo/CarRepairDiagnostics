package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.domain.validation.MissingCarDataFieldValidator.MISSING_DATA_FIELD_MSG;
import static com.ubiquisoft.evaluation.utils.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.utils.CommonTestMembers;

class MissingCarDataFieldValidatorTest extends CommonTestMembers {

    private final MissingCarDataFieldValidator validator = new MissingCarDataFieldValidator();

    @Test
    void whenAllFieldsPresentThenValidWithEmptyList() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when
        List<String> missingFields = validator.findMissingFields(car);
        // then
        assertThat(missingFields).isEmpty();
    }

    @Test
    void whenMissingFieldThenInvalidWithNonEmptyList() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        // when
        List<String> missingFields = validator.findMissingFields(car);
        // then
        assertThat(missingFields.size()).isEqualTo(1);
        assertThat(missingFields.get(0)).isEqualTo("make");
    }

    @Test
    void whenNoMissingFieldsThenNoneToPrint() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        validator.findMissingFields(car);
        // when
        validator.printMissingFields();
        // then
        assertThat(outContent.toString()).isEmpty();

        revertSysOutStream();
    }

    @Test
    void whenMissingFieldsThenFieldsToPrint() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        validator.findMissingFields(car);
        // when
        validator.printMissingFields();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_DATA_FIELD_MSG + " make\n");

        revertSysOutStream();
    }
}
