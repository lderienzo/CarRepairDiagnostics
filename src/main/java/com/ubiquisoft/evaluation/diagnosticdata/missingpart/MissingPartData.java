package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import java.util.*;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticData;
import com.ubiquisoft.evaluation.domain.PartType;

public class MissingPartData extends DiagnosticData {

    private final Map<PartType, Integer> missingParts;


    public MissingPartData(Map<PartType, Integer> missingParts) {
        this.missingParts = missingParts;
    }

    public Map<PartType, Integer> getMissingParts() {
        return Collections.unmodifiableMap(missingParts);
    }
}
