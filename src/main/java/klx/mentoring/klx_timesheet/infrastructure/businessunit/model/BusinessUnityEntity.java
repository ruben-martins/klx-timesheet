package klx.mentoring.klx_timesheet.infrastructure.businessunit.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.model.CollaboratorEntity;

@Entity
@Table(name="business_unity")
public class BusinessUnityEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Name is mandatory")
    @Size(max = 50, message = "Name must not exceed 100 characters")
    private String name;
    
    @ManyToMany(cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @Column(nullable = true)
    Set<CollaboratorEntity> collaborators;

    public BusinessUnityEntity() {
        this.collaborators = new HashSet<CollaboratorEntity>();
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

    public Set<CollaboratorEntity> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(Set<CollaboratorEntity> collaborators) {
        this.collaborators = collaborators;
    }

}
