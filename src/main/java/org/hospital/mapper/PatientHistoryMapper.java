package org.hospital.mapper;

import org.hospital.domain.entity.PatientHistory;
import org.hospital.dto.PatientHistoryRequest;
import org.hospital.dto.PatientHistoryResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PatientHistoryMapper {

    PatientHistory toEntity(PatientHistoryRequest request);

    PatientHistoryResponse toResponse(PatientHistory patient);
}
