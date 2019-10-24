package com.ubiquisoft.evaluation.diagnosticdata.missingfield;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.CommonMissingFieldDataTestMembers.EXTRACTOR;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldDataPrinter.MISSING_DATA_FIELD_DETECTED_MSG;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.CommonDataPrinterTestMembers;

class MissingFieldDataPrinterTest extends CommonDataPrinterTestMembers {

    private static MissingFieldDataPrinter printer;


    @Test
    void whenNoMissingFieldsInXmlThenNothingPrinted() {
        // given
        createPrinter(VALID_XML);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    private void createPrinter(String xmlToUse) {
        createCarFromXml(xmlToUse);
        MissingFieldData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        printer = new MissingFieldDataPrinter(diagnosticData);
    }

    @Test
    void whenSomeMissingFieldsInXmlThenMissingFieldsPrinted() {
        // given
        createPrinter(INVALID_XML_MISSING_DATA_FIELDS);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_DATA_FIELD_DETECTED_MSG + " make\n");
    }

    @Test
    void whenAllFieldsMissingInXmlThenAllMissingFieldsPrinted() {
        // given
        createPrinter(INVALID_XML_MISSING_ALL_DATA_FIELDS);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_MISSING_ALL_DATA_FIELDS);
    }
}
