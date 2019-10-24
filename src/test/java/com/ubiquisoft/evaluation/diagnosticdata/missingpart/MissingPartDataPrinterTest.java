package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingpart.CommonMissingPartDataTestMembers.EXTRACTOR;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.CommonDataPrinterTestMembers;

class MissingPartDataPrinterTest extends CommonDataPrinterTestMembers {

    private static MissingPartDataPrinter printer;


    @Test
    void whenNoPartsMissingInXmlThenNoMissingPartsPrinted() {
        // given
        createPrinter(VALID_XML);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    private void createPrinter(String xmlToUse) {
        createCarFromXml(xmlToUse);
        MissingPartData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        printer = new MissingPartDataPrinter(diagnosticData);
    }

    @Test
    void whenSomePartsMissingInXmlThenMissingPartsPrinted() {
        // given
        createPrinter(INVALID_XML_MISSING_PARTS);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_MISSING_PARTS);
    }

    @Test
    void whenAllPartsMissingInXmlThenAllPartsPrinted() {
        // given
        createPrinter(INVALID_XML_MISSING_ALL_PARTS);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_MISSING_ALL_PARTS);
    }
}
