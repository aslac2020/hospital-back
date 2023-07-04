package org.hospital.usecase;


import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;

import javax.validation.Valid;

public interface RoomIncludeUseCase {

    RoomResponse save(@Valid RoomRequest request);
}
