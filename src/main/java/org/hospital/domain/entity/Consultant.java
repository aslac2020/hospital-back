package org.hospital.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Timespan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "consultas")
public class Consultant implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "patient_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private Room room;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "roomavaliate_id")
    private RoomAvaliate roomAvaliate;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patientHistory_id")
    private PatientHistory patientHistory;

    @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateConsult;

    private Boolean isPatientToken;

    private Boolean isPatientRoomSorting;

    private Boolean isPatientWaitingClinic;

    private Boolean isPatientRoomClinic;

    private Boolean  isPatientRoomMedication;

    private Boolean isConsultEncerred = false;

}
