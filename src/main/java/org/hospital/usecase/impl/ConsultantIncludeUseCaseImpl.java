package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Consultant;
import org.hospital.domain.entity.Patient;
import org.hospital.domain.entity.Room;
import org.hospital.dto.*;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.mapper.RoomMapper;
import org.hospital.repository.ConsultantRepository;
import org.hospital.repository.PatientRepository;
import org.hospital.repository.RoomRepository;
import org.hospital.usecase.ConsultantIncludeUseCase;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@ApplicationScoped
@Slf4j
public class ConsultantIncludeUseCaseImpl implements ConsultantIncludeUseCase {

    @Inject
    ConsultantRepository repository;

    @Inject
    PatientRepository patientRepository;

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

        request.setDateConsult(LocalDateTime.now());
        Consultant consultant = mapper.toEntity(request);

        repository.save(consultant);

        log.info("PacienteIncludeUseCaseImpl - include patient");

        return consultant;
    }
}
