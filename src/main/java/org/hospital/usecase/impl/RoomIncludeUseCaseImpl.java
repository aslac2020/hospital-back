package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Patient;
import org.hospital.domain.entity.Room;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.hospital.mapper.RoomMapper;
import org.hospital.repository.RoomRepository;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Slf4j
public class RoomIncludeUseCaseImpl implements RoomIncludeUseCase {

    @Inject
    RoomRepository repository;

    @Inject
    RoomMapper mapper;


    @Override
    @Transactional
    public RoomResponse save(RoomRequest request) {

       Room entity = includeRoom(request);
        RoomResponse response = mapper.toResponse(entity);
        return response;
    }

    private Room includeRoom(RoomRequest request){

        Room room = mapper.toEntity(request);
        repository.save(room);

        log.info("PacienteIncludeUseCaseImpl - include patient");

        return room;
    }
}
