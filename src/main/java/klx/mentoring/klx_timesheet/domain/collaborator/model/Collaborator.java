package klx.mentoring.klx_timesheet.domain.collaborator.model;

import java.time.LocalDate;
import java.util.UUID;

public record Collaborator(
    UUID id,
    String name,
    String lastName,
    String email,
    LocalDate hireDate,
    String position
) { }
