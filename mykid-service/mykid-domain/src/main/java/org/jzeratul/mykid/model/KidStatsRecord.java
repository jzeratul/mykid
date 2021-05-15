package org.jzeratul.mykid.model;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.List;

public record KidStatsRecord(
		
        Long id,
        Long userid,
        
        OffsetDateTime datetime,
        
        List<String> activities,
        
        double temperature,
        
        double feedFromLeftDuration,
        double feedFromRightDuration,
        
        double pumpFromLeftQuantity,
        double pumpFromRightQuantity,
        
        double extraBottleMotherMilkQuantity,
        
        double weight,
        
        double extraBottleFormulaeMilkQuantity,
        
        OffsetDateTime createdAt,
        
        int dayFeedCount) {
	
	public LocalDate getDay() {
		return datetime.toLocalDate();
	}
	
	public double totalFeedQuantity() {
		return 
				extraBottleMotherMilkQuantity + 
				extraBottleFormulaeMilkQuantity;
	}
	
	public double totalFeedDuration() {
		return 
				feedFromLeftDuration + 
				feedFromRightDuration;
	}
	
	public double totalPumpQuantity() {
		return pumpFromLeftQuantity + 
				pumpFromRightQuantity;
	}
}
