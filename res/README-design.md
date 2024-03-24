# Financial Portfolio Management System

## Overview
The Financial Portfolio Management System is a Java application designed to help users manage their financial portfolios. It provides functionality for tracking portfolio composition, adding and removing shares, calculating portfolio values, and saving portfolio data. The system includes classes and interfaces to handle various aspects of portfolio management, such as processing CSV files, fetching data from external APIs, and providing user interfaces for interacting with the system.

## Classes and Interfaces

### AccountModel (Interface)
- Represents an account that holds all portfolios.
- Provides methods for setting portfolio names, adding shares, finishing portfolio builds, retrieving portfolio composition, getting total portfolio value at a certain date, listing portfolios, and saving portfolios.

### PortfolioModel (Interface)
- Represents a portfolio, providing methods to retrieve its total value at a certain date, its composition, and to save the portfolio.

### ShareModel (Interface)
- Represents a share, providing methods to retrieve its cost, current value, value at a specific date, as well as details like date, ticker symbol, and quantity.

### CacheNode
- Represents a node in a cache, storing data and indicating precision.
- Provides methods for retrieving data and determining precision.

### CsvFileIO
- Provides functionality for reading from and writing to CSV files.
- Includes methods for parsing CSV files into structured data and generating CSV files from structured data.

### CsvProcessor
- Provides functionality for processing CSV data, such as validating symbols, retrieving data, and extracting portfolio information.

### FetchApi
- Provides functionality for fetching data from an external API.
- Includes methods for making API requests, handling responses, and processing data.

### AccountTextBasedView
- Implements the AccountView interface and provides a text-based user interface for displaying messages related to the account.

### AccountTextBasedController
- Implements the AccountController interface and provides a text-based interface to interact with the account model.
- Handles user input and updates to the model.

### Other Classes
- **ParsedShares**: Represents parsed share data.
- **AccountView**: Defines methods for displaying messages related to account information.
- **AccountController**: Defines methods for managing interactions between the account model and account view interfaces.

## Usage
To use the Financial Portfolio Management System, create an instance of the AccountModel and use its methods to manage portfolios, add shares, and retrieve portfolio information. Use the CsvFileIO class to import and export portfolio data from CSV files, and the FetchApi class to fetch external data for analysis.
