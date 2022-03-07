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
public enum StatusAssignmentEnum {
    CHO_TIEP_NHAN(1L, "Chờ tiếp nhận"),DA_TIEP_NHAN(2L,"Đã tiếp nhân"),DANG_XU_LY(2L,"Đang xử lý");
    private final Long status;
    private final String statusDescription;

    private StatusAssignmentEnum(Long status, String statusDescription) {
        this.status = status;
        this.statusDescription = statusDescription;
    }

    public Long getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public static String getStatusDescription(Long status) {
        for (StatusAssignmentEnum statusDescription : StatusAssignmentEnum.values()) {
            if (statusDescription.getStatus().equals(status)) {
                return statusDescription.getStatusDescription();
            }
        }
        return null;
    }
}
