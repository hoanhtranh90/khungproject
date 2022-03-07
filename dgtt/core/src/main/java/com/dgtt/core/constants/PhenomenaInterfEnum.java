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
public enum PhenomenaInterfEnum {
    THOAI(1L, "Thoại"), NHAC(2L, "Nhạc"), UE(3L, "Tiếng ù rè"), KHAC(4L, "Khác");

    private final Long phenomenaInterf;
    private final String phenomenaInterfDescription;

    private PhenomenaInterfEnum(Long phenomenaInterf, String phenomenaInterfDescription) {
        this.phenomenaInterf = phenomenaInterf;
        this.phenomenaInterfDescription = phenomenaInterfDescription;
    }

    public Long getPhenomenaInterf() {
        return phenomenaInterf;
    }

    public String getPhenomenaInterfDescription() {
        return phenomenaInterfDescription;
    }
    
    public static String getPhenomenaInterfDescription(Long phenomenaInterf) {
        for (PhenomenaInterfEnum statusDescription : PhenomenaInterfEnum.values()) {
            if (statusDescription.getPhenomenaInterf().equals(phenomenaInterf)) {
                return statusDescription.getPhenomenaInterfDescription();
            }
        }
        return null;
    }
}
