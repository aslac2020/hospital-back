package org.hospital.mapper;


import org.hospital.domain.entity.Consultant;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantResponse;
import org.hospital.dto.ConsultantStatusRequest;
import org.hospital.dto.ConsultantStatusResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "cdi")
public interface ConsultantMapper {

    Consultant toEntity(ConsultantRequest request);

    Consultant toEntityStatus(ConsultantStatusRequest request);

    ConsultantResponse toResponse(Consultant consult);

    ConsultantRequest toRequest(Consultant consultant);

    ConsultantStatusResponse toResponseConsult(ConsultantStatusRequest request);
}
