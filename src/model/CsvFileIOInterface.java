package model;

import java.util.List;

/**
 * The CsvFileIOInterface interface defines a contract for classes that provide
 * functionality
 * for reading from and writing to CSV files. It specifies methods for parsing
 * CSV files into
 * structured data and generating CSV files from structured data.
 */

public interface CsvFileIOInterface {

  /**
   * Loads data from a CSV file.
   */
  void loadData();

  /**
   * Retrieves the loaded data from the CSV file.
   *
   * @return A list containing arrays of string representing the loaded data.
   */
  List<String[]> getData();

  /**
   * Stores data to a CSV file.
   *
   * @param values An array of string representing the data to be stored.
   */
  void storeData(String[] values);
}
