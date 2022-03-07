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
public enum TypeTelcoEnum {

    VIETTEL("04", "VIETTEL"), ARFM("03", "ARFM"), MOBIFONE("01", "MOBIFONE"), VIETNAMMOBILE("02", "VIETNAMMOBILE"),VNPT("05","VNPT");
    private final String type;
    private final String typeName;

    private TypeTelcoEnum(String type, String typeName) {
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
        for (TypeTelcoEnum typeTelcoEnum : TypeTelcoEnum.values()) {
            if (typeTelcoEnum.getType().equals(type)) {
                return typeTelcoEnum.getTypeName();
            }
        }
        return null;
    }
}
