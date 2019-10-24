package com.ubiquisoft.evaluation.diagnosticdata;

public interface CarDiagnosticDataPrinter {
    void printMissingDataFields();
    void printMissingParts();
    void printDamagedParts();
}
