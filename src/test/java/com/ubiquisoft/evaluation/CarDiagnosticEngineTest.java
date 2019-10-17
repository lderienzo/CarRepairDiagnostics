package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.CarDiagnosticEngine.ENDING_DIAGNOSTICS_EARLY_MSG;
import static com.ubiquisoft.evaluation.CarDiagnosticEngine.MISSING_DATA_FIELDS_ERROR_MSG;
import static com.ubiquisoft.evaluation.enums.ExitCode.ERROR;
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

    private static CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private PrintStream sysErr;

    // TODO - BELOW REPEATED
    private static final String INVALID_XML_FILE = "SampleCar-missing-data-fields.xml";
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
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        // when
        ExitCode exitCode = diagnosticEngine.executeDiagnostics(car);
        // then
        assertThat(exitCode).isEqualTo(ERROR);
        assertThat(errContent.toString()).isEqualTo(
                MISSING_DATA_FIELDS_ERROR_MSG + "\n" + ENDING_DIAGNOSTICS_EARLY_MSG + "\n");
        assertThat(outContent.toString()).isEqualTo("Beginning Diagnostics...\nMissing Data Field(s) Detected: make\n");
    }

}
