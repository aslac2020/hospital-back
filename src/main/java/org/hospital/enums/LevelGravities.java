package org.hospital.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LevelGravities {
    NOT_URGENT("BLUE"),
    LITTLE_URGENT("GREEN"),
    URGENT("YELLOW"),
    EMERGENCY("RED");

    private final String value;

}
