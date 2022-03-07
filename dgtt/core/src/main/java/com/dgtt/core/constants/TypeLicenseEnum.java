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
public enum TypeLicenseEnum {
    ORGANIZATION(1L, "Giấy phép tổ chức/cá nhân"), BTS(2L, "Giấy phép trạm gốc");
    private final Long type;
    private final String typeDescription;

    public Long getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    private TypeLicenseEnum(Long type, String typeDescription) {
        this.type = type;
        this.typeDescription = typeDescription;
    }

}
