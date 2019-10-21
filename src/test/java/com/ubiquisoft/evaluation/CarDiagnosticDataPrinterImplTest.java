package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.CarDiagnosticDataPrinterImpl.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;

final class CarDiagnosticDataPrinterImplTest extends CommonTestMembers {

    private static CarDiagnosticDataPrinter diagnosticDataPrinter;

    @BeforeEach
    void setUpStreams() {
        redirectSystemOutputToOutContent();
    }

    @AfterEach
    void revertStreams() {
        resetSystemOutput();
    }

    @Test
    void whenXmlContainsAllValidDataThenPrintMissingDataFieldsPrintsNothing() {
        // given
        setUpDiagnosticDataPrinter(VALID_XML);
        // when
        diagnosticDataPrinter.printMissingDataFields();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    private void setUpDiagnosticDataPrinter(String xmlToUse) {
        Car car = CAR_CREATOR.createFromXml(xmlToUse);
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        diagnosticDataPrinter = new CarDiagnosticDataPrinterImpl(diagnosticData);
    }

    @Test
    void whenXmlContainsAllValidDataThenPrintMissingPartsPrintsNothing() {
        // given
        setUpDiagnosticDataPrinter(VALID_XML);
        // when
        diagnosticDataPrinter.printMissingParts();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    @Test
    void whenXmlContainsAllValidDataThenPrintDamagedPartsPrintsNothing() {
        // given
        setUpDiagnosticDataPrinter(VALID_XML);
        // when
        diagnosticDataPrinter.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    @Test
    void whenXmlHasMissingDataFieldsThenPrintMissingDataFieldsPrintsThem() {
        // given
        setUpDiagnosticDataPrinter(INVALID_XML_MISSING_DATA_FIELDS);
        // when
        diagnosticDataPrinter.printMissingDataFields();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_DATA_FIELD_DETECTED_MSG + " make\n");
    }

    @Test
    void whenXmlHasMissingPartsThenPrintMissingPartsPrintsThem() {
        // given
        setUpDiagnosticDataPrinter(INVALID_XML_MISSING_PARTS);
        // when
        diagnosticDataPrinter.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_MISSING_PARTS);
    }

    @Test
    void whenXmlHasDamagedPartsThenDamagedPartsPrintsThem() {
        // given
        setUpDiagnosticDataPrinter(INVALID_XML_DAMAGED_PARTS);
        // when
        diagnosticDataPrinter.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEqualTo(EXPECTED_DAMAGED_PARTS);
    }
}
