package org.hospital.domain.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jdk.jfr.Timespan;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

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
    @JoinColumn(name = "medico_id")
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "paciente_id")
    private Patient patient;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sala_id")
    private Room room;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy 00:00:00")
    private LocalDateTime dateConsult;

    private Boolean isPatientToken;

    private Boolean isPatientRoomSorting;

    private Boolean isPatientRoomClinic;

    private Boolean  isPatientRoomMedication;

    private Boolean isConsultEncerred = false;

}
