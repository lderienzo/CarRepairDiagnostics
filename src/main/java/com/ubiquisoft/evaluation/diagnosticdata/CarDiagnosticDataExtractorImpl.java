package com.ubiquisoft.evaluation.diagnosticdata;

import com.ubiquisoft.evaluation.diagnosticdata.damagedpart.*;
import com.ubiquisoft.evaluation.diagnosticdata.missingfield.*;
import com.ubiquisoft.evaluation.diagnosticdata.missingpart.*;
import com.ubiquisoft.evaluation.domain.Car;

public final class CarDiagnosticDataExtractorImpl implements CarDiagnosticDataExtractor {

    private final MissingFieldDataExtractor missingFieldDataExtractor = new MissingFieldDataExtractor();
    private final MissingPartDataExtractor missingPartDataExtractor = new MissingPartDataExtractor();
    private final DamagedPartDataExtractor damagedPartDataExtractor = new DamagedPartDataExtractor();

    @Override
    public DiagnosticData extractDiagnosticData(Car car) {
        MissingFieldData missingFieldData = missingFieldDataExtractor.extractDiagnosticData(car);
        MissingPartData missingPartData = missingPartDataExtractor.extractDiagnosticData(car);
        DamagedPartData damagedPartData = damagedPartDataExtractor.extractDiagnosticData(car);
        return new DiagnosticData(missingFieldData, missingPartData, damagedPartData);
    }
}
