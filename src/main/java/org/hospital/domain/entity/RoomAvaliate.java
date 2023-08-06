package org.hospital.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hospital.enums.LevelGravities;

import javax.json.bind.annotation.JsonbDateFormat;
import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "triagem")
public class RoomAvaliate implements Serializable {
   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   private Double pressureBlood;
   private Double temperature;
   private Integer saturation;
   private Integer meditionDiabetes;
   private String observation;
   private String levelGravities;

   @JsonbDateFormat(value = "yyyy-MM-dd HH:mm:ss")
   private LocalDateTime dataAvaliate;

}
