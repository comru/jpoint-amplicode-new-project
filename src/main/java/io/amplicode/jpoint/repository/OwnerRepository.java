package io.amplicode.jpoint.repository;

import io.amplicode.jpoint.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
}