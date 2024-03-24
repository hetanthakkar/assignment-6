package model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * The CsvProcessor class provides functionality for processing CSV data. It
 * includes methods for
 * validating symbols, retrieving data, and extracting portfolio information
 * from CSV files,
 * offering a comprehensive toolset for working with CSV data.
 */

public class CsvProcessor implements CsvProcessorInterface {
  private final List<String[]> data;

  /**
   * Constructs a new CsvProcessor object with the provided CSV data.
   *
   * @param data The CSV data to be processed.
   */
  public CsvProcessor(List<String[]> data) {
    this.data = data;
  }

  @Override
  public String[] validateSymbol(String symbol) {
    int index = binarySearch(symbol, 0);
    return index != -1 ? data.get(index) : null;
  }

  @Override
  public CacheNode getData(String symbol) {
    String[] entry = validateSymbol(symbol);
    return entry != null ? new CacheNode(true, entry) : null;
  }

  @Override
  public CacheNode getData(String symbol, String date) {
    String[] entry = getEntry(symbol, date, true);
    if (entry == null) {
      entry = getEntry(symbol, date, false);
    }
    if (entry == null) {
      return null;
    }
    return new CacheNode(entry[0].equals(date), entry);
  }

  private int binarySearch(String key, int columnIndex) {
    int left = 0;
    int right = data.size() - 1;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      String[] midRow = data.get(mid);
      int comparison = midRow[columnIndex].compareTo(key);

      if (comparison == 0) {
        return mid;
      } else if (comparison < 0) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }

    return -1;
  }

  private String[] getEntry(String symbol, String date, boolean exactMatch) {
    data.sort(new EntryComparator());
    int left = 0;
    int right = data.size() - 1;
    String[] result = null;

    while (left <= right) {
      int mid = left + (right - left) / 2;
      String[] midEntry = data.get(mid);
      int symbolComparison = midEntry[midEntry.length - 1].compareTo(symbol);

      if (symbolComparison == 0) {
        int dateComparison = midEntry[0].compareTo(date);

        if (dateComparison == 0) {
          return midEntry;
        } else if (dateComparison < 0) {
          if (exactMatch) {
            left = mid + 1;
          } else {
            result = midEntry;
            left = mid + 1;
          }
        } else {
          right = mid - 1;
        }
      } else if (symbolComparison < 0) {
        left = mid + 1;
      } else {
        right = mid - 1;
      }
    }
    return result;
  }

  @Override
  public String getPortfolioNameFromCsv() {
    if (!data.isEmpty()) {
      // Assuming the first row contains the portfolio name
      return data.get(0)[0];
    }
    return null; // Return null if data is empty
  }

  @Override
  public List<ParsedShares> getSharesFromCsv() {
    List<ParsedShares> sharesList = new ArrayList<>();

    // Start from the second row (index 1) since the first row is the portfolio name
    for (int i = 1; i < data.size(); i++) {
      String[] row = data.get(i);
      if (row.length >= 2) {
        String symbol = row[0].trim(); // Assuming symbol is in the first column
        int quantity = Integer.parseInt(row[1].trim()); // Assuming quantity is in the second column
        sharesList.add(new ParsedShares(symbol, quantity));
      }
    }

    return sharesList;
  }

  private static class EntryComparator implements Comparator<String[]> {
    @Override
    public int compare(String[] entry1, String[] entry2) {
      int symbolComparison = entry1[entry1.length - 1].compareTo(entry2[entry2.length - 1]);
      if (symbolComparison != 0) {
        return symbolComparison;
      }
      return entry1[0].compareTo(entry2[0]);
    }
  }
}