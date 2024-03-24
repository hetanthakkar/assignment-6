package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * The FetchApi class provides functionality for fetching data from an external
 * API.
 * It includes methods for making API requests, handling responses, and
 * processing data,
 * offering a comprehensive toolset for interacting with external APIs.
 */

public class FetchApi implements FetchApiInterface {

  private String apiKey;

  /**
   * Constructs a new FetchApi object with the specified API key.
   *
   * @param apiKey The API key used for accessing the API.
   */
  public FetchApi(String apiKey) {
    this.apiKey = apiKey;
  }

  /**
   * Constructs a new FetchApi object with a default API key.
   */
  public FetchApi() {
    this.apiKey = "W0M1JOKC82EZEQA8";
  }

  @Override
  public String fetchData(String symbol, String date) {
    String apiURL = String.format(
            "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv",
            symbol, apiKey);
    try {
      URL url = new URL(apiURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        boolean isFirstLine = true;
        String matchingLine = null;
        while ((inputLine = in.readLine()) != null) {
          if (isFirstLine) {
            isFirstLine = false;
          } else {
            String[] temp = inputLine.split(",");
            if (temp[0].equals(date)) {
              matchingLine = inputLine;
              break;
            }
          }
        }
        in.close();
        return matchingLine;
      } else {
        System.out.println("HTTP Error: " + responseCode);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }

  @Override
  public String fetchData(String symbol) {
    String apiURL = String.format(
            "https://www.alphavantage.co/query?function=TIME_SERIES_DAILY&outputsize=full&symbol=%s&apikey=%s&datatype=csv",
            symbol, apiKey);
    try {
      URL url = new URL(apiURL);
      HttpURLConnection connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      int responseCode = connection.getResponseCode();
      if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        boolean isFirstLine = true;
        String matchingLine = null;
        while ((inputLine = in.readLine()) != null) {
          if (isFirstLine) {
            isFirstLine = false;
          } else {
            matchingLine = inputLine;
            break;
          }
        }
        in.close();
        return matchingLine;
      } else {
        System.out.println("HTTP Error: " + responseCode);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
