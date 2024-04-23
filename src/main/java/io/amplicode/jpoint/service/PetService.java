package io.amplicode.jpoint.service;

import io.amplicode.jpoint.dto.PetDto;
import io.amplicode.jpoint.mapper.PetMapper;
import io.amplicode.jpoint.model.PetType;
import io.amplicode.jpoint.repository.PetRepository;
import io.amplicode.jpoint.repository.PetTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class PetService {

    private final PetRepository petRepository;

    private final PetMapper petMapper;

    public Slice<PetDto> find(String q, Pageable pageable) {
        return petRepository
                .findAllByFilter(q, pageable)
                .map(petMapper::toDto);
    }
}
