package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.enums.ExitCode.ERROR;
import static com.ubiquisoft.evaluation.enums.ExitCode.OK;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;
import com.ubiquisoft.evaluation.domain.output.MissingCarPartPrinter;
import com.ubiquisoft.evaluation.domain.validation.DamagedCarPartValidator;
import com.ubiquisoft.evaluation.domain.validation.MissingCarDataFieldValidator;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;
import com.ubiquisoft.evaluation.enums.ExitCode;

import java.util.List;
import java.util.Map;



public class CarDiagnosticEngine {

	private static final String ENDING_DIAGNOSTICS_EARLY_MSG = "Diagnostics halted early.";
	private MissingCarDataFieldValidator missingDataFieldValidator = new MissingCarDataFieldValidator();
	private DamagedCarPartValidator damagedCarPartValidator = new DamagedCarPartValidator();
	private MissingCarPartPrinter missingPartPrinter;
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

	public ExitCode executeDiagnostics(Car car) {
		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are missing
		 *                then print the missing fields to the console
		 *                in a similar manner to how the provided methods do.
		 *
		 *      Second  - Validate that no parts are missing using the 'getMissingPartsMap' method in the Car class,
		 *                if one or more are then run each missing part and its count through the provided missing part method.
		 *
		 *      Third   - Validate that all parts are in working condition, if any are not
		 *                then run each non-working part through the provided damaged part method.
		 *
		 *      Fourth  - If validation succeeds for the previous steps then print something to the console informing the user as such.
		 * A damaged part is one that has any condition other than NEW, GOOD, or WORN.
		 *
		 * Important:
		 *      If any validation fails, complete whatever step you are actively on and end diagnostics early.
		 *
		 * Treat the console as information being read by a user of this application. Attempts should be made to ensure
		 * console output is as least as informative as the provided methods.
		 */

		System.out.println(BEGIN_DIAGNOSTICS_MSG);
		if (checkForMissingDataFields(car) == ERROR) return ERROR;
		if (checkForMissingParts(car) == ERROR) return ERROR;
		if (checkForDamagedParts(car) == ERROR) return ERROR;
		System.out.println(END_DIAGNOSTICS_SUCCESSFUL_MSG);
		return OK;
	}

	private ExitCode checkForMissingDataFields(Car car) {
		System.out.println(BEGIN_CHECK_DATA_FIELDS_MSG);
		List<String> fields = missingDataFieldValidator.findMissingFields(car);
		if (thereAreFieldsMissing(fields)) {
			printMissingFields();
			return ERROR;
		}
		System.out.println(END_CHECK_DATA_FIELDS_MSG);
		return OK;
	}

	private void printMissingFields() {
		System.err.println(MISSING_DATA_FIELDS_ERROR_MSG);
		missingDataFieldValidator.printMissingFields();
	}

	private boolean thereAreFieldsMissing(List<String> fields) {
		return fieldsIsNotEmpty(fields);
	}

	private boolean fieldsIsNotEmpty(List<String> fields) {
		return !fields.isEmpty();
	}

	private ExitCode checkForMissingParts(Car car) {
		System.out.println(BEGIN_CHECK_MISSING_PARTS_MSG);
		missingPartPrinter = new MissingCarPartPrinter(car);
		if (missingPartPrinter.partsMissing()) {
			System.err.println(MISSING_PARTS_ERROR_MSG);
			missingPartPrinter.printMissingParts();
			return ERROR;
		}
		System.out.println(END_CHECK_MISSING_PARTS_MSG);
		return OK;
	}

	private ExitCode checkForDamagedParts(Car car) {
		System.out.println(BEGIN_CHECK_DAMAGED_PARTS_MSG);
		Map<PartType, ConditionType> damagedPartsMap = damagedCarPartValidator.getDamagedPartsMap(car);
		if (thereAreDamagedParts(damagedPartsMap)) {
			System.err.println(DAMAGED_PARTS_ERROR_MSG);
			damagedCarPartValidator.printDamagedParts();
			return ERROR;
		}
		System.out.println(END_CHECK_DAMAGED_PARTS_MSG);
		return OK;
	}

	private boolean thereAreDamagedParts(Map<PartType, ConditionType> map) {
		return !map.isEmpty();
	}

	public static void main(String[] args) {
		if (args == null || args.length ==0)
			haltProgramEarly("No XML File specified. ");

		String xmlFile = args[0];
		Car car = new CarCreator().createFromXml(xmlFile);
		if (runDiagnosticCheckOnCar(car) == ERROR)
			haltProgramEarly();
	}

	private static ExitCode runDiagnosticCheckOnCar(Car car) {
		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
		return diagnosticEngine.executeDiagnostics(car);
	}

	private static void haltProgramEarly(String... msgPrefix) {
		System.err.println(msgPrefix + ENDING_DIAGNOSTICS_EARLY_MSG);
		System.exit(1);
	}
}
