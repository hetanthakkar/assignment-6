package model;

import java.util.Map;

/**
 * The ShareStatisticsInterface defines the methods for calculating various statistical
 * measures and analyses related to a stock or security.
 */
public interface ShareStatisticsInterface {

  /**
   * Calculates the intra-day gain or loss for the given date.
   *
   * @param date The date in the format "yyyy-MM-dd" for which the intra-day gain/loss is needed.
   * @return The intra-day gain or loss, calculated as the closing price minus the opening price.
   * @throws Exception If the opening price data is not available.
   */
  Double intraDayGainLoss(String date) throws Exception;

  /**
   * Calculates the periodic gain or loss between two dates.
   *
   * @param d1 The start date in the format "yyyy-MM-dd".
   * @param d2 The end date in the format "yyyy-MM-dd".
   * @return The periodic gain or loss, calculated as the closing price
   *     on d2 minus the closing price on d1.
   * @throws Exception If the closing price data is not available for either date.
   */
  Double periodicGainLoss(String d1, String d2) throws Exception;

  /**
   * Calculates the X-day moving average for the given date.
   *
   * @param x    The number of days for the moving average calculation.
   * @param date The date in the format "yyyy-MM-dd" for which the moving average is needed.
   * @return The X-day moving average for the specified date.
   * @throws Exception If the closing price data is not available for any of the required dates.
   */
  Double xDayMovingAverage(int x, String date) throws Exception;

  /**
   * Calculates the crossovers between the stock price and its X-day moving average
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @param x     The number of days for the moving average calculation.
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  Map<String, Integer>[] crossovers(String date1, String date2, int x) throws Exception;

  /**
   * Calculates the crossovers between the stock price and its 30-day moving average
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  Map<String, Integer>[] crossovers(String date1, String date2) throws Exception;

  /**
   * Calculates the crossovers between two moving averages (X-day and Y-day)
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @param x     The number of days for the first moving average calculation.
   * @param y     The number of days for the second moving average calculation.
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  Map<String, Integer>[] movingCrossovers(String date1, String date2, int x, int y)
          throws Exception;

  /**
   * Calculates the crossovers between the 3-day and 1-day moving averages
   * for the given date range.
   *
   * @param date1 The start date in the format "yyyy-MM-dd".
   * @param date2 The end date in the format "yyyy-MM-dd".
   * @return A map containing the dates and corresponding crossover directions (1 or -1).
   * @throws Exception If the required data is not available for any of the dates in the range.
   */
  Map<String, Integer>[] movingCrossovers(String date1, String date2) throws Exception;
}