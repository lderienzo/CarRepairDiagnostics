package com.ubiquisoft.evaluation;

interface CarDiagnosticDataPrinter {
    void printMissingDataFields();
    void printMissingParts();
    void printDamagedParts();
}
