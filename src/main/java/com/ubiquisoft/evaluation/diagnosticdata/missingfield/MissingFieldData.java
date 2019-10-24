package com.ubiquisoft.evaluation.diagnosticdata.missingfield;

import java.util.*;

import com.ubiquisoft.evaluation.diagnosticdata.DiagnosticData;

public class MissingFieldData extends DiagnosticData {

    private final List<String> fields;


    public MissingFieldData(List<String> fields) {
        this.fields = fields;
    }

    public List<String> getMissingFields() {
        return Collections.unmodifiableList(fields);
    }
}
