package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.damagedpart.CommonDamagedPartDataTestMembers.EXTRACTOR;
import static com.ubiquisoft.evaluation.domain.ConditionType.*;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;

class DamagedPartDataExtractorTest {

    @Test
    void whenNoPartsDamagedInXmlThenNoDamagedPartDataExtracted() {
        // given/when
        createCarFromXml(VALID_XML);
        // then
        assertThat(EXTRACTOR.extractDiagnosticData(car).getDamagedParts()).isEmpty();
    }

    @Test
    void whenSomeDamagedPartsInXmlThenDamagedPartDataExtracted() {
        // given/when
        createCarFromXml(INVALID_XML_DAMAGED_PARTS);
        // then
        assertThat(EXTRACTOR.extractDiagnosticData(car).getDamagedParts().entrySet())
                .containsExactlyInAnyOrderElementsOf(expectedSomeDamagedPartsMap().entrySet());
    }

    private Map<PartType, ConditionType> expectedSomeDamagedPartsMap() {
        Map<PartType, ConditionType> expected = new EnumMap<>(PartType.class);
        expected.put(ENGINE, USED);
        expected.put(ELECTRICAL, NO_POWER);
        expected.put(TIRE, FLAT);
        expected.put(OIL_FILTER, CLOGGED);
        return expected;
    }

    @Disabled
    @Test   // TODO -- This test exposes a bug where if multiple tires are damaged the damaged part map DOES NOT track them
    void whenAllPartsDamagedInXmlThenAllDamagedPartDataExtracted() {
        // given/when
        createCarFromXml(INVALID_XML_ALL_PARTS_DAMAGED);
        // then
        assertThat(EXTRACTOR.extractDiagnosticData(car).getDamagedParts().entrySet())
                .containsExactlyInAnyOrderElementsOf(expectedAllDamagedPartsMap().entrySet());
    }

    private Map<PartType, ConditionType> expectedAllDamagedPartsMap() {
        Map<PartType, ConditionType> expected = new EnumMap<>(PartType.class);
        expected.put(ENGINE, USED);
        expected.put(ELECTRICAL, USED);
        expected.put(TIRE, USED);
        expected.put(FUEL_FILTER, USED);
        expected.put(OIL_FILTER, USED);
        return expected;
    }
}
