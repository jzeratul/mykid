package org.jzeratul.mykid.model;

import java.time.OffsetDateTime;
import java.util.List;

public record KidStatsRecord(
        Long id,
        OffsetDateTime datetime,
        List<String> activities,
        Double temperature,
        Double feedFromLeftDuration,
        Double feedFromRightDuration,
        Double pumpFromLeftQuantity,
        Double pumpFromRightQuantity,
        Double extraBottleMotherMilkQuantity,
        Double weight,
        Double extraBottleFormulaeMilkQuantity,
        OffsetDateTime createdAt) {
}
