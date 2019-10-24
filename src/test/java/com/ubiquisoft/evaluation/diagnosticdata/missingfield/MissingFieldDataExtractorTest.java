package com.ubiquisoft.evaluation.diagnosticdata.missingfield;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.CommonMissingFieldDataTestMembers.EXTRACTOR;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Test;

class MissingFieldDataExtractorTest {

    private static MissingFieldData extractedData;


    @Test
    void whenNoMissingFieldsInXmlThenNoMissingFieldDataExtracted() {
        // given/when
        extractedData(VALID_XML);
        // then
        assertThat(extractedData.getMissingFields()).isEmpty();
    }

    private void extractedData(String xmlToUse) {
        // given
        createCarFromXml(xmlToUse);
        // when
        extractedData = EXTRACTOR.extractDiagnosticData(car);
    }

    @Test
    void whenSomeMissingFieldsInXmlThenFieldDataExtracted() {
        // given/when
        extractedData(INVALID_XML_MISSING_DATA_FIELDS);
        // then
        assertThat(extractedData.getMissingFields()).hasSize(1).containsOnly("make");
    }

    @Test
    void whenAllFieldsMissingInXmlThenAllFieldDataExtracted() {
        extractedData(INVALID_XML_MISSING_ALL_DATA_FIELDS);
        // then
        assertThat(extractedData.getMissingFields()).hasSize(3).containsExactly("make","model","year");
    }
}
