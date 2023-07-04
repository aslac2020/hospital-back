package org.hospital.usecase;


import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;

import javax.validation.Valid;

public interface ConsultantIncludeUseCase {

    ConsultantResponse save(@Valid ConsultantRequest request);
}
