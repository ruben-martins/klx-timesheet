Feature: Collaborator Service

  This feature ensures that the system correctly handles collaborator service
  throwing exception, including validation errors when invalid data is provided.

  Scenario: Attempt to pass a collaborator with a null name to collaborator service
    Given A collaborator with a null name
    When The collaborator service attempts to create it
    Then The InvalidCollaboratorDataException is threw