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
public enum ProcessTypePlanEnum {
    NGUOI_PHE_DUYET(1L, "Người phê duyệt"), NGUOI_PHOI_HOP(2L, "Người phối hợp");
    private final Long type;
    private final String typeDescription;

    public Long getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    private ProcessTypePlanEnum(Long type, String typeDescription) {
        this.type = type;
        this.typeDescription = typeDescription;
    }

}
