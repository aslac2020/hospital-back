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
public class ConsultantStatusRequest {
    private Boolean isPatientToken;
    private Boolean isPatientRoomSorting;
    private Boolean isPatientRoomClinic;
    private Boolean  isPatientRoomMedication;
}
