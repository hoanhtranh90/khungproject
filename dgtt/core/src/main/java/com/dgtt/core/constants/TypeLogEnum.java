/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.constants;

/**
 *
 * @author sadfsafbhsaid
 */
public enum TypeLogEnum {
    NOIBO(1L, "Web nội bộ"), CONGKHAI(2L, "Web công khai");
    private final Long type;
    private final String typeDescription;

    private TypeLogEnum(Long type, String typeDescription) {
        this.type = type;
        this.typeDescription = typeDescription;
    }

    public Long getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    public static String getTypeDescription(Long type) {
        for (TypeLogEnum statusDescription : TypeLogEnum.values()) {
            if (statusDescription.getType().equals(type)) {
                return statusDescription.getTypeDescription();
            }
        }
        return null;
    }
}
