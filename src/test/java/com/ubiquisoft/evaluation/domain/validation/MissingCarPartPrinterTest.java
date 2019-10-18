package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.domain.output.MissingCarPartPrinter.MISSING_PART_DETECTED_MSG;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.output.MissingCarPartPrinter;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

public class MissingCarPartPrinterTest {

    private MissingCarPartPrinter missingCarPartPrinter;

    // TODO - BELOW REPEATED
    private static final String VALID_XML_FILE = "SampleCar.xml";
    private static final String INVALID_XML_FILE = "SampleCar-incomplete-parts-list.xml";
    private static CarCreator carCreator = new CarCreator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;

    @Test
    public void whenNoPartsMissingThenPartsMissingReturnsFalse() {
        // given
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when/then
        assertThat(missingCarPartPrinter.partsMissing()).isFalse();
    }

    @Test
    public void whenPartsMissingThenPartsMissingReturnsTrue() {
        // given
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when/then
        assertThat(missingCarPartPrinter.partsMissing()).isTrue();
    }

    @Test
    public void whenNoPartsMissingThenNothingPrinted() {
        // given
        revertStreams();
        setUpStreams();
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when
        missingCarPartPrinter.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo("");
    }

    // TODO - REPEATED
    public void revertStreams() {
        System.setOut(sysOut);
    }
    // TODO - REPEATED
    private void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void whenPartsMissingThenMissingPartsAndCountPrinted() {
        // given
        revertStreams();
        setUpStreams();
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        missingCarPartPrinter = new MissingCarPartPrinter(car);
        // when
        missingCarPartPrinter.printMissingParts();
        // then
        assertThat(outContent.toString()).isEqualTo(    //  TODO - REPEATED
                MISSING_PART_DETECTED_MSG + " ENGINE - Count: 1\n" +
                MISSING_PART_DETECTED_MSG + " TIRE - Count: 2\n" +
                MISSING_PART_DETECTED_MSG + " FUEL_FILTER - Count: 1\n");
    }
}
