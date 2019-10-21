package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;

final class CarDiagnosticDataValidatorImplTest extends CommonTestMembers {

    private static CarDiagnosticDataValidator diagnosticDataValidator;


    @Test
    void whenXmlContainsAllValidDataThenValidatorReportsThis() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // when
        diagnosticDataValidator = new CarDiagnosticDataValidatorImpl(diagnosticData);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isFalse();
        assertThat(diagnosticDataValidator.hasMissingParts()).isFalse();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isFalse();
    }

    @Test
    void whenXmlHasMissingDataFieldsThenHasMissingDataFieldsIsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // when
        diagnosticDataValidator = new CarDiagnosticDataValidatorImpl(diagnosticData);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isTrue();
        assertThat(diagnosticDataValidator.hasMissingParts()).isFalse();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isFalse();
    }

    @Test
    void whenXmlHasMissingPartsThenHasMissingPartsIsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // when
        diagnosticDataValidator = new CarDiagnosticDataValidatorImpl(diagnosticData);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isFalse();
        assertThat(diagnosticDataValidator.hasMissingParts()).isTrue();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isFalse();
    }

    @Test
    void whenXmlHasDamagedPartsThenHasDamagedPartsIsTrue() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // when
        diagnosticDataValidator = new CarDiagnosticDataValidatorImpl(diagnosticData);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isFalse();
        assertThat(diagnosticDataValidator.hasMissingParts()).isFalse();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isTrue();
    }

}
