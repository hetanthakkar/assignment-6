package model;

interface FlexiblePortfolioModel extends PortfolioModel {

  void buyShare(String share, int quantity) throws Exception;

  void sellShare(String share, int quantity) throws Exception;

}
