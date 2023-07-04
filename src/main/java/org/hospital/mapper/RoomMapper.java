package org.hospital.mapper;


import org.hospital.domain.entity.Room;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface RoomMapper {

    Room toEntity(RoomRequest request);

    RoomResponse toResponse(Room room);
}
