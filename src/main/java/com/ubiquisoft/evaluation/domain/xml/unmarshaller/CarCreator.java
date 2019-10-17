package com.ubiquisoft.evaluation.domain.xml.unmarshaller;

import java.io.InputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.exception.CarCreatorException;


public class CarCreator {
    private static final String ERROR_MSG = "Error constructing car from xml.";

    public Car createFromXml(String xmlFile) {
        InputStream xml = readXmlFileDataIntoInputStream(xmlFile);
        verifyInputStreamIsValid(xml);
        return createCarFromXmlInput(xml);
    }

    private InputStream readXmlFileDataIntoInputStream(String xmlFile) {
        return ClassLoader.getSystemResourceAsStream(xmlFile);
    }

    private void verifyInputStreamIsValid(InputStream xml) {
        if (xml == null) throw new CarCreatorException(ERROR_MSG + ": xml is null.");
    }

    private Car createCarFromXmlInput(InputStream xml) {
        try {
            JAXBContext context = buildJAXBContextForConvertingXMLIntoObject();
            Unmarshaller unmarshaller = createUnmarshallerFromContext(context);
            return unmarshallCarFromXml(xml, unmarshaller);
        } catch (JAXBException e) {
            throw new CarCreatorException(ERROR_MSG, e);
        }
    }

    private JAXBContext buildJAXBContextForConvertingXMLIntoObject() throws JAXBException {
        return JAXBContext.newInstance(Car.class, Part.class);
    }

    private Unmarshaller createUnmarshallerFromContext(JAXBContext context) throws JAXBException {
        return context.createUnmarshaller();
    }

    private Car unmarshallCarFromXml(InputStream xml, Unmarshaller unmarshaller) throws JAXBException {
        return (Car) unmarshaller.unmarshal(xml);
    }
}
