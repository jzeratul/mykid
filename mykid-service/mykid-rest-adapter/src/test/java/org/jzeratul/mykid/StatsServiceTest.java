//package org.jzeratul.mykid;
//
//import static org.junit.jupiter.api.Assertions.assertEquals;
//
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.Arrays;
//import java.util.List;
//import java.util.stream.Collectors;
//
//import javax.validation.Valid;
//
//import jdk.incubator.vector.VectorOperators.Test;
//import org.jzeratul.mykid.model.DailyTotals;
//import org.jzeratul.mykid.model.GenericActivities;
//import org.jzeratul.mykid.model.GetStatsResponse;
//import org.jzeratul.mykid.model.KidStatsRecord;
//import org.jzeratul.mykid.model.SleepRecord;
//import org.jzeratul.mykid.model.Stats;
//import org.jzeratul.mykid.rest.EncryptorService;
//import org.jzeratul.mykid.storage.StatsDataStore;
//
//public class StatsServiceTest {
//
//	private final StatsDataStore fakeData = new StatsDataStore() {
//
//		@Override
//		public void storeStats(KidStatsRecord record) {
//		}
//
//		@Override
//		public List<KidStatsRecord> getStats(OffsetDateTime start, OffsetDateTime end, long userid) {
//			return Arrays.asList(
//					record(3, 20, 20, 1),
//					record(3, 20, 19, 2),
//					record(3, 20, 18, 3),
//					record(3, 10, 20, 1),
//					record(3, 10, 19, 2),
//					record(2, 20, 20, 4),
//			    record(2, 15, 20, 5),
//			    record(2, 15, 19, 6),
//			    record(1, 14, 20, 7),
//			    record(1, 14, 19, 8));
//		}
//
//    @Override
//    public void delete(KidStatsRecord mapFromStats) {
//    }
//
//    @Override
//    public void storeSleep(SleepRecord record) {
//    }
//
//    @Override
//    public List<SleepRecord> getSleep(OffsetDateTime start, OffsetDateTime end, long userid) {
//      return null;
//    }
//  };
//
//  private EncryptorService es = new EncryptorService() {
//
//		@Override
//		public Long decLong(String encrypted) {
//			return null;
//		}
//
//		@Override
//		public String encLong(Long id) {
//			return null;
//		}
//
//		@Override
//		public String decStr(String encrypted) {
//			return null;
//		}
//
//		@Override
//		public String encStr(String decrypted) {
//			return null;
//		}
//  };
//
//	private final StatsService service = new StatsService(fakeData, null, () -> 0, es);
//
//	@org.junit.jupiter.api.Test
//	public void testOrder() {
////		assertEquals(expectedResult, (first + second),
////				() -> first + " + " + second + " should equal " + expectedResult);
//		GetStatsResponse response = service.getStats(null, null);
//
//		List<DailyTotals> dailyTotals = response.getDailyTotals();
//
//		String log1 = dailyTotals.stream().map(t -> t.getDate() + " dayFeedCount:" + t.getDailyFeedCount() + " dayFeedTime:" + t.getDailyFeedTime())
//		    .collect(Collectors.joining(",\n"));
//
//		@Valid
//		List<Stats> stats = response.getStats();
//		String log2 = stats.stream().map(s -> s.getDatetime() + " " + s.getDaycount()).collect(Collectors.joining(", \n"));
//
//		System.out.println(log1);
//		System.out.println("------------");
//		System.out.println(log2);
//
//		assertEquals(5, dailyTotals.size());
//
//		/*
//		 * Totals
//		 * 2021-3-20 dayFeedCount:6.0 dayFeedTime:6.0,
//		 * 2021-3-10 dayFeedCount:4.0 dayFeedTime:4.0,
//		 * 2021-2-20 dayFeedCount:2.0 dayFeedTime:2.0,
//		 * 2021-2-15 dayFeedCount:4.0 dayFeedTime:4.0,
//		 * 2021-1-14 dayFeedCount:4.0 dayFeedTime:4.0
//		 */
//
//		assertEquals("2021-03-20", dailyTotals.get(0).getDate());
//		assertEquals("2021-03-10", dailyTotals.get(1).getDate());
//		assertEquals("2021-02-20", dailyTotals.get(2).getDate());
//		assertEquals("2021-02-15", dailyTotals.get(3).getDate());
//		assertEquals("2021-01-14", dailyTotals.get(4).getDate());
//
//		assertEquals(6d, dailyTotals.get(0).getDailyFeedCount());
//		assertEquals(4d, dailyTotals.get(1).getDailyFeedCount());
//		assertEquals(2d, dailyTotals.get(2).getDailyFeedCount());
//		assertEquals(4d, dailyTotals.get(3).getDailyFeedCount());
//		assertEquals(4d, dailyTotals.get(4).getDailyFeedCount());
//
//		assertEquals(6d, dailyTotals.get(0).getDailyFeedTime());
//		assertEquals(4d, dailyTotals.get(1).getDailyFeedTime());
//		assertEquals(2d, dailyTotals.get(2).getDailyFeedTime());
//		assertEquals(4d, dailyTotals.get(3).getDailyFeedTime());
//		assertEquals(4d, dailyTotals.get(4).getDailyFeedTime());
//
//		/*
//		 * 20-Mar-2021 20:01 3.0,
//		 * 20-Mar-2021 19:02 2.0,
//		 * 20-Mar-2021 18:03 1.0,
//		 * 10-Mar-2021 20:01 2.0,
//		 * 10-Mar-2021 19:02 1.0,
//		 * 20-Feb-2021 20:04 1.0,
//		 * 15-Feb-2021 20:05 2.0,
//		 * 15-Feb-2021 19:06 1.0,
//		 * 14-Jan-2021 20:07 2.0,
//		 * 14-Jan-2021 23:59 1.0
//		 */
//		assertEquals(3d, stats.get(0).getDaycount());
//		assertEquals(2d, stats.get(1).getDaycount());
//		assertEquals(1d, stats.get(2).getDaycount());
//		assertEquals(2d, stats.get(3).getDaycount());
//		assertEquals(1d, stats.get(4).getDaycount());
//		assertEquals(1d, stats.get(5).getDaycount());
//		assertEquals(2d, stats.get(6).getDaycount());
//		assertEquals(1d, stats.get(7).getDaycount());
//		assertEquals(2d, stats.get(8).getDaycount());
//		assertEquals(1d, stats.get(9).getDaycount());
//	}
//
//	private KidStatsRecord record(int month, int day, int hour, int minute) {
//		return new KidStatsRecord(1L, 2L, OffsetDateTime.of(2021, month, day, hour, minute, 0, 0, ZoneOffset.UTC),
//		    Arrays.asList(
//		    		GenericActivities.BOTTLE_MOTHER_MILK.name(),
//		    		GenericActivities.WEIGHT.name()),
//		    1d, 1d, 1d, 1d, 1d, 1d, 3400d, 1d, OffsetDateTime.now());
//	}
//}
