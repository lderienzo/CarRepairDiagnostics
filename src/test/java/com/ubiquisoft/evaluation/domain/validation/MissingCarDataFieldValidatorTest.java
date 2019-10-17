package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.domain.validation.MissingCarDataFieldValidator.MISSING_DATA_FIELD_MSG;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

public class MissingCarDataFieldValidatorTest {

    private MissingCarDataFieldValidator validator = new MissingCarDataFieldValidator();
    private static final String INVALID_XML_FILE = "SampleCar-missing-data-fields.xml";

    // TODO - BELOW REPEATED
    private static final String VALID_XML_FILE = "SampleCar.xml";
    private static CarCreator carCreator = new CarCreator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;


    @Test
    public void whenAllFieldsPresentThenValidWithEmptyList() {
        // given
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        // when
        List<String> missingFields = validator.findMissingFields(car);
        // then
        assertThat(missingFields).isEmpty();
    }

    @Test
    public void whenMissingFieldThenInvalidWithNonEmptyList() {
        // given
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        // when
        List<String> missingFields = validator.findMissingFields(car);
        // then
        assertThat(missingFields.size()).isEqualTo(1);
        assertThat(missingFields.get(0)).isEqualTo("make");
    }

    @Test
    public void whenNoMissingFieldsThenNoneToPrint() {
        // given
        revertStreams();
        setUpStreams();
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        validator.findMissingFields(car);
        // when
        validator.printMissingFields();
        // then
        assertThat(outContent.toString()).isEmpty();
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
    public void whenMissingFieldsThenFieldsToPrint() {
        // given
        revertStreams();
        setUpStreams();
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        validator.findMissingFields(car);
        // when
        validator.printMissingFields();
        // then
        assertThat(outContent.toString()).isEqualTo(MISSING_DATA_FIELD_MSG + " make\n");
    }
}
