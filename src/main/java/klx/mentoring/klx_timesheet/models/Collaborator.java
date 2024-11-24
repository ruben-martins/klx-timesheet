package klx.mentoring.klx_timesheet.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="collaborators")
public class Collaborator {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(name="last_name")
    private String lastName;

    @Column(name="business_unity")
    private String businessUnity;

    private String position;
 
    public Collaborator() {
    }

    public Collaborator(String name, String lastName, String businessUnity, String position) {
        this.name = name;
        this.lastName = lastName;
        this.businessUnity = businessUnity;
        this.position = position;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getBusinessUnity() {
        return businessUnity;
    }

    public void setBusinessUnity(String businessUnity) {
        this.businessUnity = businessUnity;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
    
}

