package org.jzeratul.mykid.rest;

import java.time.LocalDateTime;

public enum BackInHistory {
	today {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.withHour(0).withMinute(0).withSecond(0).withNano(0), 
					end };
		}
	},
	yesterday {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now().minusDays(1).withHour(23).withMinute(59).withSecond(59).withNano(0);

			return new LocalDateTime[] { 
					end.withHour(0).withMinute(0).withSecond(0).withNano(0), 
					end };
		}
	},
	last24h {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.minusHours(24), 
					end };
		}
	},
	last48h {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.minusHours(48), 
					end };
		}
	},
	last72h {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.minusHours(72), 
					end };
		}
	},
	last7d {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.minusDays(7), 
					end };
		}
	},
	last1m {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.minusMonths(1), 
					end };
		}
	},
	last3m {
		@Override
		public LocalDateTime[] getDates() {

			LocalDateTime end = LocalDateTime.now();

			return new LocalDateTime[] { 
					end.minusMonths(3), 
					end };
		}
	},
	alltime {
		@Override
		public LocalDateTime[] getDates() {
			return null;
		}
	};

	public abstract LocalDateTime[] getDates();
}
