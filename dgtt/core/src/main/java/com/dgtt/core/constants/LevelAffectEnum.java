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
public enum LevelAffectEnum {
    IT_ANH_HUONG(1L, "Ít ảnh hưởng"), VUA_PHAI(2L, "Vừa phải"), RAT_NHIEU(3L, "Rất nhiều"), KHONG_HOAT_DONG(4L, "Không hoạt động được");
    private Long levelAffect;
    private String levelAffectDescription;

    private LevelAffectEnum(Long levelAffect, String levelAffectDescription) {
        this.levelAffect = levelAffect;
        this.levelAffectDescription = levelAffectDescription;
    }

    public Long getLevelAffect() {
        return levelAffect;
    }

    public String getLevelAffectDescription() {
        return levelAffectDescription;
    }

    public static String getLevelAffectDescription(Long levelAffect) {
        for (LevelAffectEnum statusDescription : LevelAffectEnum.values()) {
            if (statusDescription.getLevelAffect().equals(levelAffect)) {
                return statusDescription.getLevelAffectDescription();
            }
        }
        return null;
    }
}
