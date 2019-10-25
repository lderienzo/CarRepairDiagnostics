package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.CarDiagnosticEngine.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldDataPrinter.MISSING_DATA_FIELD_DETECTED_MSG;
import static com.ubiquisoft.evaluation.enums.ExitCode.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.*;

import org.junit.jupiter.api.*;

import com.google.common.collect.ObjectArrays;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.enums.ExitCode;

class CarDiagnosticEngineTest extends CommonDataPrinterTestMembers {

    private static final String[] EXPECTED_SOME_PARTS_DAMAGED_OUTPUT_PREFIX = {
            BEGIN_DIAGNOSTICS_MSG, BEGIN_CHECK_DATA_FIELDS_MSG, END_CHECK_DATA_FIELDS_MSG,
            BEGIN_CHECK_MISSING_PARTS_MSG, END_CHECK_MISSING_PARTS_MSG, BEGIN_CHECK_DAMAGED_PARTS_MSG };
    private static final String[] EXPECTED_SOME_PARTS_DAMAGED =
            ObjectArrays.concat(EXPECTED_SOME_PARTS_DAMAGED_OUTPUT_PREFIX, EXPECTED_DAMAGED_PARTS, String.class);
    private static final String[] EXPECTED_ALL_PARTS_DAMAGED =
            ObjectArrays.concat(EXPECTED_SOME_PARTS_DAMAGED_OUTPUT_PREFIX, TestConstants.EXPECTED_ALL_PARTS_DAMAGED, String.class);
    private static CarDiagnosticEngine diagnosticEngine;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
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
    void whenXmlValidThenDiagnosticsCompletedSuccessfully() {
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

    @Test
    void whenSomeFieldsMissingThenPrintedToConsoleWithEarlyTermination() {
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
    void whenAllFieldsMissingThenAllFieldsPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_ALL_DATA_FIELDS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(MISSING_DATA_FIELDS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
                BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + EXPECTED_MISSING_ALL_DATA_FIELDS);
    }

    @Test
    void whenSomePartsMissingThenPrintedToConsoleWithEarlyTermination() {
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
    void whenAllPartsMissingThenAllPrintedPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_ALL_PARTS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(MISSING_PARTS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
                BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + END_CHECK_DATA_FIELDS_MSG + "\n" +
                BEGIN_CHECK_MISSING_PARTS_MSG + "\n" + EXPECTED_MISSING_ALL_PARTS);
    }

    @Test
    void whenSomePartsDamagedThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(DAMAGED_PARTS_ERROR_MSG + "\n");
        assertThat(outContentSplitToStringArray()).containsOnly(EXPECTED_SOME_PARTS_DAMAGED);
    }

    @Test
    void whenAllPartsDamagedThenAllDamagedPartsPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_ALL_PARTS_DAMAGED);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(DAMAGED_PARTS_ERROR_MSG + "\n");
        assertThat(outContentSplitToStringArray()).containsOnly(EXPECTED_ALL_PARTS_DAMAGED);
    }

}
