package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.enums.ExitCode.*;

import com.ubiquisoft.evaluation.diagnosticdata.*;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;
import com.ubiquisoft.evaluation.enums.ExitCode;



final class CarDiagnosticEngine {

	static final String BEGIN_DIAGNOSTICS_MSG = "Beginning Car Diagnostics...";
	static final String BEGIN_CHECK_DATA_FIELDS_MSG = "Begin missing data field check...";
	static final String END_CHECK_DATA_FIELDS_MSG = "Data field check complete.";
	static final String MISSING_DATA_FIELDS_ERROR_MSG = "Car has missing data fields.";
	static final String BEGIN_CHECK_MISSING_PARTS_MSG = "Begin missing part check...";
	static final String END_CHECK_MISSING_PARTS_MSG = "Missing part check complete.";
	static final String MISSING_PARTS_ERROR_MSG = "Car has missing parts. ";
	static final String BEGIN_CHECK_DAMAGED_PARTS_MSG = "Begin damaged parts check...";
	static final String END_CHECK_DAMAGED_PARTS_MSG = "Damaged parts check complete.\n";
	static final String DAMAGED_PARTS_ERROR_MSG = "Car has damaged parts. ";
	static final String END_DIAGNOSTICS_SUCCESSFUL_MSG = "All diagnostic checks on car completed.\nCar is \"A-OK.\"";
	private static final String ENDING_DIAGNOSTICS_EARLY_MSG = "Diagnostics halted early.";
	private static CarDiagnosticDataValidator diagnosticDataValidator;
	private static CarDiagnosticDataPrinter diagnosticDataPrinter;


	public ExitCode executeDiagnostics(Car car) {
		System.out.println(BEGIN_DIAGNOSTICS_MSG);
		DiagnosticData diagnosticData = generateDiagnosticData(car);
		initDiagnosticComponentsWithData(diagnosticData);
		if (runMissingDataFieldDiagnostic() == ERROR) return ERROR;
		if (runMissingPartsDiagnostic() == ERROR) return ERROR;
		if (runDamagedPartsDiagnostic() == ERROR) return ERROR;
		System.out.println(END_DIAGNOSTICS_SUCCESSFUL_MSG);
		return OK;
	}

	private DiagnosticData generateDiagnosticData(Car car) {
		return new CarDiagnosticDataExtractorImpl().extractDiagnosticData(car);
	}

	private void initDiagnosticComponentsWithData(DiagnosticData diagnosticData) {
		diagnosticDataValidator = new CarDiagnosticDataValidatorImpl(diagnosticData);
		diagnosticDataPrinter = new CarDiagnosticDataPrinterImpl(diagnosticData);
	}

	private ExitCode runMissingDataFieldDiagnostic() {
		System.out.println(BEGIN_CHECK_DATA_FIELDS_MSG);
		if (diagnosticDataValidator.hasMissingDataFields()) {
			System.err.println(MISSING_DATA_FIELDS_ERROR_MSG);
			diagnosticDataPrinter.printMissingDataFields();
			return ERROR;
		}
		System.out.println(END_CHECK_DATA_FIELDS_MSG);
		return OK;
	}

	private ExitCode runMissingPartsDiagnostic() {
		System.out.println(BEGIN_CHECK_MISSING_PARTS_MSG);
		if (diagnosticDataValidator.hasMissingParts()) {
			System.err.println(MISSING_PARTS_ERROR_MSG);
			diagnosticDataPrinter.printMissingParts();
			return ERROR;
		}
		System.out.println(END_CHECK_MISSING_PARTS_MSG);
		return OK;
	}

	private ExitCode runDamagedPartsDiagnostic() {
		System.out.println(BEGIN_CHECK_DAMAGED_PARTS_MSG);
		if (diagnosticDataValidator.hasDamagedParts()) {
			System.err.println(DAMAGED_PARTS_ERROR_MSG);
			diagnosticDataPrinter.printDamagedParts();
			return ERROR;
		}
		System.out.println(END_CHECK_DAMAGED_PARTS_MSG);
		return OK;
	}

	public static void main(String[] args) {
		if (argsIsEmpty(args))
			haltProgramEarly("No XML File specified. ");
		String xmlFile = getXmlFilePathArgument(args);
		Car car = new CarCreator().createFromXml(xmlFile);
		if (runDiagnosticCheckOnCar(car) == ERROR)
			haltProgramEarly();
	}

	private static boolean argsIsEmpty(String[] a) {
		return a == null || a.length == 0;
	}

	private static void haltProgramEarly(String msgPrefix) {
		System.err.println(msgPrefix + ENDING_DIAGNOSTICS_EARLY_MSG);
		System.exit(1);
	}

	private static String getXmlFilePathArgument(String[] a) {
		return a[0];
	}

	private static ExitCode runDiagnosticCheckOnCar(Car car) {
		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
		return diagnosticEngine.executeDiagnostics(car);
	}

	private static void haltProgramEarly() {
		haltProgramEarly("");
	}
}
