package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.damagedpart.CommonDamagedPartDataTestMembers.EXTRACTOR;
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
        DamagedPartData diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        printer = new DamagedPartDataPrinter(diagnosticData);
    }

    @Test
    void whenSomeDamagedPartsInXmlThenPartsPrinted() {
        // given
        createPrinter(INVALID_XML_DAMAGED_PARTS);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_DAMAGED_PARTS);
    }

    @Disabled
    @Test   // TODO - bug, cant print multiple damaged tires
    void whenAllPartsDamagedInXmlThenAllPartsPrinted() {
        // given
        createPrinter(INVALID_XML_ALL_PARTS_DAMAGED);
        // when
        printer.printDiagnosticData();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_ALL_PARTS_DAMAGED);
    }
}
