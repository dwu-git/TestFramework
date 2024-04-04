@tag
Feature: Error validation

  @ErrorValidation
  Scenario Outline: Negative test with invalid email and password
    Given I landed on Landing page
    When Logged in with username <username> and password <password>
    Then "Incorrect email or password." message is displayed

    Examples:
      | username               | password    |
      | invalid@qwerty.com     | invalid     |
