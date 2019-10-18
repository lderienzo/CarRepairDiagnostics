package com.ubiquisoft.evaluation.domain;

import static com.ubiquisoft.evaluation.domain.PartType.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class Car {

	private String year;
	private String make;
	private String model;
	private List<Part> parts;
	private Map<PartType, Integer> missingPartsMap = new EnumMap<>(PartType.class);
	public static final String PART_TYPE_MUST_NOT_BE_NULL_MSG = "PartType must not be null";

	public Map<PartType, Integer> getMissingPartsMap() {

		/*
		 * Return map of the part types missing.
		 *
		 * Each car requires one of each of the following types:
		 *      ENGINE, ELECTRICAL, FUEL_FILTER, OIL_FILTER
		 * and four of the type: TIRE
		 *
		 * Example: a car missing three of the four tires should return a map like this:
		 *
		 *      {
		 *          "TIRE": 3
		 *      }
		 */
		createMissingPartsMapFromPartsList();
		return missingPartsMap;
	}

	private void createMissingPartsMapFromPartsList() {
		missingPartsMap.put(ENGINE, determineNumberMissingOfSpecificPartType(1, ENGINE));
		missingPartsMap.put(ELECTRICAL, determineNumberMissingOfSpecificPartType(1, ELECTRICAL));
		missingPartsMap.put(FUEL_FILTER, determineNumberMissingOfSpecificPartType(1, FUEL_FILTER));
		missingPartsMap.put(OIL_FILTER, determineNumberMissingOfSpecificPartType(1, OIL_FILTER));
		missingPartsMap.put(TIRE, determineNumberMissingOfSpecificPartType(4, TIRE));
	}

	private int determineNumberMissingOfSpecificPartType(int shouldHave, PartType partType) {
		return subtractNumberShouldHaveFromActual(shouldHave, partType);
	}

	private int subtractNumberShouldHaveFromActual(int shouldHave, PartType partType) {
		return Math.toIntExact(shouldHave - actualNumberPresent(partType));
	}

	private long actualNumberPresent(PartType partType) {
		return parts.stream().filter(p -> p.getType() == partType).count();
	}

	@Override
	public String toString() {
		return "Car{" +
				       "year='" + year + '\'' +
				       ", make='" + make + '\'' +
				       ", model='" + model + '\'' +
				       ", parts=" + parts +
				       '}';
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters *///region
	/* --------------------------------------------------------------------------------------------------------------- */

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getMake() {
		return make;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public List<Part> getParts() {
		return Collections.unmodifiableList(parts);
	}

	public void setParts(List<Part> parts) {
		this.parts = parts;
	}

	/* --------------------------------------------------------------------------------------------------------------- */
	/*  Getters and Setters End *///endregion
	/* --------------------------------------------------------------------------------------------------------------- */

}
