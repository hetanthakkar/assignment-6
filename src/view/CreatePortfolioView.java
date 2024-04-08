package view;

import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;

public class CreatePortfolioView extends JFrame implements AccountView {
  JCheckBox dollarCostAveragingCheckBox;
  private JCheckBox fixedAmountCheckBox;
  private String username;
  private StringBuilder command;

  public CreatePortfolioView() {
    super("Stock Program");

    showPortfolioWindow();
  }

  private void showPortfolioWindow() {
    username = System.getProperty("user.name");
    JPanel contentPanel = new JPanel(new BorderLayout());

    // Create a new frame for the portfolio
    JFrame portfolioFrame = new JFrame("Stock Portfolio - " + username);

    // Welcome message
    JLabel portfolioLabel = new JLabel("Hello, " + username + "!");
    portfolioFrame.add(portfolioLabel, BorderLayout.NORTH);

    // Portfolio creation panel
    JPanel portfolioPanel = new JPanel();
    portfolioPanel.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    // Portfolio Name
    JLabel portfolioNameLabel = new JLabel("Portfolio Name:");
    c.fill = GridBagConstraints.HORIZONTAL;
    c.gridx = 0;
    c.gridy = 0;
    c.insets = new Insets(5, 5, 5, 5); // Add padding
    portfolioPanel.add(portfolioNameLabel, c);

    JTextField portfolioNameField = new JTextField(15);
    c.gridx = 1;
    portfolioPanel.add(portfolioNameField, c);

    dollarCostAveragingCheckBox = new JCheckBox("Enable start-to-finish dollar-cost averaging");
    c.gridx = 0;
    c.gridy = 2;
    portfolioPanel.add(dollarCostAveragingCheckBox, c);
    // Select fixed amount strategy checkbox
    fixedAmountCheckBox = new JCheckBox("Select fixed amount strategy");
    c.gridx = 0;
    c.gridy = 1;
    portfolioPanel.add(fixedAmountCheckBox, c);

    JTextField fixedAmountField = new JTextField();
    fixedAmountField.setPreferredSize(new Dimension(100, 25)); // Set desired width and height
    fixedAmountField.setEnabled(false);
    fixedAmountField.setVisible(false);
    c.gridx = 1;
    c.weightx = 1.0; // Allow horizontal expansion (optional)
    portfolioPanel.add(fixedAmountField, c);

    fixedAmountCheckBox.addItemListener(
        e -> {
          boolean isSelected = e.getStateChange() == ItemEvent.SELECTED;
          fixedAmountField.setEnabled(isSelected);
          fixedAmountField.setVisible(isSelected);
          portfolioPanel.revalidate();
        });

    // List to hold the added stock holding panels
    ArrayList<JPanel> stockHoldingPanels = new ArrayList<>();

    // Add button to add a new stock holding panel
    JButton addButton = new JButton("+");
    c.gridx = 2;
    c.gridy = 0;
    c.insets = new Insets(5, 5, 5, 5);
    portfolioPanel.add(addButton, c);

    addButton.addActionListener(
        e -> {
          JPanel stockHoldingPanel =
              createStockHoldingPanel(
                  stockHoldingPanels.size(), stockHoldingPanels, portfolioPanel, portfolioFrame);
          stockHoldingPanels.add(stockHoldingPanel);
          c.gridx = 0;
          c.gridy = stockHoldingPanels.size() + 2;
          c.gridwidth = 3;
          c.fill = GridBagConstraints.HORIZONTAL;
          portfolioPanel.add(stockHoldingPanel, c);
          c.gridwidth = 1;
          portfolioFrame.pack();
        });

    portfolioFrame.add(portfolioPanel, BorderLayout.CENTER);

    JScrollPane scrollPane = new JScrollPane(portfolioPanel);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

    portfolioFrame.add(scrollPane, BorderLayout.CENTER);

    JButton addToPortfolioButton = new JButton("Add to Portfolio");
    addToPortfolioButton.addActionListener(
      e -> {
        StringBuilder commandBuilder = new StringBuilder("create-flexible ");
        commandBuilder.append(portfolioNameField.getText()).append(" ");
        for (JPanel stockHoldingPanel : stockHoldingPanels) {
          JComboBox<String> symbolChoiceBox = (JComboBox<String>) stockHoldingPanel.getComponent(1);
          JTextField quantityField = (JTextField) stockHoldingPanel.getComponent(3);
          commandBuilder
            .append("(")
            .append(symbolChoiceBox.getSelectedItem())
            .append("-")
            .append(quantityField.getText())
            .append(") ");
        }
        this.command = new StringBuilder(commandBuilder.toString().trim());
        // Print the command (you can replace this with your logic to execute the command)
        System.out.println(this.command);
        new DashboardView();
        // Add your logic here for adding the portfolio to your data structure
      });

    portfolioFrame.add(addToPortfolioButton, BorderLayout.PAGE_END);

    // Set the preferred size of the frame to a reasonable maximum size
    portfolioFrame.setPreferredSize(new Dimension(800, 600));

    portfolioFrame.pack();
    portfolioFrame.setLocationRelativeTo(null);
    portfolioFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    portfolioFrame.setVisible(true);
  }

