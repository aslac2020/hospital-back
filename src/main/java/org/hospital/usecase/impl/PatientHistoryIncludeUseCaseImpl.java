package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.PatientHistory;
import org.hospital.dto.PatientHistoryRequest;
import org.hospital.dto.PatientHistoryResponse;
import org.hospital.mapper.PatientHistoryMapper;
import org.hospital.repository.PatientHistoryRepository;
import org.hospital.usecase.PatientHistoryIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@Slf4j
public class PatientHistoryIncludeUseCaseImpl implements PatientHistoryIncludeUseCase {

    @Inject
    PatientHistoryRepository repository;

    @Inject
    PatientHistoryMapper mapper;


    @Override
    public PatientHistoryResponse save(PatientHistoryRequest request) {
        PatientHistory entity = includePatient(request);
        PatientHistoryResponse response = mapper.toResponse(entity);
        return response;
    }


    private PatientHistory includePatient(PatientHistoryRequest request) {

        PatientHistory patientHistory = mapper.toEntity(request);
        repository.save(patientHistory);

        log.info("PatientHistoryIncludeUseCaseImpl - include patient history");

        return patientHistory;
    }


}
