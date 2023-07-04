package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Consultant;
import org.hospital.domain.entity.Room;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.mapper.RoomMapper;
import org.hospital.repository.ConsultantRepository;
import org.hospital.repository.RoomRepository;
import org.hospital.usecase.ConsultantIncludeUseCase;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Slf4j
public class ConsultantIncludeUseCaseImpl implements ConsultantIncludeUseCase {

    @Inject
    ConsultantRepository repository;

    @Inject
    ConsultantMapper mapper;


    @Override
    @Transactional
    public ConsultantResponse save(ConsultantRequest request) {

       Consultant entity = includeConsultant(request);
        ConsultantResponse response = mapper.toResponse(entity);
        return response;
    }

    private Consultant includeConsultant(ConsultantRequest request){

        Consultant consultant = mapper.toEntity(request);
        repository.save(consultant);

        log.info("PacienteIncludeUseCaseImpl - include patient");

        return consultant;
    }
}
