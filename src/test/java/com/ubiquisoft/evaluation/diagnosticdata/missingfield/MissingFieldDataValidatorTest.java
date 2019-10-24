package com.ubiquisoft.evaluation.diagnosticdata.missingfield;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.CommonMissingFieldDataTestMembers.EXTRACTOR;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;


class MissingFieldDataValidatorTest {

    private MissingFieldDataValidator validator;


    @Test
    void whenNoMissingFieldsInXmlThenDiagnosticDataValid() {
        // given/when
        createValidator(VALID_XML);
        // then
        assertThat(validator.isValid()).isTrue();
    }

    private void createValidator(String xmlToUse) {
        // given
        createCarFromXml(xmlToUse);
        MissingFieldData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // when
        validator = new MissingFieldDataValidator(diagnosticData);
    }

    @Test
    void whenSomeMissingFieldsInXmlThenDiagnosticDataInvalid() {
        // given/when
        createValidator(INVALID_XML_MISSING_DATA_FIELDS);
        // then
        assertThat(validator.isValid()).isFalse();
    }

    @Test
    void whenAllFieldsMissingInXmlThenDiagnosticDataInvalid() {
        // given/when
        createValidator(INVALID_XML_MISSING_ALL_DATA_FIELDS);
        // then
        assertThat(validator.isValid()).isFalse();
    }
}
