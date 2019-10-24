package com.ubiquisoft.evaluation.diagnosticdata;


import com.ubiquisoft.evaluation.diagnosticdata.damagedpart.DamagedPartData;
import com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldData;
import com.ubiquisoft.evaluation.diagnosticdata.missingpart.MissingPartData;

public class DiagnosticData {

    private final MissingFieldData missingFieldData;
    private final MissingPartData missingPartData;
    private final DamagedPartData damagedPartData;


    protected DiagnosticData() {
        this.missingFieldData = null;
        this.missingPartData = null;
        this.damagedPartData = null;
    }

    public DiagnosticData(MissingFieldData missingFieldData,
                          MissingPartData missingPartData,
                          DamagedPartData damagedPartData) {
        this.missingFieldData = missingFieldData;
        this.missingPartData = missingPartData;
        this.damagedPartData = damagedPartData;
    }

    public MissingFieldData getMissingFieldData() {
        return missingFieldData;
    }

    public MissingPartData getMissingPartData() {
        return missingPartData;
    }

    public DamagedPartData getDamagedPartData() {
        return damagedPartData;
    }
}
