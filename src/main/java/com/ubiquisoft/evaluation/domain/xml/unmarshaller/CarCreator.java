package com.ubiquisoft.evaluation.domain.xml.unmarshaller;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CarCreator {
    private Car car;
    private InputStream xml;
    private JAXBContext context;
    private Unmarshaller unmarshaller;
    private static final String ERROR_MSG = "An error occurred attempting to load SampleCar.xml";

    public Car createFromXml(String xmlFile) {
        loadClasspathResource(xmlFile);
        verifyResourceWasLoadedProperly();
        return createCar();
    }

    private void loadClasspathResource(String xmlFile) {
        xml = ClassLoader.getSystemResourceAsStream(xmlFile);
    }

    private void verifyResourceWasLoadedProperly() {
        if (xml == null) {
            log.error(ERROR_MSG + ": xml is null.");
            errorOut();
        }
    }

    private Car createCar() {
        buildJAXBContextForConvertingXMLIntoObject();
        createUnmarshallerFromContext(context);
        useUnmarshallerToCreateCar(unmarshaller);
        return car;
    }

    private void buildJAXBContextForConvertingXMLIntoObject() {
        try {
            context = JAXBContext.newInstance(Car.class, Part.class);
        } catch (JAXBException e) {
            log.error(ERROR_MSG + ": " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void createUnmarshallerFromContext(JAXBContext context) {
        try {
            unmarshaller = context.createUnmarshaller();
        } catch (JAXBException e) {
            log.error(ERROR_MSG + ": " + e.getMessage());
            errorOut();
        }
    }

    private void useUnmarshallerToCreateCar(Unmarshaller unmarshaller) {
        try {
            car = (Car) unmarshaller.unmarshal(xml);
        } catch (JAXBException e) {
            log.error(ERROR_MSG + ": " + e.getMessage());
            errorOut();
        }
    }

    private void errorOut() {
        System.err.println(ERROR_MSG);
        System.exit(1);
    }
}
