package org.hospital.usecase;


import org.hospital.dto.PatientHistoryRequest;
import org.hospital.dto.PatientHistoryResponse;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;

import javax.validation.Valid;

public interface PatientHistoryIncludeUseCase {

    PatientHistoryResponse save(@Valid PatientHistoryRequest request);
}
