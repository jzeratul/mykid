package org.jzeratul.mykid.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.OffsetDateTime;

public record SleepRecord(

    Long id,
    OffsetDateTime createdAt,
    OffsetDateTime startSleep,
    OffsetDateTime endSleep
    
		) {
	
	public SleepRecord {
		
	}
	
	public SleepRecord() {
		this(null, null, null, null);
	}
	
	public Duration getSleepDuration() {
		if(startSleep == null || endSleep == null) {
			return Duration.ZERO;
		}
		
		return Duration.between(endSleep, startSleep);
	}
	
	public LocalDate getSleepDay() {
		if(startSleep != null) {
			return startSleep.toLocalDate();
		}
		return null;
	}

}
