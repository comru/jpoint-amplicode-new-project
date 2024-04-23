package io.amplicode.jpoint.api;

import io.amplicode.jpoint.dto.PetDto;
import io.amplicode.jpoint.mapper.PetMapper;
import io.amplicode.jpoint.model.Pet;
import io.amplicode.jpoint.repository.PetRepository;
import io.amplicode.jpoint.service.PetService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rest/pets")
@RequiredArgsConstructor
public class PetController {

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    private final PetService petTypeService;

    @PostMapping
    public PetDto save(@RequestBody PetDto petDto) {
        Pet pet = petMapper.toEntity(petDto);
        Pet resultPet = petRepository.save(pet);
        return petMapper.toDto(resultPet);
    }


    @GetMapping
    public Slice<PetDto> find(@RequestParam String q, Pageable pageable) {
        return petTypeService.find(q, pageable);
    }
}

