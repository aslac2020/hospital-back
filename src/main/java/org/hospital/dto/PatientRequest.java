package org.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientRequest {
    private String name;
    private String lastName;
    private Integer age;
    private String cpf;
    private Boolean isPreferential;
}
