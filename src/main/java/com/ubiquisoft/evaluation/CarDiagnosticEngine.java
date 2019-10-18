package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.domain.Car.PART_TYPE_MUST_NOT_BE_NULL_MSG;
import static com.ubiquisoft.evaluation.enums.ExitCode.ERROR;
import static com.ubiquisoft.evaluation.enums.ExitCode.OK;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.Part;
import com.ubiquisoft.evaluation.domain.PartType;
import com.ubiquisoft.evaluation.domain.output.MissingCarPartPrinter;
import com.ubiquisoft.evaluation.domain.validation.MissingCarDataFieldValidator;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;
import com.ubiquisoft.evaluation.enums.ExitCode;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;



public class CarDiagnosticEngine {

	public static final String BEGIN_DIAGNOSTICS_MSG = "Beginning Car Diagnostics...";
	public static final String BEGIN_CHECK_DATA_FIELDS_MSG = "Begin missing data field check...";
	public static final String END_CHECK_DATA_FIELDS_MSG = "Data field check complete.";
	public static final String MISSING_DATA_FIELDS_ERROR_MSG = "Car has missing data fields.";
	public static final String BEGIN_CHECK_MISSING_PARTS_MSG = "Begin missing part check...";
	public static final String END_CHECK_MISSING_PARTS_MSG = "Missing part check complete.";
	public static final String MISSING_PARTS_ERROR_MSG = "Car has missing parts. ";

	private static final String ENDING_DIAGNOSTICS_EARLY_MSG = "Diagnostics halted early.";
	private boolean thereAreMissingParts = false;
	private boolean thereAreDamagedParts = false;

	private MissingCarDataFieldValidator missingDataFieldValidator = new MissingCarDataFieldValidator();
	private MissingCarPartPrinter missingPartPrinter;


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

		System.out.println(BEGIN_CHECK_DATA_FIELDS_MSG);
		if (checkForMissingDataFields(car) == ERROR) return ERROR;
		System.out.println(END_CHECK_DATA_FIELDS_MSG);

		System.out.println(BEGIN_CHECK_MISSING_PARTS_MSG);
		if (checkForMissingParts(car) == ERROR) return ERROR;
		System.out.println(END_CHECK_MISSING_PARTS_MSG);

		Map<PartType, ConditionType> damagedPartMap = checkForDamagedParts(car);
		printDamagedParts(damagedPartMap);
		System.out.println("Car damaged part check complete.");

		System.out.println("Diagnostic check on car complete. All checks successful. Car is A-OK.");

		return OK;
	}

	private ExitCode checkForMissingDataFields(Car car) {
		List<String> fields = missingDataFieldValidator.findMissingFields(car);
		if (thereAreFieldsMissing(fields)) {
			printMissingFields();
			return ERROR;
		}
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
		missingPartPrinter = new MissingCarPartPrinter(car);
		if (missingPartPrinter.partsMissing()) {
			System.err.println(MISSING_PARTS_ERROR_MSG);
			missingPartPrinter.printMissingParts();
			return ERROR;
		}
		return OK;
	}

	private Map<PartType, ConditionType> checkForDamagedParts(Car car) {
		return putAnyDamagedPartsInDamagedPartMap(car.getParts());
	}

	private Map<PartType, ConditionType> putAnyDamagedPartsInDamagedPartMap(List<Part> parts) {
		Map<PartType, ConditionType> map = new EnumMap<>(PartType.class);
		for (Part part : parts) {
			addPartToMapIfNotWorking(part, map);
		}
		return map;
	}

	private void addPartToMapIfNotWorking(Part part, Map<PartType, ConditionType> map) {
		if (part.isNotInWorkingCondition()) {
			thereAreDamagedParts = true;
			map.put(part.getType(), part.getCondition());
		}
	}

	private void printDamagedParts(Map<PartType, ConditionType> map) {
		for (Map.Entry<PartType, ConditionType> e : map.entrySet()) {
			printDamagedPart(e.getKey(), e.getValue());
		}
		if (thereAreDamagedParts) {
			System.err.println("Car has damaged parts. " + ENDING_DIAGNOSTICS_EARLY_MSG);
			System.exit(1);
		}
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException(PART_TYPE_MUST_NOT_BE_NULL_MSG);
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) {
		Car car = new CarCreator().createFromXml("SampleCar.xml");
		if (runDiagnosticCheckOnCar(car) == ERROR)
			haltProgramEarly();
	}

	private static ExitCode runDiagnosticCheckOnCar(Car car) {
		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();
		return diagnosticEngine.executeDiagnostics(car);
	}

	private static void haltProgramEarly() {
		System.err.println(ENDING_DIAGNOSTICS_EARLY_MSG);
		System.exit(1);
	}
}
