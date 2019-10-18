package com.ubiquisoft.evaluation.domain.validation;

import static com.ubiquisoft.evaluation.domain.ConditionType.*;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static com.ubiquisoft.evaluation.domain.validation.DamagedCarPartValidator.DAMAGED_PART_DETECTED_MSG;
import static com.ubiquisoft.evaluation.utils.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.io.PrintStream;
import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;
import com.ubiquisoft.evaluation.utils.CommonTestMembers;

public class DamagedCarPartValidatorTest extends CommonTestMembers {

    private DamagedCarPartValidator damagedCarPartValidator = new DamagedCarPartValidator();

    @Test
    public void whenNoDamagedPartsThenDamagedPartMapEmpty() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when
        Map<PartType, ConditionType> damagedPartsMap = damagedCarPartValidator.getDamagedPartsMap(car);
        // then
        assertThat(damagedPartsMap).isEmpty();
    }

    @Test
    public void whenDamagedPartsThenDamagedPartMapNotEmpty() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
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
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        damagedCarPartValidator.getDamagedPartsMap(car);
        // when
        damagedCarPartValidator.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEmpty();

        revertSysOutStream();
    }

    @Test
    public void whenDamagedPartsThenPartsPrinted() {
        // given
        setUpSysOutStream();
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        damagedCarPartValidator.getDamagedPartsMap(car);
        // when
        damagedCarPartValidator.printDamagedParts();
        // then
        assertThat(outContent.toString()).isEqualTo(DAMAGED_PART_DETECTED_MSG + " ENGINE - Condition: USED\n" +
                DAMAGED_PART_DETECTED_MSG + " ELECTRICAL - Condition: NO_POWER\n" +
                DAMAGED_PART_DETECTED_MSG + " TIRE - Condition: FLAT\n" +
                DAMAGED_PART_DETECTED_MSG + " OIL_FILTER - Condition: CLOGGED\n");

        revertSysOutStream();
    }
}
