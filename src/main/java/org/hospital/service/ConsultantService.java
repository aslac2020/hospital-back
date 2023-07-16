package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import org.hospital.domain.entity.Consultant;
import org.hospital.domain.entity.Room;
import org.hospital.dto.*;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.repository.ConsultantRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class ConsultantService  implements Serializable {

    private final ConsultantRepository repository;
    private final ConsultantMapper mapper;

    public Consultant create(ConsultantRequest request){
        request.setDateConsult(LocalDateTime.now());
        Consultant room = mapper.toEntity(request);
        ConsultantResponse response = mapper.toResponse(room);
        repository.persist((Iterable<Consultant>) response);
        return room;
    }

    public List<Consultant> getAllConsultants(){
        return repository.listAll();
    }

    public Consultant getConsultantById(Long id){
        Optional<Consultant> optionalConsultant = Optional.ofNullable(repository.findById(id));
        if(optionalConsultant.isEmpty()){
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deleteConsultant(Long id){
        Optional<Consultant> optionalConsultant = Optional.ofNullable(repository.findById(id));
        if(optionalConsultant.isEmpty()){
            throw new NotFoundException();
        }
         repository.deleteById(id);
    }


    public Consultant updateConsultant(Long id, ConsultantRequest request){

        Consultant consultantModel = repository.findById(id);

        if(consultantModel == null){
            throw new WebSocketClientHandshakeException("Consulta com id " + id + "não encontrada :(");
        }

        //consultantModel.setDateConsult(request.getDateConsult());
        consultantModel.setRoom(request.getRoom());
        consultantModel.setDoctor(request.getDoctor());
        consultantModel.setPatient(request.getPatient());
        consultantModel.setIsPatientToken(request.getIsPatientToken());
        consultantModel.setIsPatientRoomClinic(request.getIsPatientRoomClinic());
        consultantModel.setIsPatientRoomMedication(request.getIsPatientRoomMedication());
        consultantModel.setIsPatientRoomSorting(request.getIsPatientRoomSorting());
        repository.update(consultantModel);
        return consultantModel;
    }

    public ConsultantStatusResponse updateStatus(Long id, ConsultantStatusRequest request){

        Consultant consultantModel = mapper.toEntityStatus(request);
        ConsultantStatusResponse response = mapper.toResponseConsult(request);
        consultantModel = repository.findById(id);

        if(consultantModel == null){
            throw new WebSocketClientHandshakeException("Consulta com id " + id + "não encontrada :(");
        }

        if(request.getIsPatientToken() == true){
            consultantModel.setIsPatientToken(false);
            consultantModel.setIsPatientRoomSorting(true);
            return response;
        }

        if(request.getIsPatientRoomSorting() == true){
            consultantModel.setIsPatientRoomSorting(false);
            consultantModel.setIsPatientRoomClinic(true);
            return response;
        }

        if(request.getIsPatientRoomClinic() == true){
            consultantModel.setIsPatientRoomClinic(false);
            return response;
        }

        return null;
    }





}
