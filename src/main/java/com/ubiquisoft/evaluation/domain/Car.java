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

		return createMissingPartsMapFromPartsList();
	}

	private Map<PartType, Integer> createMissingPartsMapFromPartsList() {
		Map<PartType, Integer> m = new EnumMap<>(PartType.class);
		m.put(ENGINE, determineNumberOfMissingPartType(1, ENGINE));
		m.put(ELECTRICAL, determineNumberOfMissingPartType(1, ELECTRICAL));
		m.put(FUEL_FILTER, determineNumberOfMissingPartType(1, FUEL_FILTER));
		m.put(OIL_FILTER, determineNumberOfMissingPartType(1, OIL_FILTER));
		m.put(TIRE, determineNumberOfMissingPartType(4, TIRE));
		return m;
	}

	private int determineNumberOfMissingPartType(int expectedNumber, PartType partType) {
		return subtractExpectedNumberOfPartTypeFromActual(expectedNumber, partType);
	}

	private int subtractExpectedNumberOfPartTypeFromActual(int expectedNumber, PartType partType) {
		return Math.toIntExact(expectedNumber - numberOfPartTypePresentInList(partType));
	}

	private long numberOfPartTypePresentInList(PartType partType) {
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
