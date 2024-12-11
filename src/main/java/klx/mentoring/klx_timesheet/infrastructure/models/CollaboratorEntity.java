package klx.mentoring.klx_timesheet.infrastructure.models;

import java.time.LocalDate;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import klx.mentoring.klx_timesheet.domain.Collaborator;

@Entity
@Table(name="collaborators")
public class CollaboratorEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String name;

    private String lastName;

    private String email;

    private LocalDate hireDate;

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

    public CollaboratorEntity(Collaborator collaborator){
        this.name = collaborator.getName();
        this.lastName = collaborator.getLastName();
        this.email = collaborator.getEmail();
        this.hireDate = collaborator.getHireDate();
        this.position = collaborator.getPosition();
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
