package org.jzeratul.mykid.model;

import java.time.Duration;
import java.time.OffsetDateTime;

public class Test {
	
	
	public static void main(String[] args) {

		
		OffsetDateTime d1 = OffsetDateTime.now().plusDays(2);
		
		OffsetDateTime d2 = d1.minusDays(4);
		
		
		System.out.println(Duration.between(d2, d1).toMinutes());
		
		
	}
}
