package com.lhmgiw.jpametamodel.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum StatusEnum {
    ACTIVE("ACTIVE", "active"),
    INACTIVE("INACTIVE", "inactive"),
    DELETE("DELETE", "delete");

    private String code;
    private String description;

    public static StatusEnum getEnum(String code){
        switch (code){
            case "ACTIVE":
                return ACTIVE;
            case "INACTIVE":
                return INACTIVE;
            case "DELETE":
                return DELETE;
            default:
                return null;
        }
    }
}
