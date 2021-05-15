package org.jzeratul.mykid.model;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

public record SleepRecord(

    Long id,
    Long userid,
    LocalDateTime createdAt,
    LocalDateTime startSleep,
    LocalDateTime endSleep
    
		) {
	
	public Duration getSleepDuration() {
		if(endSleep == null) {
			return Duration.between(startSleep, OffsetDateTime.now());
		}
		
		return Duration.between(startSleep, endSleep);
	}
	
	public LocalDate getSleepDay() {
		if(startSleep != null) {
			return startSleep.toLocalDate();
		}
		return null;
	}

}
