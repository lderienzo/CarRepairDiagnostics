package com.ubiquisoft.evaluation.domain.xml.unmarshaller;

import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;

public class CarCreatorTest {
    private static CarCreator carCreator;

    @BeforeAll
    public static void init() {
        carCreator = new CarCreator();
    }

    @Test
    public void testCarCreator() {
        // given
        String xmlFile = "SampleCar.xml";
        // when
        Car car = carCreator.createFromXml(xmlFile);
        // then
        assertThat(car).isNotNull();
    }

}
