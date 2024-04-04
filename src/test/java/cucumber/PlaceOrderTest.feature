@tag
Feature: Place the order on the website

  Background:
    Given I landed on Landing page

  @Regression
  Scenario Outline: Positive test of placing the order
    Given Logged in with username <username> and password <password>
    When I add product <product> to Cart
    And Checkout <product> and submit the order
    Then "THANKYOU FOR THE ORDER." message is displayed on Confirmation page

    Examples:
    | username                         | password                         | product           |
    | easdfdssdfsSSW@drgerh34.rsrg     | easdfdssdfsSSW@drgerh34.rsrg     | ZARA COAT 3       |
