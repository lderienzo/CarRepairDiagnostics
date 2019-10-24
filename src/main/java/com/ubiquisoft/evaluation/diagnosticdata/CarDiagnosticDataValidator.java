package com.ubiquisoft.evaluation.diagnosticdata;

public interface CarDiagnosticDataValidator {
    boolean hasMissingDataFields();
    boolean hasMissingParts();
    boolean hasDamagedParts();
}
