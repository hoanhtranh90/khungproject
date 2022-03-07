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
public enum TimeInterfEnum {
    KHONG_LIEN_TUC(1L, "Không liên tục"), LIEN_TUC(2L, "Liên tục"), THEO_GIO(3L, "Theo giờ");
    private final Long timeInterf;
    private final String timeInterfDescription;

    private TimeInterfEnum(Long timeInterf, String timeInterfDescription) {
        this.timeInterf = timeInterf;
        this.timeInterfDescription = timeInterfDescription;
    }

    public Long getTimeInterf() {
        return timeInterf;
    }

    public String getTimeInterfDescription() {
        return timeInterfDescription;
    }
    
    public static String getTimeInterfDescription(Long timeInterf) {
        for (TimeInterfEnum statusDescription : TimeInterfEnum.values()) {
            if (statusDescription.getTimeInterf().equals(timeInterf)) {
                return statusDescription.getTimeInterfDescription();
            }
        }
        return null;
    }

}
