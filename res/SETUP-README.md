## Important Information

- There is a file called `symbols.csv` which contains all the valid Ticker symbols. Please do not delete this file.
- `symbols.csv` file contains the list of all valid ticker symbols.

For generalized use cases, we recommend considering share prices after Jan 2000 for as accurate values as possible.

## Portfolio Operations

To create portfolios:

There are 3 ways to create portfolios. `create` and `create-inflexible` create inflexible portfolios (cannot be changed after creation).
`create-flexible` will create a flexible portfolio, where it has buy and sell functionalities.
- `create portfolio1 (MSFT-1) (AAPL-2) (GOOG-2)`
- `create-inflexible portfolio2 (GOOG-1) (AMZN-2)`
- `create-flexible portfolio3 (GOOG-1)`

Other Operations:
- `help`: See quick manual for supported all commands in program
- `list`: List all the portfolios.
- `buy portfolio1 (AAPL-1)`: buying 1 share of AAPL for portfolio1 (only if portfolio1 is flexible)
- `sell portfolio1 (AAPL-1)`: selling 1 share of AAPL for portfolio1 (only if portfolio1 is flexible)
- `show portfolio1`: Display details of `portfolio1`, where `portfolio1` must already exist
- `getvalue portfolio1 2024-03-14`: Get the total value of `portfolio1` on March 14, 2024.
- `get-cost portfolio1 2024-03-14`: Get the total cost of `portfolio1` on March 14, 2024.
- `save portfolio1`: Save `portfolio1`.
- To load a portfolio: `load /Users/hetanthakkar/Assignment4/retirement/` if your portfolio name is `retirement.csv`.


