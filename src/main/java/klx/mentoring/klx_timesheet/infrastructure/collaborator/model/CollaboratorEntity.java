package klx.mentoring.klx_timesheet.infrastructure.collaborator.model;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;

@Entity
@Table(name="collaborators")
public class CollaboratorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    private String name;

    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must not exceed 50 characters")
    @Column(nullable = false)
    private String lastName;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    @PastOrPresent(message = "Hire date must be in the past or today")
    private LocalDate hireDate;

    @NotBlank(message = "Position is mandatory")
    @Size(max = 100, message = "Position must not exceed 100 characters")
    @Column(nullable = false)
    private String position;

    public CollaboratorEntity() {
    }

    public CollaboratorEntity(UUID id, String name, String lastName, String email, LocalDate hireDate, String position) {
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
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getLastName() {
        return lastName;
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
