package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.CarDiagnosticEngine.*;
import static com.ubiquisoft.evaluation.domain.output.MissingCarPartPrinter.MISSING_PART_DETECTED_MSG;
import static com.ubiquisoft.evaluation.domain.validation.DamagedCarPartValidator.DAMAGED_PART_DETECTED_MSG;
import static com.ubiquisoft.evaluation.domain.validation.MissingCarDataFieldValidator.MISSING_DATA_FIELD_MSG;
import static com.ubiquisoft.evaluation.enums.ExitCode.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;
import com.ubiquisoft.evaluation.enums.ExitCode;

public class CarDiagnosticEngineTest {

    private static CarDiagnosticEngine diagnosticEngine;
    private static final String VALID_XML = "SampleCar-all-diagnostics-valid.xml";

    // TODO - BELOW REPEATED
    private static final String INVALID_XML_MISSING_DATA_FIELDS = "SampleCar-missing-data-fields.xml";
    private static final String INVALID_XML_MISSING_PARTS = "SampleCar-incomplete-parts-list.xml";
    private static final String INVALID_XML_DAMAGED_PARTS = "SampleCar.xml"; // TODO -- REPEATED damaged parts

    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream sysErr;
    private static CarCreator carCreator = new CarCreator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;


    @BeforeEach
    public void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
        sysErr = System.err;
        System.setErr(new PrintStream(errContent));
    }

    @AfterEach
    public void revertStreams() {
        System.setOut(sysOut);
        System.setErr(sysErr);
    }

    @Test
    public void whenMissingFieldsThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = carCreator.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(MISSING_DATA_FIELDS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
            BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + MISSING_DATA_FIELD_MSG + " make\n");
    }

    @Test
    public void whenMissingPartsThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = carCreator.createFromXml(INVALID_XML_MISSING_PARTS);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(MISSING_PARTS_ERROR_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo(BEGIN_DIAGNOSTICS_MSG + "\n" +
                        BEGIN_CHECK_DATA_FIELDS_MSG + "\n" + END_CHECK_DATA_FIELDS_MSG + "\n" +
                BEGIN_CHECK_MISSING_PARTS_MSG + "\n" +
                MISSING_PART_DETECTED_MSG + " ENGINE - Count: 1\n" +
                MISSING_PART_DETECTED_MSG + " TIRE - Count: 2\n" +
                MISSING_PART_DETECTED_MSG + " FUEL_FILTER - Count: 1\n");
    }

    @Test
    public void whenDamagedPartsThenPrintedToConsoleWithEarlyTermination() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = carCreator.createFromXml(INVALID_XML_DAMAGED_PARTS);
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
    public void whenAllAspectsOfXmlValidThenDiagnosticsCompleted() {
        // given
        diagnosticEngine = new CarDiagnosticEngine();
        Car car = carCreator.createFromXml(VALID_XML);
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
