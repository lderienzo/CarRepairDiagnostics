package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.CarDiagnosticEngine.*;
import static com.ubiquisoft.evaluation.domain.Car.*;
import static com.ubiquisoft.evaluation.enums.ExitCode.*;
import static com.ubiquisoft.evaluation.domain.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.enums.ExitCode;
import com.ubiquisoft.evaluation.utils.CommonTestMembers;

class CarDiagnosticEngineTest extends CommonTestMembers {

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private static CarDiagnosticEngine diagnosticEngine;
    private PrintStream sysErr;

    @BeforeEach
    void setUpStreams() {
        setUpSysOutStream();
        sysErr = System.err;
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    void revertStreams() {
        revertSysOutStream();
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
            BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + MISSING_DATA_FIELD_MSG + " make\n");
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
                BEGIN_CHECK_MISSING_PARTS_MSG + "\n" + MISSING_PARTS);
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
                BEGIN_CHECK_DAMAGED_PARTS_MSG + "\n" +
                DAMAGED_PART_DETECTED_MSG + " ENGINE - Condition: USED\n" +
                DAMAGED_PART_DETECTED_MSG + " ELECTRICAL - Condition: NO_POWER\n" +
                DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: FLAT\n" +
                DAMAGED_PART_DETECTED_MSG + " OIL_FILTER - Condition: CLOGGED\n");
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
