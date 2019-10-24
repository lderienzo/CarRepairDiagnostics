package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import java.util.*;

import com.ubiquisoft.evaluation.diagnosticdata.CarDiagnosticDataExtractor;
import com.ubiquisoft.evaluation.domain.*;

public class DamagedPartDataExtractor implements CarDiagnosticDataExtractor {

    @Override
    public DamagedPartData extractDiagnosticData(Car car) {
        return new DamagedPartData(findDamagedParts(car));
    }

    private Map<PartType, ConditionType> findDamagedParts(Car car) {
        Map<PartType, ConditionType> map = new EnumMap<>(PartType.class);
        car.getParts().forEach(part -> addPartToMapIfNotWorking(part, map));
        return map;
    }

    private void addPartToMapIfNotWorking(Part part, Map<PartType, ConditionType> map) {
        if (part.isNotInWorkingCondition())
            map.put(part.getType(), part.getCondition());
    }
}
