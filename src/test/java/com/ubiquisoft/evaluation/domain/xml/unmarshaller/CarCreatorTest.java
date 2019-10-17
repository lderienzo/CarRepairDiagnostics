package com.ubiquisoft.evaluation.domain.xml.unmarshaller;

import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.exception.CarCreatorException;

public class CarCreatorTest {
    // TODO -- REPEATED
    private static CarCreator carCreator = new CarCreator();

    @Test
    public void whenValidXmlFileThenCarProperlyCreated() {
        // given
        String validXmlFile = "SampleCar.xml";
        // when
        Car car = carCreator.createFromXml(validXmlFile);
        // then
        assertThat(car).isNotNull();
        assertThat(car.getYear()).isEqualTo("2006");
        assertThat(car.getMake()).isEqualTo("Ford");
        assertThat(car.getModel()).isEqualTo("Explorer");
        assertThat(car.getParts()).isNotNull();
        assertThat(car.getParts().size()).isEqualTo(8);
    }

    @Test
    public void whenInvalidXmlFileThenCarCreatorExceptionThrown() {
        // given
        String invalidXmlFile = "bogus.xml";
        Assertions.assertThrows(CarCreatorException.class, () -> {  // then
            carCreator.createFromXml(invalidXmlFile); // when
        });
    }

}
