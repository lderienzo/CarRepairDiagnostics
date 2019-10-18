package com.ubiquisoft.evaluation.domain.xml.unmarshaller;

import static com.ubiquisoft.evaluation.utils.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.exception.CarCreatorException;

class CarCreatorTest {

    @Test
    void whenValidXmlFileThenCarProperlyCreated() {
        // given/when
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // then
        assertThat(car).isNotNull();
        assertThat(car.getYear()).isEqualTo("2006");
        assertThat(car.getMake()).isEqualTo("Ford");
        assertThat(car.getModel()).isEqualTo("Explorer");
        assertThat(car.getParts()).isNotNull();
        assertThat(car.getParts().size()).isEqualTo(8);
    }

    @Test
    void whenInvalidXmlFileThenCarCreatorExceptionThrown() {
        // given
        String invalidXmlFile = "bogus.xml";
        Assertions.assertThrows(CarCreatorException.class, () -> {  // then
            CAR_CREATOR.createFromXml(invalidXmlFile); // when
        });
    }

}
