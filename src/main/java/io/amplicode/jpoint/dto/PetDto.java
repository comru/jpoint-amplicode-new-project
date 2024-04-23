package io.amplicode.jpoint.dto;

import java.time.LocalDate;

/**
 * DTO for {@link io.amplicode.jpoint.model.Pet}
 */
public record PetDto(Long id, String name, LocalDate birthDate, Long ownerId, Long petTypeId) {
}