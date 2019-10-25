package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;


import com.google.common.collect.*;
import com.ubiquisoft.evaluation.diagnosticdata.CarDiagnosticDataExtractor;
import com.ubiquisoft.evaluation.domain.*;

public class DamagedPartDataExtractor implements CarDiagnosticDataExtractor {

    private final Multimap<PartType, ConditionType> damagedPartMap = ArrayListMultimap.create();


    @Override
    public DamagedPartData extractDiagnosticData(Car car) {
        return new DamagedPartData(findDamagedParts(car));
    }

    private Multimap<PartType, ConditionType> findDamagedParts(Car car) {
        car.getParts().forEach(this::addPartToMapIfNotWorking);
        return damagedPartMap;
    }

    private void addPartToMapIfNotWorking(Part part) {
        if (part.isNotInWorkingCondition())
            damagedPartMap.put(part.getType(), part.getCondition());
    }
}
