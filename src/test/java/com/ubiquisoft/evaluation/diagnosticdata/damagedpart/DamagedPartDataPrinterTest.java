package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.*;

import com.ubiquisoft.evaluation.CommonDataPrinterTestMembers;

class DamagedPartDataPrinterTest extends CommonDataPrinterTestMembers {

    private DamagedPartDataPrinter printer;


    @Test
    void whenNoDamagedPartsInXmlThenNothingPrinted() {
        // given
        createPrinter(VALID_XML);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    private void createPrinter(String xmlToUse) {
        createCarFromXml(xmlToUse);
        DamagedPartData diagnosticData = new DamagedPartDataExtractor().extractDiagnosticData(car);
        printer = new DamagedPartDataPrinter(diagnosticData);
    }

    @Test
    void whenSomeDamagedPartsInXmlThenPartsPrinted() {
        // given
        createPrinter(INVALID_XML_DAMAGED_PARTS);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContentSplitToStringArray()).containsOnly(EXPECTED_DAMAGED_PARTS);
    }

    @Test
    void whenAllPartsDamagedInXmlThenAllPartsPrinted() {
        // given
        createPrinter(INVALID_XML_ALL_PARTS_DAMAGED);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContentSplitToStringArray()).containsOnly(EXPECTED_ALL_PARTS_DAMAGED);
    }
}
