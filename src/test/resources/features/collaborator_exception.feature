Feature: Collaborator Management

  This feature ensures that the system correctly handles collaborator creation,
  including validation errors when invalid data is provided.

  Scenario: Attempt to create a collaborator with a null name
    Given I have a collaborator with a null name
    When I attempt to create the collaborator
    Then I receive an InvalidCollaboratorDataException
