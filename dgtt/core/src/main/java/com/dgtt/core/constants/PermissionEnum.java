/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.dgtt.core.constants;

/**
 *
 * @author Nguyen_Toan
 */
public enum PermissionEnum {

    ADMIN("ADMIN", "Admin", 1), LANH_DAO("LANHDAO", "Lãnh đạo", 10), VAN_THU("VANTHU", "Văn thư", 20), CHUYEN_VIEN("CHUYENVIEN", "Chuyên viên", 30);

    private String roleCode;
    private String roleDescription;
    private Integer level;

    private PermissionEnum(String roleCode, String roleDescription, Integer level) {
        this.roleCode = roleCode;
        this.roleDescription = roleDescription;
        this.level = level;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public Integer getLevel() {
        return level;
    }

    public static Integer getLevelRole(String roleCode) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (roleCode.contains(permissionEnum.getRoleCode())) {
                return permissionEnum.getLevel();
            }
        }
        return null;
    }

    public static String getRoleDescription(String roleCode) {
        for (PermissionEnum permissionEnum : PermissionEnum.values()) {
            if (permissionEnum.getRoleCode().equals(roleCode)) {
                return permissionEnum.getRoleDescription();
            }
        }
        return null;
    }

}
