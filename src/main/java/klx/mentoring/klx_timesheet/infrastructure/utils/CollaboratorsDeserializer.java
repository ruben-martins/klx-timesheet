package klx.mentoring.klx_timesheet.infrastructure.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import klx.mentoring.klx_timesheet.domain.collaborator.model.Collaborator;

public class CollaboratorsDeserializer extends JsonDeserializer<Set<Collaborator>> {

    @Override
    public Set<Collaborator> deserialize(JsonParser parser, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        try {
            // Deserializar como conjunto de colaboradores
            Set<Collaborator> collaborators = ctxt.readValue(parser,
                    ctxt.getTypeFactory().constructCollectionType(HashSet.class, Collaborator.class));

            // Validar cada colaborador
            for (Collaborator collaborator : collaborators) {
                if (collaborator.id() == null) {
                    throw InvalidFormatException.from(parser,
                            "There is at least one collaborator whose 'id' field is null.",
                            null, UUID.class);
                }

                try {
                    UUID.fromString(collaborator.id().toString());
                } catch (IllegalArgumentException e) {
                    throw InvalidFormatException.from(parser,
                            "Invalid ID format at field 'id': '" + collaborator.id() + "'. Must be a valid UUID.",
                            collaborator.id(), UUID.class);
                }
            }
            return collaborators;
        } catch (InvalidFormatException e) {
            throw e; // Re-lanzar para manejarlo más adelante
        } catch (Exception e) {
            throw new InvalidFormatException(parser,
                    "Invalid value for collaborators. Must be a valid set of Collaborators.",
                    parser.currentName(), Set.class);
        }
    }
}