  private JPanel createStockHoldingPanel(
      int index,
      ArrayList<JPanel> stockHoldingPanels,
      JPanel portfolioPanel,
      JFrame portfolioFrame) {
    JPanel stockHoldingPanel = new JPanel(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();

    // Ticker Symbol
    JLabel symbolLabel = new JLabel("Select Ticker Symbol:");
    c.gridx = 0;
    c.gridy = 0;
    c.anchor = GridBagConstraints.LINE_END; // Align label to the end of the row
    c.insets = new Insets(5, 5, 5, 10); // Add padding between label and component
    stockHoldingPanel.add(symbolLabel, c);

    String[] symbols = {"AAPL", "GOOG", "MSFT"};
    JComboBox<String> symbolChoiceBox = new JComboBox<>(symbols);
    c.gridx = 1;
    c.anchor = GridBagConstraints.LINE_START; // Align combo box to the start of the row
    c.insets = new Insets(5, 10, 5, 5); // Add padding between component and next label
    stockHoldingPanel.add(symbolChoiceBox, c);

    // Quantity
    JLabel quantityLabel = new JLabel("Enter Quantity:");
    c.gridx = 0;
    c.gridy = 1;
    c.anchor = GridBagConstraints.LINE_END; // Align label to the end of the row
    c.insets = new Insets(5, 5, 5, 10); // Add padding between label and component
    stockHoldingPanel.add(quantityLabel, c);

    JTextField quantityField = new JTextField(5);
    c.gridx = 1;
    c.anchor = GridBagConstraints.LINE_START; // Align text field to the start of the row
    c.insets = new Insets(5, 10, 5, 5); // Add padding between component and next label
    stockHoldingPanel.add(quantityField, c);

    // Purchase Date
    if (!dollarCostAveragingCheckBox.isSelected()) {
      JLabel dateLabel = new JLabel("Select Purchase Date:");
      c.gridx = 0;
      c.gridy = 2;
      c.anchor = GridBagConstraints.LINE_END; // Align label to the end of the row
      c.insets = new Insets(5, 5, 5, 10); // Add padding between label and component
      stockHoldingPanel.add(dateLabel, c);
    }
    JDateChooser dateChooser = new JDateChooser();
    c.gridx = 1;
    c.anchor = GridBagConstraints.LINE_START; // Align date chooser to the start of the row
    c.insets = new Insets(5, 10, 5, 5); // Add padding between component and next label
    stockHoldingPanel.add(dateChooser, c);

    // Remove button
    JButton removeButton = new JButton("-");
    c.gridx = 2;
    c.gridy = 0;
    c.gridheight = 1; // Set gridheight to 1 (span only one row)
    c.insets = new Insets(5, 5, 5, 5);
    stockHoldingPanel.add(removeButton, c);

    removeButton.addActionListener(
        e -> {
          int panelIndex = stockHoldingPanels.indexOf(stockHoldingPanel);
          stockHoldingPanels.remove(panelIndex);
          portfolioPanel.remove(stockHoldingPanel);
          portfolioFrame.pack();
        });

    JTextField weightageField = new JTextField(5);
    weightageField.setEnabled(false); // Initially disabled
    weightageField.setVisible(false); // Initially invisible
    if (fixedAmountCheckBox.isSelected()) {
      JLabel weightageLabel = new JLabel("Select Weightage of Share:");
      c.gridx = 0;
      c.gridy = 3;
      c.anchor = GridBagConstraints.LINE_END; // Align label to the end of the row
      c.insets = new Insets(5, 5, 5, 10); // Add padding
      stockHoldingPanel.add(weightageLabel, c);

      c.gridx = 1;
      c.anchor = GridBagConstraints.LINE_START; // Align text field to the start of the row
      c.insets = new Insets(5, 10, 5, 5); // Add padding
      stockHoldingPanel.add(weightageField, c);

      weightageField.setEnabled(true);
      weightageField.setVisible(true);
    }
    weightageField = new JTextField(5);
    weightageField.setEnabled(false); // Initially disabled
    weightageField.setVisible(false); // Initially invisible
    if (fixedAmountCheckBox.isSelected()) {
      // Include weightage field for DCA strategy
      JLabel weightageLabel = new JLabel("Select Weightage of Share:");
      c.gridx = 0;
      c.gridy = 3;
      c.anchor = GridBagConstraints.LINE_END; // Align label to the end of the row
      c.insets = new Insets(5, 5, 5, 10); // Add padding
      stockHoldingPanel.add(weightageLabel, c);

      c.gridx = 1;
      c.anchor = GridBagConstraints.LINE_START; // Align text field to the start of the row
      c.insets = new Insets(5, 10, 5, 5); // Add padding
      stockHoldingPanel.add(weightageField, c);

      weightageField.setEnabled(true);
      weightageField.setVisible(true);
    }

    JTextField investAmountField = new JTextField(5);
    investAmountField.setEnabled(false); // Initially disabled
    investAmountField.setVisible(false); // Initially invisible
    if (dollarCostAveragingCheckBox.isSelected()) {
      System.out.println("dfs");
      // Include invest amount field for DCA strategy
      JLabel investAmountLabel = new JLabel("Enter Invest Amount:");
      c.gridx = 0;
      c.gridy = 4;
      c.anchor = GridBagConstraints.LINE_END; // Align label to the end of the row
      c.insets = new Insets(5, 5, 5, 10); // Add padding
      stockHoldingPanel.add(investAmountLabel, c);

      c.gridx = 1;
      c.anchor = GridBagConstraints.LINE_START; // Align text field to the start of the row
      c.insets = new Insets(5, 10, 5, 5); // Add padding
      stockHoldingPanel.add(investAmountField, c);

      investAmountField.setEnabled(true);
      investAmountField.setVisible(true);
    }

    return stockHoldingPanel;
  }

  public static void main(String[] args) {
    new CreatePortfolioView();
  }

  @Override
  public void displayMessage(String message) {
    JOptionPane.showMessageDialog(this, message);
  }

  @Override
  public StringBuilder getCommand() {
    if (this.command==null){
      return new StringBuilder();
    }
    int spaceIndex = this.command.indexOf(" ");

    if (spaceIndex != -1) {
      return new StringBuilder(this.command.substring(0, spaceIndex));
    } else {
      return new StringBuilder("this.command");
    }
  }

  @Override
  public StringBuilder getRestOfCommand() {
    if (this.command==null){
      return new StringBuilder();
    }
    int spaceIndex = this.command.indexOf(" ");
    if (spaceIndex != -1) {
      return new StringBuilder(this.command.substring(spaceIndex + 1).trim());
    } else {
      return new StringBuilder("");
    }
  }

}
