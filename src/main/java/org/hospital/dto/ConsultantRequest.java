package org.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hospital.domain.entity.Doctor;
import org.hospital.domain.entity.Patient;
import org.hospital.domain.entity.Room;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConsultantRequest {
    private LocalDateTime dateConsult;
    private Room room;
    private Patient patient;
    private Doctor doctor;
    private Boolean isPatientToken;
    private Boolean isPatientRoomSorting;
    private Boolean isPatientRoomClinic;
    private Boolean  isPatientRoomMedication;
    private Boolean isConsultEncerred;
}
