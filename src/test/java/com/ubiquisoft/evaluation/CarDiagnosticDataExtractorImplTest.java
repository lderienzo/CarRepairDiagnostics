package com.ubiquisoft.evaluation;

import static com.ubiquisoft.evaluation.domain.ConditionType.*;
import static com.ubiquisoft.evaluation.domain.PartType.*;
import static com.ubiquisoft.evaluation.TestConstants.*;
import static org.assertj.core.api.Java6Assertions.assertThat;

import java.util.EnumMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.ubiquisoft.evaluation.domain.Car;
import com.ubiquisoft.evaluation.domain.ConditionType;
import com.ubiquisoft.evaluation.domain.PartType;

final class CarDiagnosticDataExtractorImplTest extends CommonTestMembers {

    private static final CarDiagnosticDataExtractor CAR_DIAGNOSTIC_DATA_EXTRACTOR  = new CarDiagnosticDataExtractorImpl();
    private static DiagnosticData diagnosticData;

    @Test
    void whenXmlContainsAllValidDataThenAllExtractedDiagnosticDataEmpty() {
        // given
        Car car = CAR_CREATOR.createFromXml(VALID_XML);
        // when
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThat(diagnosticData.getMissingFields()).isEmpty();
        assertThatMissingPartsMapHasZeroForAllMissingPartCounts();
        assertThat(diagnosticData.getDamagedParts()).isEmpty();
    }

    private void assertThatMissingPartsMapHasZeroForAllMissingPartCounts() {
        assertThat(diagnosticData.getMissingParts().values()).containsExactly(0, 0, 0, 0, 0);
    }

    @Test
    void whenXmlHasMissingDataFieldsThenExtractedDiagnosticDataWillContainThem() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_DATA_FIELDS);
        // when
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThat(diagnosticData.getMissingFields().size()).isEqualTo(1);
        assertThat(diagnosticData.getMissingFields().get(0)).isEqualTo("make");
        assertThatMissingPartsMapHasZeroForAllMissingPartCounts();
        assertThat(diagnosticData.getDamagedParts()).isEmpty();
    }

    @Test
    void whenXmlMissingPartsThenExtractedDiagnosticDataWillContainThem() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_MISSING_PARTS);
        // when
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThat(diagnosticData.getMissingFields()).isEmpty();
        assertThat(diagnosticData.getMissingParts().entrySet())
                .containsExactlyInAnyOrderElementsOf(expectedMissingParts().entrySet());
        assertThat(diagnosticData.getDamagedParts()).isEmpty();
    }

    private Map<PartType, Integer> expectedMissingParts() {
        Map<PartType, Integer> expected = new EnumMap<>(PartType.class);
        expected.put(FUEL_FILTER, 1);
        expected.put(ELECTRICAL, 0);
        expected.put(OIL_FILTER, 0);
        expected.put(ENGINE, 1);
        expected.put(TIRE, 2);
        return expected;
    }

    @Test
    void whenXmlContainsDamagedPartsThenExtractedDiagnosticDataWillContainThem() {
        // given
        Car car = CAR_CREATOR.createFromXml(INVALID_XML_DAMAGED_PARTS);
        // when
        diagnosticData = CAR_DIAGNOSTIC_DATA_EXTRACTOR.extractDiagnosticData(car);
        // then
        assertThat(diagnosticData.getMissingFields()).isEmpty();
        assertThatMissingPartsMapHasZeroForAllMissingPartCounts();
        assertThat(diagnosticData.getDamagedParts().entrySet())
                .containsExactlyInAnyOrderElementsOf(expectedDamagedPartsMap().entrySet());
    }

    private Map<PartType, ConditionType> expectedDamagedPartsMap() {
        Map<PartType, ConditionType> expected = new EnumMap<>(PartType.class);
        expected.put(ENGINE, USED);
        expected.put(ELECTRICAL, NO_POWER);
        expected.put(TIRE, FLAT);
        expected.put(OIL_FILTER, CLOGGED);
        return expected;
    }
}
