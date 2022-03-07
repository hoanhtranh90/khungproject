package com.dgtt.core.constants;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DELL
 */
public enum StatusPlanEnum {

    CHO_GUI_DUYET(0L, "Chờ gửi phê duyệt"), CHO_DUYET(1L, "Chờ duyệt"), DUYET_CAP_PHONG(2L, "Duyệt cấp phòng"),
    DA_DUYET(3L, "Đã duyệt"), TU_CHOI(4L, "Từ chối");

    private final Long status;
    private final String statusDescription;

    private StatusPlanEnum(Long status, String statusDescription) {
        this.status = status;
        this.statusDescription = statusDescription;
    }

    public Long getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public static List getStatusPlanIsActive() {
        return Arrays.asList(CHO_DUYET.getStatus(), DUYET_CAP_PHONG.getStatus(), DA_DUYET.getStatus(), TU_CHOI.getStatus());
    }

    public static String getStatusDescription(Long status) {
        for (StatusPlanEnum statusDescription : StatusPlanEnum.values()) {
            if (statusDescription.getStatus().equals(status)) {
                return statusDescription.getStatusDescription();
            }
        }
        return null;
    }
}
