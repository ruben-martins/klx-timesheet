package klx.mentoring.klx_timesheet.domain.businessunit.model;

import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;
import klx.mentoring.klx_timesheet.infrastructure.collaborator.utils.CollaboratorsDeserializer;


public record BusinessUnit(
    UUID id, 
    String name, 
    @JsonDeserialize(using = CollaboratorsDeserializer.class)
    Set<Collaborator> collaborators) {
}
