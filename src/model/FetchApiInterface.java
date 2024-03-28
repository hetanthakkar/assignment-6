package model;

/**
 * The FetchApiInterface interface defines a contract for classes that provide functionality for
 * fetching data from an API. It specifies methods for making API requests, handling responses, and
 * processing data, offering a consistent interface for interacting with various APIs.
 */
public interface FetchApiInterface {

  /**
   * Fetches data for a given symbol and date from the API.
   *
   * @param symbol The symbol for which to fetch data.
   * @param date The date for which to fetch data.
   * @return The fetched data as a string.
   */
  String fetchData(String symbol, String date);

  /**
   * Fetches data for a given symbol from the API.
   *
   * @param symbol The symbol for which to fetch data.
   * @return The fetched data as a string.
   */
  String fetchData(String symbol);

  String fetchPrevData(String symbol, String date);

}
