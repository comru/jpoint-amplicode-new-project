package io.amplicode.jpoint.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Getter
@Setter
@Entity
@Table(name = "owners", indexes = {
        @Index(name = "idx_owners_last_name", columnList = "last_name")
})
public class Owner {
    @Id
    @Column(name = "id", nullable = false)
    private Long id;

    @NotNull
    @Column(name = "first_name", nullable = false, length = Integer.MAX_VALUE)
    private String firstName;

    @NotNull
    @Column(name = "last_name", nullable = false, length = Integer.MAX_VALUE)
    private String lastName;

    @Column(name = "address", length = Integer.MAX_VALUE)
    private String address;

    @Column(name = "city", length = Integer.MAX_VALUE)
    private String city;

    @Digits(fraction = 0, integer = 10)
    @NotNull
    @Column(name = "telephone", nullable = false, length = 10)
    private String telephone;

}