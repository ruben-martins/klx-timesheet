package klx.mentoring.klx_timesheet.domain.dto;

import java.time.LocalDate;
import java.util.UUID;

public class CollaboratorDto {

    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate hireDate;
    private String position;
    public CollaboratorDto(UUID id, String name, String lastName, String email, LocalDate hireDate, String position) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.email = email;
        this.hireDate = hireDate;
        this.position = position;
    }
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public LocalDate getHireDate() {
        return hireDate;
    }
    public void setHireDate(LocalDate hireDate) {
        this.hireDate = hireDate;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }

    
}
