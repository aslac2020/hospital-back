package org.hospital.usecase;


import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;

import javax.validation.Valid;

public interface PatientIncludeUseCase {

    PatientResponse save(@Valid PatientRequest request);
}
