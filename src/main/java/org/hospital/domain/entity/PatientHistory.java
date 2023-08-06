package org.hospital.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "historico_paciente")
public class PatientHistory implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String prescriton;

    @Column(nullable = true)
    private String examsPrescribed;

    private String orientations;

    @Column(nullable = true)
    private Boolean isCertificate;

    @Column(nullable = true)
    private String resultsExams;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dataConsult;

}
