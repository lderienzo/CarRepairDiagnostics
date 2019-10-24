package com.ubiquisoft.evaluation.diagnosticdata.missingpart;

import static com.ubiquisoft.evaluation.CommonTestMembers.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static com.ubiquisoft.evaluation.diagnosticdata.missingpart.CommonMissingPartDataTestMembers.EXTRACTOR;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.*;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.PartType;

class MissingPartDataExtractorTest {

    private MissingPartData diagnosticData;


    @Test
    void whenNoPartsMissingInXmlThenNoMissingPartDataExtracted() {
        // given
        createCarFromXml(VALID_XML);
        // when
        diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThatMissingPartsMapHasZeroForAllMissingPartCounts();
    }

    private void assertThatMissingPartsMapHasZeroForAllMissingPartCounts() {
        assertThat(diagnosticData.getMissingParts().values()).containsExactly(0, 0, 0, 0, 0);
    }

    @Test
    void whenSomeSomePartsMissingInXmlThenMissingPartDataExtracted() {
        // given
        createCarFromXml(INVALID_XML_MISSING_PARTS);
        // when
        diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThat(diagnosticData.getMissingParts().entrySet())
                .containsExactlyInAnyOrderElementsOf(expectedSomeMissingParts().entrySet());
    }

    private Map<PartType, Integer> expectedSomeMissingParts() {
        Map<PartType, Integer> expected = new EnumMap<>(PartType.class);
        expected.put(FUEL_FILTER, 1);
        expected.put(ELECTRICAL, 0);
        expected.put(OIL_FILTER, 0);
        expected.put(ENGINE, 1);
        expected.put(TIRE, 2);
        return expected;
    }

    @Test
    void whenAllPartsMissingInXmlThenAllPartDataExtracted() {
        // given
        createCarFromXml(INVALID_XML_MISSING_ALL_PARTS);
        // when
        diagnosticData = EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThat(diagnosticData.getMissingParts().entrySet())
                .containsExactlyInAnyOrderElementsOf(expectedAllMissingParts().entrySet());
    }

    private Map<PartType, Integer> expectedAllMissingParts() {
        Map<PartType, Integer> expected = new EnumMap<>(PartType.class);
        Arrays.stream(PartType.values()).forEach(partType -> {
                    int shouldHave = (partType == TIRE ? 4 : 1);
                    expected.put(partType, shouldHave);});
        return expected;
    }
}
