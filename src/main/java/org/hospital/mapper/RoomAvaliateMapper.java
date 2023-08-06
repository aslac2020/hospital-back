package org.hospital.mapper;


import org.hospital.domain.entity.Room;
import org.hospital.domain.entity.RoomAvaliate;
import org.hospital.dto.RoomAvaliateRequest;
import org.hospital.dto.RoomAvaliateResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "cdi")
public interface RoomAvaliateMapper {

    RoomAvaliate toEntity(RoomAvaliateRequest request);

    RoomAvaliateResponse toResponse(RoomAvaliate roomAvaliate);
}
