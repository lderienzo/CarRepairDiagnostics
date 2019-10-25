package com.ubiquisoft.evaluation.diagnosticdata.damagedpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.domain.ConditionType.*;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.guava.api.Assertions.assertThat;


import org.junit.jupiter.api.Test;

import com.google.common.collect.*;
import com.ubiquisoft.evaluation.domain.*;

class DamagedPartDataExtractorTest {

    @Test
    void whenNoPartsDamagedInXmlThenNoDamagedPartDataExtracted() {
        // given/when
        DamagedPartData diagnosticData = extractDiagnosticData(VALID_XML);
        // then
        assertThat(diagnosticData.getDamagedParts().isEmpty()).isTrue();
    }

    private DamagedPartData extractDiagnosticData(String xmlToUse) {
        // given
        createCarFromXml(xmlToUse);
        DamagedPartDataExtractor extractor = new DamagedPartDataExtractor();
        // when
        return extractor.extractDiagnosticData(car);
    }

    @Test // TODO
    void whenSomeDamagedPartsInXmlThenDamagedPartDataExtracted() {
        // given/when
        DamagedPartData diagnosticData = extractDiagnosticData(INVALID_XML_DAMAGED_PARTS);
        // then
        assertThat(diagnosticData.getDamagedParts()).hasSameEntriesAs(expectedSomeDamagedPartsMap());
    }

    private Multimap<PartType, ConditionType> expectedSomeDamagedPartsMap() {
        Multimap<PartType, ConditionType> expected = ArrayListMultimap.create();
        expected.put(ENGINE, USED);
        expected.put(ELECTRICAL, NO_POWER);
        expected.put(TIRE, FLAT);
        expected.put(OIL_FILTER, CLOGGED);
        return expected;
    }

    @Test
    void whenAllPartsDamagedInXmlThenAllDamagedPartDataExtracted() {
        // given/when
        DamagedPartData diagnosticData = extractDiagnosticData(INVALID_XML_ALL_PARTS_DAMAGED);
        // then
        assertThat(diagnosticData.getDamagedParts()).hasSameEntriesAs(expectedAllDamagedPartsMap());
    }

    private Multimap<PartType, ConditionType> expectedAllDamagedPartsMap() {
        Multimap<PartType, ConditionType> expected = ArrayListMultimap.create();
        expected.put(ENGINE, USED);
        expected.put(ELECTRICAL, USED);
        expected.put(FUEL_FILTER, USED);
        expected.put(OIL_FILTER, USED);
        expected.put(TIRE, FLAT);
        expected.put(TIRE, USED);
        expected.put(TIRE, FLAT);
        expected.put(TIRE, DAMAGED);
        return expected;
    }
}
