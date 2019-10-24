package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingpart.CommonMissingPartDataTestMembers.EXTRACTOR;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MissingPartDataValidatorTest {

    private MissingPartDataValidator validator;


    @Test
    void whenNoMissingPartsInXmlThenDiagnosticDataValid() {
        // given/when
        createValidator(VALID_XML);
        // then
        assertThat(validator.isValid()).isTrue();
    }

    private void createValidator(String xmlToUse) {
        // given
        createCarFromXml(xmlToUse);
        MissingPartData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // when
        validator = new MissingPartDataValidator(diagnosticData);
    }

    @Test
    void whenSomeMissingPartsInXmlThenDiagnosticDataInvalid() {
        // given/when
        createValidator(INVALID_XML_MISSING_PARTS);
        // then
        assertThat(validator.isValid()).isFalse();
    }

    @Test
    void whenAllPartsMissingInXmlThenDiagnosticDataInvalid() {
        // given/when
        createValidator(INVALID_XML_MISSING_ALL_PARTS);
        // then
        assertThat(validator.isValid()).isFalse();
    }
}
