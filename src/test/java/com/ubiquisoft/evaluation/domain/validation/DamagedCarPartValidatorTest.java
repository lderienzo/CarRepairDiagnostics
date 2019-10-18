package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.domain.ConditionType.*;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static com.ubiquisoft.evaluation.domain.validation.DamagedCarPartValidator.DAMAGED_PART_DETECTED_MSG;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;
import com.ubiquisoft.evaluation.domain.xml.unmarshaller.CarCreator;

public class DamagedCarPartValidatorTest {

    private static final String VALID_XML_FILE = "SampleCar-all-diagnostics-valid.xml";
    private static final String INVALID_XML_FILE = "SampleCar.xml";
    private DamagedCarPartValidator damagedCarPartValidator = new DamagedCarPartValidator();
    // TODO - BELOW REPEATED
    private static CarCreator carCreator = new CarCreator();
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private PrintStream sysOut;

    @Test
    public void whenNoDamagedPartsThenDamagedPartMapEmpty() {
        // given
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        // when
        Map<PartType, ConditionType> damagedPartsMap = damagedCarPartValidator.getDamagedPartsMap(car);
        // then
        assertThat(damagedPartsMap).isEmpty();
    }

    @Test
    public void whenDamagedPartsThenDamagedPartMapNotEmpty() {
        // given
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        // when
        Map<PartType, ConditionType> damagedPartsMap = damagedCarPartValidator.getDamagedPartsMap(car);
        // then
        assertThat(damagedPartsMap.size()).isEqualTo(4);
        assertThat(damagedPartsMap).containsAllEntriesOf(expectedMap());
    }

    private Map<PartType, ConditionType> expectedMap() {
        Map<PartType, ConditionType> expected = new EnumMap<>(PartType.class);
        expected.put(ENGINE, USED);
        expected.put(ELECTRICAL, NO_POWER);
        expected.put(TIRE, FLAT);
        expected.put(OIL_FILTER, CLOGGED);
        return expected;
    }

    @Test
    public void whenNoDamagedPartsThenNoPartsPrinted() {
        // given
        revertStreams();
        setUpStreams();
        Car car = carCreator.createFromXml(VALID_XML_FILE);
        damagedCarPartValidator.getDamagedPartsMap(car);
        // when
        damagedCarPartValidator.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEmpty();
    }

    // TODO - REPEATED
    public void revertStreams() {
        System.setOut(sysOut);
    }
    // TODO - REPEATED
    private void setUpStreams() {
        sysOut = System.out;
        System.setOut(new PrintStream(outContent));
    }

    @Test
    public void whenDamagedPartsThenPartsPrinted() {
        // given
        revertStreams();
        setUpStreams();
        Car car = carCreator.createFromXml(INVALID_XML_FILE);
        damagedCarPartValidator.getDamagedPartsMap(car);
        // when
        damagedCarPartValidator.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEqualTo(DAMAGED_PART_DETECTED_MSG + " ENGINE - Condition: USED\n" +
                DAMAGED_PART_DETECTED_MSG + " ELECTRICAL - Condition: NO_POWER\n" +
                DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: FLAT\n" +
                DAMAGED_PART_DETECTED_MSG + " OIL_FILTER - Condition: CLOGGED\n");
    }
}
