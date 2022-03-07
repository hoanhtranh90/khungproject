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
public enum StatusInterferencePlanEnum {
    CHO_GUI_DUYET(0L, "Chờ gửi duyệt"), CHO_DUYET(1L, "Chờ duyệt"), DUYET_CAP_PHONG(2L, "Duyệt cấp phòng"), DA_DUYET(3L, "Đã duyệt"), TU_CHOI(4L, "Từ chối");
    private final Long status;
    private final String statusDescription;

    private StatusInterferencePlanEnum(Long status, String statusDescription) {
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
        for (StatusInterferencePlanEnum statusDescription : StatusInterferencePlanEnum.values()) {
            if (statusDescription.getStatus().equals(status)) {
                return statusDescription.getStatusDescription();
            }
        }
        return null;
    }
}
