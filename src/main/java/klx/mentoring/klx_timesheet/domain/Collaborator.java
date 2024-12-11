package klx.mentoring.klx_timesheet.domain;

import java.time.LocalDate;
import java.util.UUID;

public class Collaborator{
    private UUID id;
    private String name;
    private String lastName;
    private String email;
    private LocalDate hireDate;
    private String position;

    public UUID getId() {
        return id;
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

    public Collaborator(UUID id, String name, String lastName, String email, LocalDate hireDate, String position) {
    }

    // public Collaborator(Collaborator collaborator) {
    //     this.id = collaborator.id();
    //     this.name = collaborator.name();
    //     this.lastName = collaborator.lastName();
    //     this.email = collaborator.email();
    //     this.hireDate = collaborator.hireDate();
    //     this.position = collaborator.position();
    // }

    // public CollaboratorRecord toCollaboratorRecord() {
    //     return new CollaboratorRecord(
    //         this.id,
    //         this.name,
    //         this.lastName,
    //         this.email,
    //         this.hireDate,
    //         this.position
    //     );
    // }
 }
