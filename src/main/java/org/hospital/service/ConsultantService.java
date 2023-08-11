package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hospital.domain.entity.Consultant;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.ConsultantRequest;
import org.hospital.dto.ConsultantResponse;
import org.hospital.dto.ConsultantStatusRequest;
import org.hospital.dto.ConsultantStatusResponse;
import org.hospital.mapper.ConsultantMapper;
import org.hospital.repository.ConsultantRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConsultantService implements Serializable {

    private final ConsultantRepository repository;
    private final ConsultantMapper mapper;

    private final RoomAvaliateService service;

    public Consultant create(ConsultantRequest request) {
        request.setDateConsult(LocalDateTime.now());
        Consultant room = mapper.toEntity(request);
        ConsultantResponse response = mapper.toResponse(room);
        repository.persist((Iterable<Consultant>) response);
        return room;
    }

    public List<Consultant> getAllConsultants() {
        return repository.listAll();
    }

    public Consultant getConsultantById(Long id) {
        Optional<Consultant> optionalConsultant = Optional.ofNullable(repository.findById(id));
        if (optionalConsultant.isEmpty()) {
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deleteConsultant(Long id) {
        Optional<Consultant> optionalConsultant = Optional.ofNullable(repository.findById(id));
        if (optionalConsultant.isEmpty()) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }


    public Consultant updateConsultant(Long id, ConsultantRequest request) {

        Consultant consultantModel = repository.findById(id);

        if (consultantModel == null) {
            throw new WebSocketClientHandshakeException("Consulta com id " + id + "não encontrada :(");
        }

        if (request.getRoomAvaliate() != null && request.getIsPatientRoomSorting() == true) {
            this.service.create(request.getRoomAvaliate());
            request.getRoomAvaliate().setDataAvaliate(LocalDateTime.now());
            consultantModel.setRoomAvaliate(request.getRoomAvaliate());
            consultantModel.setIsPatientRoomSorting(false);
            consultantModel.setIsPatientWaitingClinic(true);
        }

        consultantModel.setRoom(request.getRoom());
        consultantModel.setDoctor(request.getDoctor());
        consultantModel.setPatient(request.getPatient());
        consultantModel.setIsPatientToken(request.getIsPatientToken());
        consultantModel.setIsPatientRoomClinic(request.getIsPatientRoomClinic());
        consultantModel.setIsPatientRoomMedication(request.getIsPatientRoomMedication());
        repository.update(consultantModel);
        return consultantModel;
    }

    public ConsultantStatusResponse updateStatus(Long id, ConsultantStatusRequest request) {

        Consultant consultantModel = mapper.toEntityStatus(request);
        ConsultantStatusResponse response = mapper.toResponseConsult(request);
        consultantModel = repository.findById(id);

        if (consultantModel == null) {
            throw new WebSocketClientHandshakeException("Consulta com id " + id + "não encontrada :(");
        }

        if (request.getIsPatientToken() == true) {
            consultantModel.setIsPatientToken(false);
            consultantModel.setIsPatientRoomSorting(true);
            return response;
        }

        if (request.getIsPatientRoomSorting() == true) {
            consultantModel.setIsPatientRoomSorting(false);
            consultantModel.setIsPatientRoomClinic(true);
            return response;
        }

        if (request.getIsPatientRoomClinic() == true) {
            consultantModel.setIsPatientRoomClinic(false);
            return response;
        }

        return null;
    }

    public Consultant orderConsultByTimeRoomAvaliate() {

        var allConsults = this.getAllConsultants();
        var patient = this.patientsFilterRoomAvaliate(allConsults);
        var orderConsultEnd = new Consultant();

        var consultByPatient = allConsults
                .stream()
                .filter(c -> c.getPatient().getId() == patient.getId())
                .collect(Collectors.toList());

        if (consultByPatient.get(0).getPatient() != null) {
            orderConsultEnd.setId(consultByPatient.get(0).getId());
            orderConsultEnd.setDateConsult(consultByPatient.get(0).getDateConsult());
            orderConsultEnd.setIsConsultEncerred(consultByPatient.get(0).getIsConsultEncerred());
            orderConsultEnd.setPatient(consultByPatient.get(0).getPatient());
            orderConsultEnd.setRoom(consultByPatient.get(0).getRoom());
            orderConsultEnd.setDoctor(consultByPatient.get(0).getDoctor());
            //orderConsultEnd.setIsPatientToken(false);
            //orderConsultEnd.setIsPatientRoomSorting(true);
        }
        return orderConsultEnd;
    }


    public Patient patientsFilterRoomAvaliate(List<Consultant> consults) {
        var patient = new Patient();

        var patientIsPreferentialOld = consults
                .stream()
                .filter(c -> (c.getPatient().getIsPreferential() == true && c.getPatient().getAge() > 70) && c.getIsPatientToken() == true)
                .collect(Collectors.toList());

        if (patientIsPreferentialOld.size() > 0) {
            var patientIsPreferentialOldResult = this.postDatePatient(patientIsPreferentialOld.get(0).getPatient());
            return patientIsPreferentialOldResult;
        }

        var patientIsPreferential = consults
                .stream()
                .filter(c -> (c.getPatient().getIsPreferential() == true || c.getPatient().getAge() < 10) && c.getIsPatientToken() == true)
                .collect(Collectors.toList());

        if (patientIsPreferential.size() > 0) {
            var patientIsPreferentialResult= this.postDatePatient(patientIsPreferential.get(0).getPatient());
            return patientIsPreferentialResult;
        }

        var patientIsNotPreferential = consults
                .stream()
                .filter(c -> c.getPatient().getIsPreferential() == false && c.getIsPatientToken() == true)
                .collect(Collectors.toList());

        if (patientIsNotPreferential.size() > 0) {
            var patientIsNotPreferentialResult = this.postDatePatient(patientIsNotPreferential.get(0).getPatient());
            return patientIsNotPreferentialResult;
        }

        return patient;
    }

    public Patient postDatePatient(Patient patient) {
        var patientEntity = new Patient();
        patientEntity.setId(patient.getId());
        patientEntity.setName(patient.getName());
        patientEntity.setLastName(patient.getLastName());
        patientEntity.setAge(patient.getAge());
        patientEntity.setCpf(patient.getCpf());
        patientEntity.setIsPreferential(patient.getIsPreferential());
        return patientEntity;
    }


    public LocalDateTime orderDates() {
        var allConsults = this.getAllConsultants();
        List<LocalDateTime> listdatas = new ArrayList<LocalDateTime>();

        allConsults.forEach(x -> {
            listdatas.add(x.getDateConsult());
        });

        LocalDateTime dataMaisAntiga = listdatas.get(0);

        for (int i = 1; i < listdatas.size(); i++) {
            LocalDateTime dateAtual = listdatas.get(i);
            var patients = this.patientsFilterRoomAvaliate(allConsults);
            if (dateAtual.isBefore(dataMaisAntiga) || patients != null) {
                dataMaisAntiga = dateAtual;
                return dataMaisAntiga;
            }
        }

        return dataMaisAntiga;

    }
    public Consultant orderConsultByDoctorPatient() {

        var allConsults = this.getAllConsultants();
        var patient = this.patientsFilterDoctor(allConsults);
        var orderConsultEnd = new Consultant();

        var consultByPatient = allConsults
                .stream()
                .filter(c -> c.getPatient().getId() == patient.getId())
                .collect(Collectors.toList());

        if (consultByPatient.get(0).getPatient() != null) {
            orderConsultEnd.setId(consultByPatient.get(0).getId());
            orderConsultEnd.setDateConsult(consultByPatient.get(0).getDateConsult());
            orderConsultEnd.setIsConsultEncerred(consultByPatient.get(0).getIsConsultEncerred());
            orderConsultEnd.setPatient(consultByPatient.get(0).getPatient());
            orderConsultEnd.setRoom(consultByPatient.get(0).getRoom());
            orderConsultEnd.setDoctor(consultByPatient.get(0).getDoctor());
            orderConsultEnd.setIsPatientToken(false);
            orderConsultEnd.setIsPatientRoomSorting(false);
        }
        return orderConsultEnd;
    }

    public Patient patientsFilterDoctor(List<Consultant> consults) {
        var patient = new Patient();

        var patientIsPreferentialOldEmergency = consults
                .stream()
                .filter(c -> (c.getPatient().getIsPreferential() == true && c.getPatient().getAge() > 70)
                        && c.getIsPatientWaitingClinic() == true && (c.getRoomAvaliate().getLevelGravities().equals("URGENT") || c.getRoomAvaliate().getLevelGravities().equals("EMERGENCY")))
                .collect(Collectors.toList());

        if (patientIsPreferentialOldEmergency.size() > 0) {
            var patientIsPreferentialOldResultEmergency = this.postDatePatient(patientIsPreferentialOldEmergency.get(0).getPatient());
            return patientIsPreferentialOldResultEmergency;
        }

        var patientIsPreferentialOldNotEmergency = consults
                .stream()
                .filter(c -> (c.getPatient().getIsPreferential() == true && c.getPatient().getAge() > 70)
                        && c.getIsPatientWaitingClinic() == true &&
                        (c.getRoomAvaliate().getLevelGravities() == "LITTLE_URGENT" || c.getRoomAvaliate().getLevelGravities() == "NOT_URGENT" ))
                .collect(Collectors.toList());

        if (patientIsPreferentialOldNotEmergency.size() > 0) {
            var patientIsPreferentialOldResult = this.postDatePatient(patientIsPreferentialOldNotEmergency.get(0).getPatient());
            return patientIsPreferentialOldResult;
        }

        var patientIsPreferentialEmergency = consults
                .stream()
                .filter(c -> (c.getPatient().getIsPreferential() == true || c.getPatient().getAge() < 10)
                        &&  c.getIsPatientWaitingClinic() == true &&
                        (c.getRoomAvaliate().getLevelGravities() == "EMERGENCY" || c.getRoomAvaliate().getLevelGravities() == "URGENT"))
                .collect(Collectors.toList());

        if (patientIsPreferentialEmergency.size() > 0) {
            var patientIsPreferentialResultEmergency= this.postDatePatient(patientIsPreferentialEmergency.get(0).getPatient());
            return patientIsPreferentialResultEmergency;
        }

        var patientIsPreferentialNotEmergency = consults
                .stream()
                .filter(c -> (c.getPatient().getIsPreferential() == true || c.getPatient().getAge() < 10)
                        &&  c.getIsPatientWaitingClinic() == true &&
                        (c.getRoomAvaliate().getLevelGravities() == "LITTLE_URGENT" || c.getRoomAvaliate().getLevelGravities() == "NOT_URGENT"))
                .collect(Collectors.toList());

        if (patientIsPreferentialNotEmergency.size() > 0) {
            var patientIsPreferentialResult= this.postDatePatient(patientIsPreferentialNotEmergency.get(0).getPatient());
            return patientIsPreferentialResult;
        }


        var patientIsNotPreferentialEmergency = consults
                .stream()
                .filter(c -> c.getPatient().getIsPreferential() == false
                        &&  c.getIsPatientWaitingClinic() == true &&
                        c.getRoomAvaliate().getLevelGravities() == "EMERGENCY" || c.getRoomAvaliate().getLevelGravities() == "URGENT")
                .collect(Collectors.toList());

        if (patientIsNotPreferentialEmergency.size() > 0) {
            var patientIsNotPreferentialResultEmergency = this.postDatePatient(patientIsNotPreferentialEmergency.get(0).getPatient());
            return patientIsNotPreferentialResultEmergency;
        }

        var patientIsNotPreferentialNotEmergency = consults
                .stream()
                .filter(c -> c.getPatient().getIsPreferential() == false
                        &&  c.getIsPatientWaitingClinic() == true &&
                        c.getRoomAvaliate().getLevelGravities() == "LITTLE_URGENT" || c.getRoomAvaliate().getLevelGravities() == "NOT_URGENT")
                .collect(Collectors.toList());

        if (patientIsNotPreferentialNotEmergency.size() > 0) {
            var patientIsNotPreferentialResultEmergency = this.postDatePatient(patientIsNotPreferentialNotEmergency.get(0).getPatient());
            return patientIsNotPreferentialResultEmergency;
        }

        return patient;
    }




}
