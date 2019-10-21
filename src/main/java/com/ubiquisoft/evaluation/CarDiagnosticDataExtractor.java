package com.ubiquisoft.evaluation;


import com.ubiquisoft.evaluation.domain.Car;

interface CarDiagnosticDataExtractor {
    DiagnosticData extractDiagnosticData(Car car);
}
