package com.dgtt.core.constants;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author DELL
 */
public enum StatusEnum {

    ACTIVE(1L, "Actived"), IN_ACTIVE(2L, "InActive"), LOCK(3L, "Locked");

    private final Long status;
    private final String statusDescription;

    private StatusEnum(Long status, String statusDescription) {
        this.status = status;
        this.statusDescription = statusDescription;
    }

    public Long getStatus() {
        return status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }
    
    public static List getStatusIsDelete(){
        return Arrays.asList(ACTIVE.getStatus(),IN_ACTIVE.getStatus());
    }

    public static String getStatusDescription(Long status) {
        for (StatusEnum statusDescription : StatusEnum.values()) {
            if (statusDescription.getStatus().equals(status)) {
                return statusDescription.getStatusDescription();
            }
        }
        return null;
    }
}
