/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.constants;

/**
 *
 * @author DELL
 */
public enum TypeCellNoiseInfoEnum {
    USER_DECLARATION(1, "người dùng khai báo"), TELCO_DECLARATION(2, "lấy từ nhà mạng");

    private final Integer type;
    private final String typeDescription;

    private TypeCellNoiseInfoEnum(Integer type, String typeDescription) {
        this.type = type;
        this.typeDescription = typeDescription;
    }

    public Integer getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    
    public static String getTypeDescription(Integer type) {
        for (TypeCellNoiseInfoEnum typeCellNoiseInfoEnum : TypeCellNoiseInfoEnum.values()) {
            if (typeCellNoiseInfoEnum.getType().equals(type)) {
                return typeCellNoiseInfoEnum.getTypeDescription();
            }
        }
        return null;
    }
}
