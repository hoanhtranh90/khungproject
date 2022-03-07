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
public enum TypeInterfEnum {
    MANG_DI_DONG(1L, "Mạng thông tin di động"), DAI_VO_TUYEN(2L, "Đài vô tuyến khác");
    private Long typeInterf;
    private String typefInterfDescription;

    private TypeInterfEnum(Long typeInterf, String typefInterfDescription) {
        this.typeInterf = typeInterf;
        this.typefInterfDescription = typefInterfDescription;
    }

    public Long getTypeInterf() {
        return typeInterf;
    }

    public String getTypefInterfDescription() {
        return typefInterfDescription;
    }

    
    public static String getTypefInterfDescription(Long typeInterf) {
        for (TypeInterfEnum statusDescription : TypeInterfEnum.values()) {
            if (statusDescription.getTypeInterf().equals(typeInterf)) {
                return statusDescription.getTypefInterfDescription();
            }
        }
        return null;
    }

}
