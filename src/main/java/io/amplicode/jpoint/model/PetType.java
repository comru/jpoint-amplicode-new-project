package io.amplicode.jpoint.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "types", uniqueConstraints = {
        @UniqueConstraint(name = "uc_types_name", columnNames = {"name"})
})
public class PetType {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

}