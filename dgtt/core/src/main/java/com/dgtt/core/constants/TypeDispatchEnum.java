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
public enum TypeDispatchEnum {
    NEW_DISPATCH(1L, "Công văn khai báo mới"),OLD_DISPATCH(2L,"Công văn khai báo bổ sung");
    private final Long type;
    private final String typeDescription;

    public Long getType() {
        return type;
    }

    public String getTypeDescription() {
        return typeDescription;
    }

    private TypeDispatchEnum(Long type, String typeDescription) {
        this.type = type;
        this.typeDescription = typeDescription;
    }
    
}
