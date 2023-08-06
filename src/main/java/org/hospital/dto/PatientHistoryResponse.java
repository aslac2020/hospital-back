package org.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hospital.domain.entity.Patient;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientHistoryResponse {
    private String examsPrescribed;
    private String orientations;
    private Boolean isCertificate;
    private String resultsExams;
    private Patient patient;
    private LocalDateTime dataConsult;
}
