package com.ubiquisoft.evaluation.diagnosticdata;


import com.ubiquisoft.evaluation.domain.Car;

public interface CarDiagnosticDataExtractor {
    DiagnosticData extractDiagnosticData(Car car);
}
