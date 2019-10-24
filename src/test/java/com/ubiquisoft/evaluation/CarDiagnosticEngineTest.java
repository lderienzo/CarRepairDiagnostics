package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.CarDiagnosticEngine.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldDataPrinter.MISSING_DATA_FIELD_DETECTED_MSG;
import static com.ubiquisoft.evaluation.enums.ExitCode.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.*;

import org.junit.jupiter.api.*;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.enums.ExitCode;

class CarDiagnosticEngineTest extends CommonDataPrinterTestMembers {

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static CarDiagnosticEngine diagnosticEngine;
    private PrintStream sysErr;

    @BeforeEach
    void setUpStreams() {
        redirectSystemOutputToOutContent();
        redirectSystemErrorToErrContent();
    }

    private void redirectSystemErrorToErrContent() {
        sysErr = System.err;
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void revertStreams() {
        resetSystemOutput();
        resetSystemError();
    }

    private void resetSystemError() {
        System.setErr(sysErr);
    }

    @Test
    void whenMissingFieldsThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(MISSING_DATA_FIELDS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
            BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + MISSING_DATA_FIELD_DETECTED_MSG + " make\n");
    }

    @Test
    void whenMissingPartsThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(MISSING_PARTS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
                        BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + END_CHECK_DATA_FIELDS_MSG + "\n" +
                BEGIN_CHECK_MISSING_PARTS_MSG + "\n" + EXPECTED_MISSING_PARTS);
    }

    @Test
    void whenDamagedPartsThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(DAMAGED_PARTS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
                BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + END_CHECK_DATA_FIELDS_MSG + "\n" +
                BEGIN_CHECK_MISSING_PARTS_MSG + "\n" + END_CHECK_MISSING_PARTS_MSG + "\n" +
                BEGIN_CHECK_DAMAGED_PARTS_MSG + "\n" + EXPECTED_DAMAGED_PARTS);
    }

    @Test
    void whenAllAspectsOfXmlValidThenDiagnosticsCompleted() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(OK);
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
                BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + END_CHECK_DATA_FIELDS_MSG + "\n" +
                BEGIN_CHECK_MISSING_PARTS_MSG + "\n" + END_CHECK_MISSING_PARTS_MSG + "\n" +
                BEGIN_CHECK_DAMAGED_PARTS_MSG + "\n" + END_CHECK_DAMAGED_PARTS_MSG + "\n" +
                END_DIAGNOSTICS_SUCCESSFUL_MSG + "\n");
    }
}
