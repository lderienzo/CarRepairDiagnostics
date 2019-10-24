package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import java.util.*;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticData;
import com.ubiquisoft.evaluation.domain.*;

public class DamagedPartData extends DiagnosticData {

    private final Map<PartType, ConditionType> damagedParts;


    public DamagedPartData(Map<PartType, ConditionType> damagedParts) {
        this.damagedParts = damagedParts;
    }

    public Map<PartType, ConditionType> getDamagedParts() {
        return Collections.unmodifiableMap(damagedParts);
    }
}
