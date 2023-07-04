package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Doctor;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.DoctorRequest;
import org.hospital.dto.DoctorResponse;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.hospital.mapper.DoctorMapper;
import org.hospital.mapper.PatientMapper;
import org.hospital.repository.DoctorRepository;
import org.hospital.repository.PatientRepository;
import org.hospital.usecase.DoctorIncludeUseCase;
import org.hospital.usecase.PatientIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Slf4j
public class DoctorIncludeUseCaseImpl implements DoctorIncludeUseCase {

    @Inject
    DoctorRepository repository;

    @Inject
    DoctorMapper mapper;


    @Override
    @Transactional
    public DoctorResponse save(DoctorRequest request) {

        Doctor entity = includeDoctor(request);
        DoctorResponse response = mapper.toResponse(entity);
        return response;
    }

    private Doctor includeDoctor(DoctorRequest request){

        Doctor doctor = mapper.toEntity(request);
        repository.save(doctor);

        log.info("PacienteIncludeUseCaseImpl - include patient");

        return doctor;
    }


}
