package org.hospital.usecase;


import org.hospital.dto.DoctorRequest;
import org.hospital.dto.DoctorResponse;

import javax.validation.Valid;

public interface DoctorIncludeUseCase {

    DoctorResponse save(@Valid DoctorRequest request);
}
