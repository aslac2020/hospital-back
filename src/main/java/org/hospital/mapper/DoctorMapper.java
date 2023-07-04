package org.hospital.mapper;

import org.hospital.domain.entity.Doctor;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.DoctorRequest;
import org.hospital.dto.DoctorResponse;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface DoctorMapper {

    Doctor toEntity(DoctorRequest request);

    DoctorResponse toResponse(Doctor doctor);
}
