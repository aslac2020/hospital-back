package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import org.hospital.domain.entity.Room;
import org.hospital.domain.entity.RoomAvaliate;
import org.hospital.dto.RoomAvaliateRequest;
import org.hospital.dto.RoomAvaliateResponse;
import org.hospital.dto.RoomRequest;
import org.hospital.dto.RoomResponse;
import org.hospital.mapper.RoomAvaliateMapper;
import org.hospital.mapper.RoomMapper;
import org.hospital.repository.RoomAvaliateRepository;
import org.hospital.repository.RoomRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class RoomAvaliateService {

    private final RoomAvaliateRepository repository;
    private final RoomAvaliateMapper mapper;

    public RoomAvaliate create(RoomAvaliate room){
        repository.persist(room);
        return room;
    }

    public List<RoomAvaliate> getAllAvaliates(){
        return repository.listAll();
    }

    public RoomAvaliate getAvaliateById(Long id){
        Optional<RoomAvaliate> optionalRoom = Optional.ofNullable(repository.findById(id));
        if(optionalRoom.isEmpty()){
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deleteAvaliate(Long id){
        Optional<RoomAvaliate> optionalRoom = Optional.ofNullable(repository.findById(id));
        if(optionalRoom.isEmpty()){
            throw new NotFoundException();
        }
         repository.deleteById(id);
    }

    public RoomAvaliate updateAvaliate(Long id, RoomAvaliate room){
        RoomAvaliate roomModel = repository.findById(id);

        if(roomModel == null){
            throw new WebSocketClientHandshakeException("Sala com id " + id + "não encontrada :(");
        }

        roomModel.setTemperature(room.getTemperature());
        roomModel.setLevelGravities(room.getLevelGravities());
        roomModel.setPressureBlood(room.getPressureBlood());
        roomModel.setMeditionDiabetes(room.getMeditionDiabetes());
        roomModel.setObservation(room.getObservation());
        roomModel.setSaturation(room.getSaturation());
        roomModel.setDataAvaliate(room.getDataAvaliate());
        return roomModel;
    }





}
