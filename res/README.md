# Financial Portfolio Management System

## File Format Used
The Financial Portfolio Management System uses the CSV (Comma-Separated Values) file format for importing and exporting portfolio data. This format allows for easy exchange of data between the system and other applications.

## Completed Features

### Portfolio Creation
- Allows a user to create one or more portfolios.
- Each portfolio can contain shares of one or more stocks.

### InflexiblePortfolio
- Once created, shares cannot be added or removed from the portfolio, ensuring data integrity.

### FlexiblePortfolio
- Portfolio where shares can be bought and sold after creation.

### Portfolio Examination
- Provides functionality to examine the composition of a portfolio, including the list of stocks and the quantity of shares for each stock.

### Total Portfolio Value Calculation
- Allows the user to determine the total value of a portfolio on a certain date.
- Inflexible Portfolio 
    - Uses historical stock price data to calculate the total value accurately at any date.
- Flexible Portfolio
    - Any date before the portfolio's creation date, the value will return as 0, otherwise it has the same functionality as inflexible portfolios

### Portfolio Cost Basis Calculation
- Allows the user to determine the total cost of a portfolio on a certain date.
- Inflexible Portfolio
    - Always returns the cost of portfolio at creation.
- Flexible Portfolio
    - Will return the cost basis of portfolio over time. Keeps track of cost after buy and sell too on given dates. Any cost basis on date before creation will be $0.

### Portfolio Persistence
- Implements persistence for portfolios, allowing them to be saved to and loaded from files.
- This feature ensures that portfolio data can be retained and accessed across sessions.

### Command Line Interface (CLI)
- The system includes a GUI implemented as a command line interface (CLI).
- The CLI provides an interactive and user-friendly way to interact with the system and manage portfolios.

### Buying and Selling for Flexible Portfolios
- Shares can be bought in flexible portfolios
- Shares can be sold in flexible portfolios

### Test Coverage
- Written tests for all the models to ensure that they function as expected and to maintain code quality and reliability.

## Usage
To use the Financial Portfolio Management System, follow these steps:
1. Create one or more portfolios and add shares of stocks to them.
2. Use the CLI to examine the composition of portfolios and calculate their total value on specific dates.
3. Save portfolios to files for future reference and load them when needed.

