package org.hospital.usecase.impl;

import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Room;
import org.hospital.domain.entity.RoomAvaliate;
import org.hospital.dto.RoomAvaliateRequest;
import org.hospital.dto.RoomAvaliateResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.hospital.mapper.RoomAvaliateMapper;
import org.hospital.mapper.RoomMapper;
import org.hospital.repository.RoomAvaliateRepository;
import org.hospital.repository.RoomRepository;
import org.hospital.usecase.RoomAvaliateIncludeUseCase;
import org.hospital.usecase.RoomIncludeUseCase;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

@ApplicationScoped
@Slf4j
public class RoomAvaliateIncludeUseCaseImpl implements RoomAvaliateIncludeUseCase {

    @Inject
    RoomAvaliateRepository repository;

    @Inject
    RoomAvaliateMapper mapper;


    @Override
    @Transactional
    public RoomAvaliateResponse save(RoomAvaliateRequest request) {
        RoomAvaliate entity = includeAvaliate(request);
        RoomAvaliateResponse response = mapper.toResponse(entity);
        return response;
    }

    private RoomAvaliate includeAvaliate(RoomAvaliateRequest request){

        RoomAvaliate room = mapper.toEntity(request);
        repository.save(room);

        log.info("PacienteIncludeUseCaseImpl - include patient");

        return room;
    }


}
