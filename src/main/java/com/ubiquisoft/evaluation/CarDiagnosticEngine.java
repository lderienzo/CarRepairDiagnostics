package com.ubiquisoft.evaluation;

import com.google.common.base.Strings;
import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

import javax.xml.bind.JAXBException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CarDiagnosticEngine {
	private boolean thereAreMissingParts = false;

	public void executeDiagnostics(Car car) {

		List<String> missingDataFields = checkForMissingFields(car);
		printNamesOfAnyMissingFields(missingDataFields);

		Map<PartType, Integer> missingPartsMap = checkForMissingParts(car);
		printNamesOfAnyMissingParts(missingPartsMap);

		/*
		 * Implement basic diagnostics and print results to console.
		 *
		 * The purpose of this method is to find any problems with a car's data or parts.
		 *
		 * Diagnostic Steps:
		 *      First   - Validate the 3 data fields are present, if one or more are
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


	}

	private List<String> checkForMissingFields(Car car) {
		List<String> missingFields = new ArrayList<>();;
		if (fieldIsMissing(car.getMake()))
			missingFields.add(car.getMake());
		if (fieldIsMissing(car.getModel()))
			missingFields.add(car.getModel());
		if (fieldIsMissing(car.getYear()))
			missingFields.add(car.getYear());
		return missingFields;
	}

	private boolean fieldIsMissing(String field) {
		return Strings.isNullOrEmpty(field);
	}

	private void printNamesOfAnyMissingFields(List<String> fields) {
		fields.stream().forEach(field -> System.out.println(String.format("Missing Data Field(s) Detected: %s", field)));
		if (thereAreFieldsMissing(fields)) {
			System.err.println("Car data fields missing. Halting execution.");
			System.exit(1);
		}
	}

	private boolean thereAreFieldsMissing(List<String> fields) {
		return fieldsIsNotEmpty(fields);
	}

	private boolean fieldsIsNotEmpty(List<String> fields) {
		return !fields.isEmpty();
	}

	private Map<PartType, Integer> checkForMissingParts(Car car) {
		return car.getMissingPartsMap();
	}

	private void printNamesOfAnyMissingParts(Map<PartType, Integer> map) {
		for (Map.Entry<PartType, Integer> e : map.entrySet())
			printMessageIfMissing(e);
		if (thereAreMissingParts) {
			System.err.println("Car parts missing. Halting execution.");
			System.exit(1);
		}
	}

	private void printMessageIfMissing(Map.Entry<PartType, Integer> e) {
		if (partTypeCountGreaterThanZero(e)) {
			thereAreMissingParts = true;
			printMissingPart(e.getKey(), e.getValue());
		}
	}

	private boolean partTypeCountGreaterThanZero(Map.Entry<PartType, Integer> entry) {
		return entry.getValue() > 0;
	}

	private void printMissingPart(PartType partType, Integer count) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (count == null || count <= 0) throw new IllegalArgumentException("Count must be greater than 0");

		System.out.println(String.format("Missing Part(s) Detected: %s - Count: %s", partType, count));
	}

	private void printDamagedPart(PartType partType, ConditionType condition) {
		if (partType == null) throw new IllegalArgumentException("PartType must not be null");
		if (condition == null) throw new IllegalArgumentException("ConditionType must not be null");

		System.out.println(String.format("Damaged Part Detected: %s - Condition: %s", partType, condition));
	}

	public static void main(String[] args) throws JAXBException {

		Car car = new CarCreator().createFromXml("SampleCar.xml");

		// Build new Diagnostics Engine and execute on deserialized car object.

		CarDiagnosticEngine diagnosticEngine = new CarDiagnosticEngine();

		diagnosticEngine.executeDiagnostics(car);

	}

}
