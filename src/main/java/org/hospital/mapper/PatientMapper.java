package org.hospital.mapper;

import org.hospital.domain.entity.Patient;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface PatientMapper {

    Patient toEntity(PatientRequest request);

    PatientResponse toResponse(Patient patient);
}
