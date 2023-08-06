package org.hospital.service;

import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakeException;
import lombok.RequiredArgsConstructor;
import org.hospital.domain.entity.PatientHistory;
import org.hospital.dto.PatientHistoryRequest;
import org.hospital.dto.PatientHistoryResponse;
import org.hospital.mapper.PatientHistoryMapper;
import org.hospital.repository.PatientHistoryRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
@Transactional
@RequiredArgsConstructor
public class PatientHistoryService {

    private final PatientHistoryRepository repository;
    private final PatientHistoryMapper mapper;

    public PatientHistory create(PatientHistoryRequest request) {
        PatientHistory patient = mapper.toEntity(request);
        PatientHistoryResponse response = mapper.toResponse(patient);
        repository.persist((Iterable<PatientHistory>) response);
        return patient;
    }

    public List<PatientHistory> getAllHistoryPatients() {
        return repository.listAll();
    }


    public PatientHistory getHistoryPatientById(Long id) {
        Optional<PatientHistory> optionalPatient = Optional.ofNullable(repository.findById(id));
        if (optionalPatient.isEmpty()) {
            throw new NotFoundException();
        }
        return repository.findById(id);
    }

    public void deleteHistoryPatient(Long id) {
        Optional<PatientHistory> optionalPatient = Optional.ofNullable(repository.findById(id));
        if (optionalPatient.isEmpty()) {
            throw new NotFoundException();
        }
        repository.deleteById(id);
    }

    public PatientHistory updateHistoryPatient(Long id, PatientHistory history) {
        PatientHistory patientHistoryModel = repository.findById(id);

        if (patientHistoryModel == null) {
            throw new WebSocketClientHandshakeException("Paciente com id " + id + "não encontrado :(");
        }

        patientHistoryModel.setDataConsult(history.getDataConsult());
        patientHistoryModel.setOrientations(history.getOrientations());
        patientHistoryModel.setPrescriton(history.getPrescriton());
        patientHistoryModel.setExamsPrescribed(history.getExamsPrescribed());
        patientHistoryModel.setResultsExams(history.getResultsExams());
        patientHistoryModel.setIsCertificate(history.getIsCertificate());
        patientHistoryModel.setPatient(history.getPatient());

        return patientHistoryModel;
    }


}
