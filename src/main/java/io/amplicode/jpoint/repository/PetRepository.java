package io.amplicode.jpoint.repository;

import io.amplicode.jpoint.model.Pet;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PetRepository extends JpaRepository<Pet, Long>, JpaSpecificationExecutor<Pet> {

    @Query("""
            select p from Pet p
            where upper(p.name) like upper(concat('%', :q, '%')) or upper(p.owner.firstName) like upper(concat('%', :q, '%')) or upper(p.owner.lastName) = upper(:q)
            order by p.name""")
    Slice<Pet> findAllByFilter(@Param("q") String q, Pageable pageable);

}