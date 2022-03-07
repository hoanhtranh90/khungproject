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
public enum InterventionServicesEnum {
    ATCN(1L, "An toàn cứu nạn"), PCBL(2L, "Phòng chống bão lụt"),DDCC(3L,"Di động công cộng"),DDDR(4L,"Di động dùng riêng"),
    HK(5L,"Hàng không"),PTTH(6L,"Phát thanh truyền hình"),QPAN(7L,"Quốc phòng an ninh"),KHAC(8L,"Khác");
    private Long typeInterf;
    private String typefInterfDescription;

    private InterventionServicesEnum(Long typeInterf, String typefInterfDescription) {
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
        for (InterventionServicesEnum statusDescription : InterventionServicesEnum.values()) {
            if (statusDescription.getTypeInterf().equals(typeInterf)) {
                return statusDescription.getTypefInterfDescription();
            }
        }
        return null;
    }

}
