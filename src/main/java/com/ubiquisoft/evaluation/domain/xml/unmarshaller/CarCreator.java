package com.ubiquisoft.evaluation.domain.xml.unmarshaller;

import java.io.*;

import javax.xml.bind.*;

import com.ubiquisoft.evaluation.domain.*;
import com.ubiquisoft.evaluation.exception.CarCreatorException;


public final class CarCreator {

    private static final String ERROR_MSG = "Error constructing car from xml.";

    public Car createFromXml(String xmlFile) {
        InputStream xml = readXmlFileDataIntoInputStream(xmlFile);
        verifyInputStreamIsValid(xml);
        return createCarFromXmlInput(xml);
    }

    private InputStream readXmlFileDataIntoInputStream(String xmlFile) {
        InputStream inStream = ClassLoader.getSystemResourceAsStream(xmlFile);
        if (inStream == null)
            inStream = seeIfFilePathSpecified(xmlFile);
        return inStream;
    }

    private InputStream seeIfFilePathSpecified(String file) {
        InputStream inStream;
        try {
            inStream = new FileInputStream(new File(file));
        } catch (FileNotFoundException e) {
            throw new CarCreatorException(e);
        }
        return inStream;
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
