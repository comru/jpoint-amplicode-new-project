package io.amplicode.jpoint.mapper;

import io.amplicode.jpoint.dto.PetDto;
import io.amplicode.jpoint.model.Owner;
import io.amplicode.jpoint.model.Pet;
import io.amplicode.jpoint.model.PetType;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface PetMapper {
    @Mapping(source = "petTypeId", target = "petType.id")
    @Mapping(source = "ownerId", target = "owner.id")
    Pet toEntity(PetDto petDto);

    @InheritInverseConfiguration(name = "toEntity")
    PetDto toDto(Pet pet);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(source = "petTypeId", target = "petType")
    @Mapping(source = "ownerId", target = "owner")
    Pet partialUpdate(PetDto petDto, @MappingTarget Pet pet);

    default Owner createOwner(Long ownerId) {
        if (ownerId == null) {
            return null;
        }
        Owner owner = new Owner();
        owner.setId(ownerId);
        return owner;
    }

    default PetType createPetType(Long petTypeId) {
        if (petTypeId == null) {
            return null;
        }
        PetType petType = new PetType();
        petType.setId(petTypeId);
        return petType;
    }

    @InheritConfiguration(name = "partialUpdate")
    Pet updateWithNull(PetDto petDto, @MappingTarget Pet pet);
}