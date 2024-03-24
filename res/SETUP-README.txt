There is a file called symbols.csv which contains all the valid Ticker symbol. Please don't delete that.

Symbols.csv file contains all the list of valid ticker symbol.
For generalized use case, we would recommend the user to consider the share prices after Jan 2000 in order to get as accurate values as possible.

To create 3 portfolios:

create portfolio1 (MSFT-1) (AAPL-2)
create portfolio2 (GOOG-1) (AMZN-2)
create portfolio3 (GOOG-1) (MSFT-1)
list: To list all the portfolios
show portfolio1
show portfolio2
show portfolio3
getvalue portfolio1 2024-03-14
getvalue portfolio2 2024-03-14
getvalue portfolio3 2024-03-14
save portfolio1
To load the portfolio:  load /Users/hetanthakkar/Assignment4/retirement/ if your portfolio name is retirement.csv

