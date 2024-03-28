package model;

import java.util.List;

/**
 * The CsvProcessorInterface interface defines a contract for classes that provide functionality for
 * processing CSV data. It specifies methods for validating symbols, retrieving data, and extracting
 * portfolio information from CSV files, providing a consistent interface for working with CSV data
 * processing.
 */
public interface CsvProcessorInterface {

  /**
   * Validates a symbol from CSV data.
   *
   * @param symbol The symbol to validate.
   * @return An array containing validated symbol data.
   */
  String[] validateSymbol(String symbol);

  /**
   * Retrieves data from the cache for a given symbol.
   *
   * @param symbol The symbol for which to retrieve data.
   * @return The retrieved data as a CacheNode.
   */
  CacheNodeInterface getData(String symbol);

  /**
   * Retrieves data from the cache for a given symbol and date.
   *
   * @param symbol The symbol for which to retrieve data.
   * @param date The date for which to retrieve data.
   * @return The retrieved data as a CacheNode.
   */
  CacheNodeInterface getData(String symbol, String date);

  /**
   * Retrieves the name of the portfolio from the CSV data.
   *
   * @return The name of the portfolio.
   */
  String getPortfolioNameFromCsv();

  /**
   * Retrieves parsed shares from the CSV data.
   *
   * @return A list of parsed shares.
   */
  List<ParsedShares> getSharesFromCsv();
}
