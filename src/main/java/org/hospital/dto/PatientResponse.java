package org.hospital.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PatientResponse {
    private String name;
    private String lastName;
    private Integer age;
    private String cpf;
    private Boolean isPreferential;
}
