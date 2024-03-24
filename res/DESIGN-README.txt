Model

model package:
    Interfaces: AccountModel, PortfolioModel, ShareModel, ParsedSharesInterface, FetchApiInterface,
        ApiProcessorInterface, CsvFileIOInterface, CsvProcessorInterface, CacheNodeInterface

    Class: Account, Portfolio, Share, PurchasedShares, ParsedShares, FetchApi,
        ApiProcessor, CsvFileIO, CsvProcessor, CacheNode

In our model, we have only one interface the is public and visible to the controller.
The AccountModel is the only available interface. It has a field of a map of PortfolioModel objects.
The map key is a string of the name of each portfolio, whose value is a PortfolioModel.
Portfolio class has a field of a map which holds keys of string of the ticker symbols of each stock,
then a list of ShareModel Interface and PurchaseShare class objects as the values. PurchaseShare
extends Share which implements ShareModel. The reason we extended Share class is because there was a
difference between a share stock and a purchased share. Purchase share holds the quantity, which is
used to calculate the cost-basis.

Then we have CSV, Cache, and API classes, which are basically helper classes which help retrieve
data for our main account-portfolio-share model. When a share object calls getCurrentValue()
or getValueAtDate(String date), then it will these helper classes. Data is first searched in cached
csv files, if not found, then it will fetch from api. If api data is available, it will write to
cache and use that data to return a value. If both cache and api data is not availble for whatever
reason, then an exception will be thrown.

Account is the class available to controller. It has objects of Portfolio. Portfolio has object of
Shares. Share creates FetchApi objects (from api) and CsvProcessor objects (from "cached" csv)
to get data for price on stocks.

Controller

controller package:
    Interfaces: AccountController

    Class: AccountTextBasedController

Our controller only has a go(AccountModel,AccountView) function which takes arguments of
AccountModel and AccountView. This go() is called by the Main.java file. When called, there is a
scanner initiated to get input from user. Depending on what the user inputs, operations will be
called to AccountModel object, which then passes output to AccountView if it applies.


View

view package:
    Interfaces: AccountView

    Class: AccountTextBasedView

Our view only has a displayMessage(String) function. It just displays message on System.out
depending on what is passed to it by the controller.