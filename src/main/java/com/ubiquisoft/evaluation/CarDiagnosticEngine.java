package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.enums.ExitCode.ERROR;
import static com.ubiquisoft.evaluation.enums.ExitCode.OK;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;
import com.ubiquisoft.evaluation.enums.ExitCode;



final class CarDiagnosticEngine {

	public static final String BEGIN_DIAGNOSTICS_MSG = "Beginning Car Diagnostics...\n";
	public static final String BEGIN_CHECK_DATA_FIELDS_MSG = "Begin missing data field check...";
	public static final String END_CHECK_DATA_FIELDS_MSG = "Data field check complete.\n";
	public static final String MISSING_DATA_FIELDS_ERROR_MSG = "Car has missing data fields.";
	public static final String BEGIN_CHECK_MISSING_PARTS_MSG = "Begin missing part check...";
	public static final String END_CHECK_MISSING_PARTS_MSG = "Missing part check complete.\n";
	public static final String MISSING_PARTS_ERROR_MSG = "Car has missing parts. ";
	public static final String BEGIN_CHECK_DAMAGED_PARTS_MSG = "Begin damaged parts check...";
	public static final String END_CHECK_DAMAGED_PARTS_MSG = "Damaged parts check complete.\n";
	public static final String DAMAGED_PARTS_ERROR_MSG = "Car has damaged parts. ";
	public static final String END_DIAGNOSTICS_SUCCESSFUL_MSG = "All diagnostic checks on car completed.\nCar is \"A-OK.\"";

	private static final String ENDING_DIAGNOSTICS_EARLY_MSG = "Diagnostics halted early.";


	public ExitCode executeDiagnostics(Car car) {
		System.out.println(BEGIN_DIAGNOSTICS_MSG);
		if (runMissingDataFieldDiagnostic(car) == ERROR) return ERROR;
		if (runMissingPartsDiagnostic(car) == ERROR) return ERROR;
		if (runDamagedPartsDiagnostic(car) == ERROR) return ERROR;
		System.out.println(END_DIAGNOSTICS_SUCCESSFUL_MSG);
		return OK;
	}

	private ExitCode runMissingDataFieldDiagnostic(Car car) {
		System.out.println(BEGIN_CHECK_DATA_FIELDS_MSG);
		if (car.hasMissingDataFields()) {
			System.err.println(MISSING_DATA_FIELDS_ERROR_MSG);
			car.printMissingDataFields();
			return ERROR;
		}
		System.out.println(END_CHECK_DATA_FIELDS_MSG);
		return OK;
	}

	private ExitCode runMissingPartsDiagnostic(Car car) {
		System.out.println(BEGIN_CHECK_MISSING_PARTS_MSG);
		if (car.hasMissingParts()) {
			System.err.println(MISSING_PARTS_ERROR_MSG);
			car.printMissingParts();
			return ERROR;
		}
		System.out.println(END_CHECK_MISSING_PARTS_MSG);
		return OK;
	}

	private ExitCode runDamagedPartsDiagnostic(Car car) {
		System.out.println(BEGIN_CHECK_DAMAGED_PARTS_MSG);
		if (car.hasDamagedParts()) {
			System.err.println(DAMAGED_PARTS_ERROR_MSG);
			car.printDamagedParts();
			return ERROR;
		}
		System.out.println(END_CHECK_DAMAGED_PARTS_MSG);
		return OK;
	}

	public static void main(String[] args) {
		if (args == null || args.length == 0)
			haltProgramEarly("No XML File specified. ");
		String xmlFile = args[0];
		Car car = new CarCreator().createFromXml(xmlFile);
		if (runDiagnosticCheckOnCar(car) == ERROR)
			haltProgramEarly();
	}

	private static void haltProgramEarly(String msgPrefix) {
		System.err.println(msgPrefix + ENDING_DIAGNOSTICS_EARLY_MSG);
		System.exit(1);
	}

	private static ExitCode runDiagnosticCheckOnCar(Car car) {
		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
		return diagnosticEngine.executeDiagnostics(car);
	}

	private static void haltProgramEarly() {
		haltProgramEarly("");
	}
}
