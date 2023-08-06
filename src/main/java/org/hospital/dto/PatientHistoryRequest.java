package org.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hospital.domain.entity.Patient;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientHistoryRequest {

    private String examsPrescribed;
    private String orientations;
    private Boolean isCertificate;
    private String resultsExams;
    private Patient patient;
    private LocalDateTime dataConsult;
}
