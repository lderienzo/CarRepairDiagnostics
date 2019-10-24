package com.ubiquisoft.evaluation.diagnosticdata;


import com.ubiquisoft.evaluation.diagnosticdata.damagedpart.DamagedPartDataPrinter;
import com.ubiquisoft.evaluation.diagnosticdata.missingfield.MissingFieldDataPrinter;
import com.ubiquisoft.evaluation.diagnosticdata.missingpart.MissingPartDataPrinter;

public final class CarDiagnosticDataPrinterImpl implements CarDiagnosticDataPrinter {

    private final MissingFieldDataPrinter missingFieldDataPrinter;
    private final MissingPartDataPrinter missingPartDataPrinter;
    private final DamagedPartDataPrinter damagedPartDataPrinter;


    public CarDiagnosticDataPrinterImpl(DiagnosticData diagnosticData) {
        this.missingFieldDataPrinter = new MissingFieldDataPrinter(diagnosticData.getMissingFieldData());
        this.missingPartDataPrinter = new MissingPartDataPrinter(diagnosticData.getMissingPartData());
        this.damagedPartDataPrinter = new DamagedPartDataPrinter(diagnosticData.getDamagedPartData());
    }

    @Override
    public void printMissingDataFields() {
        missingFieldDataPrinter.printDiagnosticData();
    }

    @Override
    public void printMissingParts() {
        missingPartDataPrinter.printDiagnosticData();
    }

    @Override
    public void printDamagedParts() {
        damagedPartDataPrinter.printDiagnosticData();
    }
}
