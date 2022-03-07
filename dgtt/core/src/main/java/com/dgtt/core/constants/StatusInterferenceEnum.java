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
public enum StatusInterferenceEnum {
    CHO_TIEP_NHAN(1L, "Chờ tiếp nhận"),CHO_PHAN_CONG(2L, "Chờ phân công");
    private final Long status;
    private final String statusDescription;

    private StatusInterferenceEnum(Long status, String statusDescription) {
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
        for (StatusInterferenceEnum statusDescription : StatusInterferenceEnum.values()) {
            if (statusDescription.getStatus().equals(status)) {
                return statusDescription.getStatusDescription();
            }
        }
        return null;
    }
}
