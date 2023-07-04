package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import org.hospital.domain.entity.Room;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.hospital.mapper.RoomMapper;
import org.hospital.repository.RoomRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;
    private final RoomMapper mapper;

    public Room create(RoomRequest request){
        Room room = mapper.toEntity(request);
        RoomResponse response = mapper.toResponse(room);
        repository.persist((Iterable<Room>) response);
        return room;
    }

    public List<Room> getAllRooms(){
        return repository.listAll();
    }

    public Room getRoomById(Long id){
        Optional<Room> optionalRoom = Optional.ofNullable(repository.findById(id));
        if(optionalRoom.isEmpty()){
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deleteRoom(Long id){
        Optional<Room> optionalRoom = Optional.ofNullable(repository.findById(id));
        if(optionalRoom.isEmpty()){
            throw new NotFoundException();
        }
         repository.deleteById(id);
    }

    public Room updateRoom(Long id, Room room){
        Room roomModel = repository.findById(id);

        if(roomModel == null){
            throw new WebSocketClientHandshakeException("Sala com id " + id + "não encontrada :(");
        }

        roomModel.setNumberRoom(room.getNumberRoom());
        roomModel.setIsAvailable(room.getIsAvailable());
        return roomModel;
    }





}
