package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.hospital.mapper.PatientMapper;
import org.hospital.repository.PatientRepository;
import org.hospital.usecase.PatientIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Slf4j
public class PatientIncludeUseCaseImpl implements PatientIncludeUseCase {

    @Inject
    PatientRepository repository;

    @Inject
    PatientMapper mapper;


    @Override
    @Transactional
    public PatientResponse save(PatientRequest request) {

        Patient entity = includePatient(request);
        PatientResponse response = mapper.toResponse(entity);
        return response;
    }

    private Patient includePatient(PatientRequest request){

        Patient patient = mapper.toEntity(request);
        repository.save(patient);

        log.info("PacienteIncludeUseCaseImpl - include patient");

        return patient;
    }
}
