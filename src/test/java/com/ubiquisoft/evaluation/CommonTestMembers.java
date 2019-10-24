package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.TestConstants.CAR_CREATOR;

import com.ubiquisoft.evaluation.domain.Car;

public class CommonTestMembers {

    public static Car car;


    public static void createCarFromXml(String xmlToUse) {
        car = CAR_CREATOR.createFromXml(xmlToUse);
    }
}
