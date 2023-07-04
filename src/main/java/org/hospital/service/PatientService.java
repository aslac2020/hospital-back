package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.hospital.mapper.PatientMapper;
import org.hospital.repository.PatientRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository repository;
    private final PatientMapper mapper;

    public Patient create(PatientRequest request){
        Patient patient = mapper.toEntity(request);
        PatientResponse response = mapper.toResponse(patient);
        repository.persist((Iterable<Patient>) response);
        return patient;
    }

    public List<Patient> getAllPatients(){
        return repository.listAll();
    }

    public Patient getPatientById(Long id){
        Optional<Patient> optionalPatient = Optional.ofNullable(repository.findById(id));
        if(optionalPatient.isEmpty()){
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deletePatient(Long id){
        Optional<Patient> optionalPatient = Optional.ofNullable(repository.findById(id));
        if(optionalPatient.isEmpty()){
            throw new NotFoundException();
        }
         repository.deleteById(id);
    }

    public Patient updatePatient(Long id, Patient patient){
        Patient patientModel = repository.findById(id);

        if(patientModel == null){
            throw new WebSocketClientHandshakeException("Paciente com id " + id + "não encontrado :(");
        }

        patientModel.setName(patient.getName());
        patientModel.setLastName(patient.getLastName());
        patientModel.setCpf(patient.getCpf());
        patientModel.setAge(patient.getAge());

        return patientModel;
    }





}
