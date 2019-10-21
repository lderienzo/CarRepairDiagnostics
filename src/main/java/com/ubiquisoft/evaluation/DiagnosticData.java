package com.ubiquisoft.evaluation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;

final class DiagnosticData {
    private final List<String> missingFields;
    private final Map<PartType, Integer> missingParts;
    private final Map<PartType, ConditionType> damagedParts;

    public DiagnosticData(List<String> missingFields,
                          Map<PartType, Integer> missingParts,
                          Map<PartType, ConditionType> damagedParts) {
        this.missingFields = missingFields;
        this.missingParts = missingParts;
        this.damagedParts = damagedParts;
    }

    public List<String> getMissingFields() {
        return Collections.unmodifiableList(missingFields);
    }

    public Map<PartType, Integer> getMissingParts() {
        return Collections.unmodifiableMap(missingParts);
    }

    public Map<PartType, ConditionType> getDamagedParts() {
        return Collections.unmodifiableMap(damagedParts);
    }
}
