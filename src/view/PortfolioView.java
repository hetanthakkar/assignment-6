package view;

import java.awt.*;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class PortfolioView extends JFrame {
  private static final NumberFormat currencyFormat = NumberFormat.getCurrencyInstance();
  private ArrayList<StockHolding> portfolios;

  public PortfolioView(ArrayList<StockHolding> portfolios) {
    this.portfolios = portfolios;
    setTitle("Portfolio View");
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLayout(new BorderLayout());

    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

    for (StockHolding portfolio : portfolios) {
      JPanel portfolioPanel = createPortfolioPanel(portfolio);
      mainPanel.add(portfolioPanel);
      mainPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Add vertical spacing
    }

    JScrollPane scrollPane = new JScrollPane(mainPanel);
    scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Adjust scroll speed
    add(scrollPane, BorderLayout.CENTER);

    pack();
    setLocationRelativeTo(null);
  }

  private JPanel createPortfolioPanel(StockHolding portfolio) {
    JPanel portfolioPanel = new JPanel(new BorderLayout());
    portfolioPanel.setBorder(BorderFactory.createTitledBorder(portfolio.getName()));

    JPanel stockHoldingsPanel = new JPanel();
    stockHoldingsPanel.setLayout(
        new GridLayout(0, 1, 10, 10)); // Single column grid layout with vertical gap

    for (StockHolding holding : portfolio.getStockHoldings()) {
      JPanel stockPanel =
          new JPanel(new BorderLayout(10, 10)); // Add horizontal gap between components
      stockPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));

      JLabel stockNameLabel = new JLabel(holding.getSymbol());
      stockPanel.add(stockNameLabel, BorderLayout.WEST);

      JPanel detailsPanel =
          new JPanel(new GridLayout(4, 1, 5, 5)); // 4 rows, 1 column grid layout with gaps

      JLabel purchasedPriceLabel =
          new JLabel(
              "Purchased Price: "
                  + currencyFormat.format(holding.getPurchasedPrice())
                  + " ("
                  + currencyFormat.format(holding.getPurchasedPrice() * holding.getQuantity())
                  + " for "
                  + holding.getQuantity()
                  + " shares)");
      detailsPanel.add(purchasedPriceLabel);

      JLabel currentPriceLabel =
          new JLabel(
              "Current Price: "
                  + currencyFormat.format(holding.getCurrentPrice())
                  + " ("
                  + currencyFormat.format(holding.getCurrentPrice() * holding.getQuantity())
                  + " for "
                  + holding.getQuantity()
                  + " shares)");
      detailsPanel.add(currentPriceLabel);

      JLabel quantityLabel = new JLabel("Quantity: " + holding.getQuantity());
      detailsPanel.add(quantityLabel);

      JLabel purchasedDateLabel = new JLabel("Date Purchased: " + holding.getPurchasedDate());
      detailsPanel.add(purchasedDateLabel);

      stockPanel.add(detailsPanel, BorderLayout.CENTER);

      JPanel buttonPanel =
          new JPanel(new GridLayout(1, 2, 5, 5)); // 1 row, 2 columns grid layout with gaps
      JButton buyButton = new JButton("Buy");
      JButton sellButton = new JButton("Sell");
      buttonPanel.add(buyButton);
      buttonPanel.add(sellButton);

      stockPanel.add(buttonPanel, BorderLayout.EAST);

      stockHoldingsPanel.add(stockPanel);

      // Add action listeners for the buy and sell buttons
      buyButton.addActionListener(e -> showQuantityDialog(holding, true));
      sellButton.addActionListener(e -> showQuantityDialog(holding, false));
    }

    JScrollPane scrollPane = new JScrollPane(stockHoldingsPanel);
    portfolioPanel.add(scrollPane, BorderLayout.CENTER);

    return portfolioPanel;
  }

  private void showQuantityDialog(StockHolding holding, boolean isBuyAction) {
    String actionText = isBuyAction ? "Buy" : "Sell";
    String dialogTitle = actionText + " " + holding.getSymbol() + " Shares";

    JPanel panel = new JPanel(new BorderLayout(10, 10));
    JLabel quantityLabel = new JLabel("Enter " + actionText + " quantity:");
    panel.add(quantityLabel, BorderLayout.NORTH);

    JTextField quantityField = new JTextField(10);
    panel.add(quantityField, BorderLayout.CENTER);

    int result =
        JOptionPane.showConfirmDialog(
            this, panel, dialogTitle, JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
    if (result == JOptionPane.OK_OPTION) {
      try {
        int quantity = Integer.parseInt(quantityField.getText());
        if (quantity > 0) {
          // Perform buy or sell action with the specified quantity
          System.out.println(actionText + " " + quantity + " shares of " + holding.getSymbol());
        } else {
          JOptionPane.showMessageDialog(
              this,
              "Invalid quantity. Please enter a positive value.",
              "Error",
              JOptionPane.ERROR_MESSAGE);
        }
      } catch (NumberFormatException ex) {
        JOptionPane.showMessageDialog(
            this,
            "Invalid quantity. Please enter a valid number.",
            "Error",
            JOptionPane.ERROR_MESSAGE);
      }
    }
  }

//  public static void main(String[] args) {
//    // Create some sample portfolios and stock holdings
//    ArrayList<Portfolio> portfolios = new ArrayList<>();
//
//    ArrayList<StockHolding> portfolio1Holdings = new ArrayList<>();
//    portfolio1Holdings.add(new StockHolding("AAPL", 200.0, 300.0, 10, new Date()));
//    portfolio1Holdings.add(new StockHolding("MSFT", 150.0, 180.0, 20, new Date()));
//    portfolios.add(new Portfolio("Portfolio 1", portfolio1Holdings));
//
//    ArrayList<StockHolding> portfolio2Holdings = new ArrayList<>();
//    portfolio2Holdings.add(new StockHolding("GOOG", 1500.0, 1800.0, 5, new Date()));
//    portfolios.add(new Portfolio("Portfolio 2", portfolio2Holdings));
//
//    ArrayList<StockHolding> portfolio3Holdings = new ArrayList<>();
//    portfolio3Holdings.add(new StockHolding("GOOG", 1500.0, 1800.0, 5, new Date()));
//    portfolios.add(new Portfolio("Portfolio 3", portfolio3Holdings));
//
//    ArrayList<StockHolding> portfolio4Holdings = new ArrayList<>();
//    portfolio4Holdings.add(new StockHolding("GOOG", 1500.0, 1800.0, 5, new Date()));
//    portfolio4Holdings.add(new StockHolding("AAPL", 200.0, 300.0, 10, new Date()));
//    portfolio4Holdings.add(new StockHolding("MSFT", 150.0, 180.0, 20, new Date()));
//    portfolio4Holdings.add(new StockHolding("MSFT", 150.0, 180.0, 20, new Date()));
//    portfolio4Holdings.add(new StockHolding("MSFT", 150.0, 180.0, 20, new Date()));
//    portfolio4Holdings.add(new StockHolding("MSFT", 150.0, 180.0, 20, new Date()));
//
//    portfolios.add(new Portfolio("Portfolio 4", portfolio4Holdings));
//
//    // Create and show the portfolio view
//    PortfolioView portfolioView = new PortfolioView(portfolios);
//    portfolioView.setVisible(true);
//  }
}

// Helper classes

class Portfolio {
  private String name;
  private ArrayList<StockHolding> stockHoldings;

  public Portfolio(String name, ArrayList<StockHolding> stockHoldings) {
    this.name = name;
    this.stockHoldings = stockHoldings;
  }

  public String getName() {
    return name;
  }

  public ArrayList<StockHolding> getStockHoldings() {
    return stockHoldings;
  }
}
