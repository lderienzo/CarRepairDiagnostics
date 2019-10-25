package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;


class DamagedPartDataValidatorTest {

    private DamagedPartDataValidator validator;


    @Test
    void whenNoPartsDamagedInXmlThenDiagnosticDataValid() {
        // given
        DamagedPartData diagnosticData = getDiagnosticData(VALID_XML);
        // when
        validator = new DamagedPartDataValidator(diagnosticData);
        // then
        assertThat(validator.isValid()).isTrue();
    }

    private DamagedPartData getDiagnosticData(String xmlToUse) {
        createCarFromXml(xmlToUse);
        DamagedPartDataExtractor extractor = new DamagedPartDataExtractor();
        return extractor.extractDiagnosticData(car);
    }

    @Test
    void whenSomePartsDamagedInXmlThenDiagnosticDataInvalid() {
        // given
        DamagedPartData diagnosticData = getDiagnosticData(INVALID_XML_DAMAGED_PARTS);
        // when
        validator = new DamagedPartDataValidator(diagnosticData);
        // then
        assertThat(validator.isValid()).isFalse();
    }

    @Test
    void whenAllPartsDamagedInXmlThenDiagnosticDataInvalid() {
        // given
        DamagedPartData diagnosticData = getDiagnosticData(INVALID_XML_ALL_PARTS_DAMAGED);
        // when
        validator = new DamagedPartDataValidator(diagnosticData);
        // then
        assertThat(validator.isValid()).isFalse();
    }
}
