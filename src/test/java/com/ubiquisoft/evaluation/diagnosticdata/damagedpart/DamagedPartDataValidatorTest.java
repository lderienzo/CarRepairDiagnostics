package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.damagedpart.CommonDamagedPartDataTestMembers.EXTRACTOR;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;


class DamagedPartDataValidatorTest {

    private DamagedPartDataValidator validator;


    @Test
    void whenNoDamagedPartsInXmlThenDiagnosticDataValid() {
        // given
        createCarFromXml(VALID_XML);
        DamagedPartData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // when
        validator = new DamagedPartDataValidator(diagnosticData);
        // then
        assertThat(validator.isValid()).isTrue();
    }

    @Test
    void whenDamagedPartsInXmlThenDiagnosticDataInvalid() {
        // given
        createCarFromXml(INVALID_XML_DAMAGED_PARTS);
        DamagedPartData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // when
        validator = new DamagedPartDataValidator(diagnosticData);
        // then
        assertThat(validator.isValid()).isFalse();
    }
}
