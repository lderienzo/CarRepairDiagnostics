package com.ubiquisoft.evaluation.domain;

import static com.ubiquisoft.evaluation.domain.PartType.*;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Strings;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public final class Car {

	private String year;
	private String make;
	private String model;
	private List<Part> parts;
	private final PartValidator partValidator = new PartValidator();
	private final DataFieldValidator fieldValidator = new DataFieldValidator();
    private final Map<PartType, Integer> missingPartsMap = new EnumMap<>(PartType.class);

	public static final String MISSING_DATA_FIELD_MSG = "Missing Data Field(s) Detected:";
	public static final String MISSING_PART_DETECTED_MSG = "Missing Part(s) Detected:";
	public static final String DAMAGED_PART_DETECTED_MSG = "Damaged Part Detected:";

	public boolean hasMissingDataFields() {
	    return missingFieldsListIsNotEmpty();
    }

    private boolean missingFieldsListIsNotEmpty() {
	    return !fieldValidator.findMissingFields(this).isEmpty();
    }

    public void printMissingDataFields() {
	    fieldValidator.printMissingFields();
    }

    public boolean hasMissingParts() {
		return partValidator.thereAreMissingParts(this);
	}

	public void printMissingParts() {
		partValidator.printMissingParts();
	}

	public Map<PartType, Integer> getMissingPartsMap() {
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

	public boolean hasDamagedParts() {
		return partValidator.thereAreDamagedParts(this);
	}

	public void printDamagedParts() {
		partValidator.printDamagedParts();
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

    static class DataFieldValidator {

        private final List<String> missingFields = new ArrayList<>();

        List<String> findMissingFields(Car car) {
            if (fieldIsMissing(car.getMake()))
                missingFields.add("make");
            if (fieldIsMissing(car.getModel()))
                missingFields.add("model");
            if (fieldIsMissing(car.getYear()))
                missingFields.add("year");
            return missingFields;
        }

        private boolean fieldIsMissing(String field) {
            return Strings.isNullOrEmpty(field);
        }

        void printMissingFields() {
            missingFields.forEach(field -> System.out.println(
                    String.format(MISSING_DATA_FIELD_MSG + " %s", field)));
        }
    }
}
