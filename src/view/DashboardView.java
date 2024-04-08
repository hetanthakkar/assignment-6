package view;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
public class DashboardView extends JFrame implements AccountView {

  public DashboardView() {
    super("Portfolio Dashboard");

    // Set dark theme
    UIManager.put("Button.background", Color.WHITE);
//    UIManager.put("Button.foreground", Color.BLACK); // Change text color to black for better contrast
    UIManager.put("Button.padding", new Insets(100, 200, 100, 200)); // Add padding
    UIManager.put("Button.border", BorderFactory.createLineBorder(Color.BLACK, 1)); // Add border
//    UIManager.put("ButtonUI", CustomButtonUI.class.getName()); // Use custom ButtonUI
//    UIManager.put("Panel.background", new Color(50, 50, 50));
//    UIManager.put("Label.foreground", Color.WHITE);

    // Create the main panel with padding and a better layout
    JPanel mainPanel = new JPanel(new GridBagLayout());
    mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Add padding
    setContentPane(mainPanel);

    GridBagConstraints c = new GridBagConstraints();
    c.insets = new Insets(10, 10, 10, 10); // Padding between elements

    // Headline
    JLabel headlineLabel = new JLabel("Hello " + System.getProperty("user.name") + "!");
    headlineLabel.setFont(new Font("Arial", Font.BOLD, 24));
    c.gridx = 0;
    c.gridy = 0;
    c.gridwidth = 2;
    c.anchor = GridBagConstraints.CENTER;
    mainPanel.add(headlineLabel, c);

    // Show Portfolio Button
    JButton showPortfolioButton = new JButton("Show/Edit Portfolio");
    c.gridx = 0;
    c.gridy = 1;
    c.gridwidth = 1;
    mainPanel.add(showPortfolioButton, c);
    showPortfolioButton.setBounds(100,100,200,100);

    showPortfolioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        ArrayList<StockHolding> stockHoldings = new ArrayList<>();
        stockHoldings.add(new StockHolding("AAPL", 200.0, 300.0, 10, new Date()));
        stockHoldings.add(new StockHolding("GOOG", 1500.0, 1800.0, 5, new Date()));
        stockHoldings.add(new StockHolding("MSFT", 150.0, 180.0, 20, new Date()));
//        new PortfolioView();
        new PortfolioView(stockHoldings);
      }
    });
    // Create Portfolio Button
    JButton createPortfolioButton = new JButton("Create Portfolio");
    c.gridx = 1;
    mainPanel.add(createPortfolioButton, c);
    createPortfolioButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        new CreatePortfolioView();
      }
    });
    // Edit Portfolio Button
//    JButton editPortfolioButton = new JButton("Edit Portfolio");
//    c.gridwidth = 2;
//    c.gridx = 0;
//    c.gridy = 2;
//    mainPanel.add(editPortfolioButton, c);

    // Set frame properties
    pack();
    setLocationRelativeTo(null);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setVisible(true);
  }

  public static void main(String[] args) {
    new DashboardView();
  }

  @Override
  public void displayMessage(String message) {

  }

  @Override
  public StringBuilder getCommand() {
    return null;
  }

  @Override
  public StringBuilder getRestOfCommand() {
    return null;
  }

  // Custom ButtonUI class for setting padding and border radius
  public static class CustomButtonUI extends BasicButtonUI {
    @Override
    protected void installDefaults(AbstractButton b) {
      super.installDefaults(b);
      b.setBorder(BorderFactory.createLineBorder(b.getBackground(), 10)); // Border radius
      b.setMargin(new Insets(10, 20, 10, 20)); // Padding inside the button
    }
  }
}
