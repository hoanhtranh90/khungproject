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
public enum ActionLogEnum {
    DANG_NHAP(1L, "Đăng nhập"), DANG_XUAT(2L, "Đăng xuất"), THEM_MOI(3L, "Thêm mới");
    private final Long action;
    private final String actionDescription;

    private ActionLogEnum(Long action, String actionDescription) {
        this.action = action;
        this.actionDescription = actionDescription;
    }

    public Long getAction() {
        return action;
    }

    public String getActionDescription() {
        return actionDescription;
    }

    public static String getActionDescription(Long action) {
        for (ActionLogEnum statusDescription : ActionLogEnum.values()) {
            if (statusDescription.getAction().equals(action)) {
                return statusDescription.getActionDescription();
            }
        }
        return null;
    }
}
