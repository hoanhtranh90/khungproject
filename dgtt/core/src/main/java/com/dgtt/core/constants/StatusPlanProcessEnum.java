package com.dgtt.core.constants;

/**
 *
 * @author DELL
 */
public enum StatusPlanProcessEnum {

    CHO_GUI_DUYET(0L, "Chờ gửi duyệt"), CHO_XU_LY(1L, "Chờ xử lý"),DA_XU_LY(3L, "Đã xử lý"), TU_CHOI(4L, "Từ chối");

    private final Long status;
    private final String statusDescription;

    private StatusPlanProcessEnum(Long status, String statusDescription) {
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
        for (StatusPlanProcessEnum statusDescription : StatusPlanProcessEnum.values()) {
            if (statusDescription.getStatus().equals(status)) {
                return statusDescription.getStatusDescription();
            }
        }
        return null;
    }
}
