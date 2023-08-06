package org.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hospital.domain.entity.Patient;
import org.hospital.enums.LevelGravities;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoomAvaliateRequest {

    private Double pressureBlood;
    private Double temperature;
    private Integer saturation;
    private Integer meditionDiabetes;
    private String levelGravities;
    private String observation;
    private LocalDateTime dataAvaliate;
}
