package org.hospital.usecase;


import org.hospital.dto.RoomAvaliateRequest;
import org.hospital.dto.RoomAvaliateResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;

import javax.validation.Valid;

public interface RoomAvaliateIncludeUseCase {

    RoomAvaliateResponse save(@Valid RoomAvaliateRequest request);
}
