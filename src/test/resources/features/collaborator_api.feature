# language: en
Feature: List Employees

  As an API client
  I want to retrieve a list of employees
  So that I can view all registered employees in the system

  Scenario: Successfully list all employees
    Given there are registered employees in the system:
      | name  | lastName | email                      | hireDate   | position  |
      | Alice | Silva    | alice.silva@example.com    | 2025-01-01 | Secretary |
      | Bruno | Costa    | bruno.costa@example.com    | 2024-12-31 | Director  |
      | Carla | Ferreira | carla.ferreira@example.com | 2025-02-21 | Professor |
    When I make a GET request to retrieve employees
    Then the response should have status code 200
    And the response should contain a list of employees
    And the response should include the previously registered data