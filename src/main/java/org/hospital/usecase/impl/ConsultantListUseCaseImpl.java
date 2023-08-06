package org.hospital.usecase.impl;

import io.smallrye.mutiny.Uni;
import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Consultant;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantResponse;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.repository.ConsultantRepository;
import org.hospital.repository.PatientRepository;
import org.hospital.usecase.ConsultantIncludeUseCase;
import org.hospital.usecase.ConsultantListUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.validation.Valid;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;

@ApplicationScoped
@Slf4j
public class ConsultantListUseCaseImpl implements ConsultantListUseCase {

    @Inject
    ConsultantRepository repository;

    @Inject
    ConsultantMapper mapper;


    @Override
    @Transactional
    public Uni<List<ConsultantResponse>> getList(@Valid ConsultantRequest request) {

        Consultant entity = mapper.toEntity(request);
        List<ConsultantResponse> response = (List<ConsultantResponse>) mapper.toResponse(entity);

        return Uni.createFrom().item(response);
    }
}
