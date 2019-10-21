package com.ubiquisoft.evaluation;

interface CarDiagnosticDataValidator {
    boolean hasMissingDataFields();
    boolean hasMissingParts();
    boolean hasDamagedParts();
}
