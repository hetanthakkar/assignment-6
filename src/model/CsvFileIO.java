package model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

// import java.io.*;
// import java.util.*;

/**
 * The CsvFileIO class provides functionality for reading data from and writing
 * data to CSV files.
 * It offers methods to parse CSV files into structured data and to generate CSV
 * files from structured data.
 */

public class CsvFileIO implements CsvFileIOInterface {
  private final File file;
  private final List<String[]> data;

  /**
   * Constructs a new CsvFileIO object with the specified file name and file path.
   *
   * @param fileName The name of the CSV file.
   * @param filePath The path to the CSV file.
   */
  public CsvFileIO(String fileName, String filePath) {
    String fileNameString = filePath + fileName + ".csv";
    file = new File(fileNameString);
    data = new ArrayList<>();
    loadData();
  }

  @Override
  public void loadData() {
    try (BufferedReader br = new BufferedReader(new FileReader(file))) {
      String line;
      while ((line = br.readLine()) != null) {
        data.add(line.split(","));
      }
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.getMessage());
    }
  }

  @Override
  public List<String[]> getData() {
    return data;
  }

  @Override
  public void storeData(String[] values) {
    data.add(values);
    data.sort(new EntryComparator());
    saveData();
  }

  private void saveData() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
      if (!file.getName().equals("symbols.csv") && !file.getName().equals("transaction.csv")) {
        for (String[] row : data) {
          writer.println(String.join(",", row));
        }
      }
    } catch (IOException e) {
      System.out.println("An error occurred: " + e.getMessage());
    }
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