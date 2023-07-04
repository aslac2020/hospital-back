package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import org.hospital.domain.entity.Doctor;
import org.hospital.domain.entity.Patient;
import org.hospital.dto.DoctorRequest;
import org.hospital.dto.DoctorResponse;
import org.hospital.dto.PatientRequest;
import org.hospital.dto.PatientResponse;
import org.hospital.mapper.DoctorMapper;
import org.hospital.mapper.PatientMapper;
import org.hospital.repository.DoctorRepository;
import org.hospital.repository.PatientRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository repository;
    private final DoctorMapper mapper;

    public Doctor create(DoctorRequest request){
        Doctor doctor = mapper.toEntity(request);
        DoctorResponse response = mapper.toResponse(doctor);
        repository.persist((Iterable<Doctor>) response);
        return doctor;
    }

    public List<Doctor> getAllDoctors(){
        return repository.listAll();
    }

    public Doctor getDoctorById(Long id){
        Optional<Doctor> optionalDoctor = Optional.ofNullable(repository.findById(id));
        if(optionalDoctor.isEmpty()){
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deleteDoctor(Long id){
        Optional<Doctor> optionalDoctor = Optional.ofNullable(repository.findById(id));
        if(optionalDoctor.isEmpty()){
            throw new NotFoundException();
        }
         repository.deleteById(id);
    }

    public Doctor updateDoctor(Long id, Doctor doctor){
        Doctor doctorModel = repository.findById(id);

        if(doctorModel == null){
            throw new WebSocketClientHandshakeException("Medico com id " + id + "não encontrado :(");
        }

        doctorModel.setName(doctor.getName());
        doctorModel.setCrm(doctor.getCrm());
        doctorModel.setSpecialties(doctor.getSpecialties());

        return doctorModel;
    }





}
