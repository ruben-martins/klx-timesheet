package klx.mentoring.klx_timesheet.records;

import java.time.LocalDate;
import java.util.UUID;

public record CollaboratorRecord(
    UUID id,
    String name,
    String lastName,
    String email,
    LocalDate hireDate,
    String position
) { }
