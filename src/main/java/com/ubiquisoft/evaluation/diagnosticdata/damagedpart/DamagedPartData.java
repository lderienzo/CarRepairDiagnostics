package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;


import com.google.common.collect.*;
import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticData;
import com.ubiquisoft.evaluation.domain.*;

public class DamagedPartData extends DiagnosticData {

    private final Multimap<PartType, ConditionType> damagedParts;


    public DamagedPartData(Multimap<PartType, ConditionType> damagedParts) {
        this.damagedParts = damagedParts;
    }

    public Multimap<PartType, ConditionType> getDamagedParts() {
        return Multimaps.unmodifiableMultimap(damagedParts);
    }
}
