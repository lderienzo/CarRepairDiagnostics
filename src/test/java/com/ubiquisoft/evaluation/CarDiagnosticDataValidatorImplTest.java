package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

final class CarDiagnosticDataValidatorImplTest extends CommonTestMembers {

    private static CarDiagnosticDataValidator diagnosticDataValidator;

    @Test
    void whenXmlContainsAllValidDataThenValidatorReportsThis() {
        // given/when
        setUpDiagnosticDataValidator(VALID_XML);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isFalse();
        assertThat(diagnosticDataValidator.hasMissingParts()).isFalse();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isFalse();
    }

    private void setUpDiagnosticDataValidator(String xmlToUse) {
        extractDiagnosticData(xmlToUse);
        diagnosticDataValidator = new CarDiagnosticDataValidatorImpl(diagnosticData);
    }

    @Test
    void whenXmlHasMissingDataFieldsThenHasMissingDataFieldsIsTrue() {
        // given/when
        setUpDiagnosticDataValidator(INVALID_XML_MISSING_DATA_FIELDS);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isTrue();
        assertThat(diagnosticDataValidator.hasMissingParts()).isFalse();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isFalse();
    }

    @Test
    void whenXmlHasMissingPartsThenHasMissingPartsIsTrue() {
        // given/when
        setUpDiagnosticDataValidator(INVALID_XML_MISSING_PARTS);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isFalse();
        assertThat(diagnosticDataValidator.hasMissingParts()).isTrue();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isFalse();
    }

    @Test
    void whenXmlHasDamagedPartsThenHasDamagedPartsIsTrue() {
        // given/when
        setUpDiagnosticDataValidator(INVALID_XML_DAMAGED_PARTS);
        // then
        assertThat(diagnosticDataValidator.hasMissingDataFields()).isFalse();
        assertThat(diagnosticDataValidator.hasMissingParts()).isFalse();
        assertThat(diagnosticDataValidator.hasDamagedParts()).isTrue();
    }

}
