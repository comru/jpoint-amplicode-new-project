package io.amplicode.jpoint.repository;

import io.amplicode.jpoint.model.PetType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PetTypeRepository extends JpaRepository<PetType, Long> {
}