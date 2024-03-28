package model;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

abstract class AbstractPortfolio implements PortfolioModel {
  String name;
  Map<String, List<ShareModel>> shares;

  String creationDate;

  /**
   * Constructs a new Portfolio object with the specified builder.
   *
   * @param newBuild The PortfolioBuilder used to build the portfolio.
   */
  AbstractPortfolio(PortfolioBuilder newBuild) {
    this.name = newBuild.name;
    this.shares = newBuild.shares;
    this.creationDate = LocalDate.now().toString();
  }

  @Override
  public String getTotalValueAtCertainDate(String date) throws Exception {
    try {
      return this.name + " Value: $" + getPortfolioValue(date);
    } catch (Exception e) {
      throw e;
    }
  }

  @Override
  public String getPortfolioComposition() {
    StringBuilder portComposition = new StringBuilder(this.name);
    portComposition.append("\n");
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      portComposition.append(" |--- ( ");
      portComposition.append(entry.getKey());
      portComposition.append(", ");
      portComposition.append(this.getTotalQuantityOfShare(entry.getValue()));
      portComposition.append(") \n");
    }
    return portComposition.toString();
  }

  // @Override
  // public CsvModel savePortfolio() {
  // return null;
  // }

  private int getTotalQuantityOfShare(List<ShareModel> listOfShares) {
    int quantity = 0;
    for (ShareModel groupOfSameShares : listOfShares) {
      quantity += groupOfSameShares.getQuantity();
    }
    return quantity;
  }

  protected double getPortfolioValue(String date) throws Exception {
    double totalValue = 0;
    if (date.equals(this.creationDate)) {
      return getCostBasisValue();
    }
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      for (ShareModel groupOfSameShares : entry.getValue()) {
        try {
          totalValue += groupOfSameShares.getValueAtDate(date);
        } catch (Exception e) {
          throw e;
        }
      }
    }
    return totalValue;
  }

  @Override
  public String savePortfolio() throws Exception {
    String filePath = System.getProperty("user.dir") + File.separator + "res" + File.separator;
    // csvFileIO.storeData(newEntry);
    CsvFileIOInterface csvFileIO = new CsvFileIO(this.name, filePath);

    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      String[] newEntry = new String[6];

      for (ShareModel groupOfSameShares : entry.getValue()) {

        newEntry[0] = groupOfSameShares.getTickerSymbol();
        newEntry[1] = String.valueOf(groupOfSameShares.getQuantity());
        newEntry[2] = String.valueOf(groupOfSameShares.getCost());
        newEntry[3] = String.valueOf(groupOfSameShares.getCurrentValue());
        newEntry[4] =
            String.valueOf(groupOfSameShares.getCurrentValue() * groupOfSameShares.getQuantity());
        newEntry[5] = String.valueOf(groupOfSameShares.getCost() * groupOfSameShares.getQuantity());
      }
      csvFileIO.storeData(newEntry);
    }
    return "Successfully saved file in" + this.name + ".csv";
  }

  @Override
  public abstract void accept(PortfolioVisitorModel visitor) throws Exception;

  @Override
  public String getCostBasis(String date) {
    try {
      return this.name + " Cost-Basis: $" + getCostBasisValue();
    } catch (Exception e) {
      throw e;
    }
  }

  protected double getCostBasisValue() {
    double cost = 0;
    for (Map.Entry<String, List<ShareModel>> entry : this.shares.entrySet()) {
      for (ShareModel groupOfSameShares : entry.getValue()) {
        cost += groupOfSameShares.getCost();
      }
    }
    return cost;
  }

  public String generatePerformanceBarChart(String startDate, String endDate) throws Exception {
    DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("MMMM yyyy dd");
    LocalDate start = LocalDate.parse(startDate, inputFormatter);
    LocalDate end = LocalDate.parse(endDate, inputFormatter);

    long daysBetween = ChronoUnit.DAYS.between(start, end) + 1;
    long numEntries = Math.min(daysBetween, 30L);
    double step = daysBetween <= 30 ? 1 : (double) daysBetween / (numEntries - 1);

    StringBuilder chart = new StringBuilder();
    chart
        .append("\n")
        .append("Performance of portfolio ")
        .append(this.name)
        .append(" from ")
        .append(startDate)
        .append(" to ")
        .append(endDate)
        .append("\n\n");

    double maxVal = 0;
    Map<LocalDate, Double> valueMap = new TreeMap<>();
    LocalDate currentDate = start;

    // Collect data points with uniform spacing
    for (int i = 0; i < numEntries; i++) {
      double value = this.getPortfolioValue(currentDate.format(inputFormatter));
      valueMap.put(currentDate, value);
      maxVal = Math.max(maxVal, value);
      currentDate = start.plusDays((long) Math.round(i * step)); // Round to nearest integer
    }

    double scale = maxVal / 50;

    int maxKeyLength =
        valueMap.keySet().stream()
            .map(key -> key.format(outputFormatter).length())
            .max(Integer::compare)
            .orElse(0);

    for (Map.Entry<LocalDate, Double> entry : valueMap.entrySet()) {
      String formattedDate = entry.getKey().format(outputFormatter);
      chart.append(String.format("%-" + maxKeyLength + "s", formattedDate)).append(" : ");
      int numAsterisks = (int) (entry.getValue() / scale);
      for (int i = 0; i < numAsterisks; i++) {
        chart.append("*");
      }
      chart.append("\n");
    }
    chart.append("\n").append("Absolute Scale: * = ").append(scale).append("\n");

    return chart.toString();
  }
}
