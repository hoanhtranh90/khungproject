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
public enum TypeBtsEnum {
    BTS("BTS", "BTS"), VIBA("VIBA", "BTS_VIBA");

    private final String type;
    private final String regex;

    private TypeBtsEnum(String type, String regex) {
        this.type = type;
        this.regex = regex;
    }

    public String getType() {
        return type;
    }

    public String getRegex() {
        return regex;
    }

}
