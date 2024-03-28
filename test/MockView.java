import view.AccountView;

class MockView implements AccountView {

  @Override
  public void displayMessage(String message) {
    System.out.println(message);
  }
}

