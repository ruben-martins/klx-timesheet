package klx.mentoring.klx_timesheet.records;

import java.time.LocalDate;

public record CollaboratorRecord(
    Long id,
    String name,
    String lastName,
    String email,
    LocalDate hireDate,
    String position
) {
}
