package model;

import java.util.Map;

public interface ShareStatisticsInterface {
  Double intraDayGainLoss(String date) throws Exception;

  Double periodicGainLoss(String d1, String d2) throws Exception;

  Double xDayMovingAverage(int x, String date) throws Exception;

  Map<String, Integer>[] crossovers(String date1, String date2, int x) throws Exception;

  Map<String, Integer>[] crossovers(String date1, String date2) throws Exception;

  Map<String, Integer>[] movingCrossovers(String date1, String date2, int x, int y)
      throws Exception;

  Map<String, Integer>[] movingCrossovers(String date1, String date) throws Exception;
}
