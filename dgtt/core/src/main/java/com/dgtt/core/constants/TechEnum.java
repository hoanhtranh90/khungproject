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
public enum TechEnum {
    GSM("GSM", "2G"), LTE("LTE", "4G"),WCDMA("WCDMA","3G");
    private final String type;
    private final String typeName;

    private TechEnum(String type, String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public String getType() {
        return type;
    }

    public String getTypeName() {
        return typeName;
    }

    public static String getTypeName(String type) {
        for (TechEnum typeTelcoEnum : TechEnum.values()) {
            if (typeTelcoEnum.getType().equals(type)) {
                return typeTelcoEnum.getTypeName();
            }
        }
        return null;
    }
    public static String getType(String typeName) {
        for (TechEnum typeTelcoEnum : TechEnum.values()) {
            if (typeTelcoEnum.getTypeName().equals(typeName)) {
                return typeTelcoEnum.getType();
            }
        }
        return null;
    }
}
