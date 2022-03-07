package com.dgtt.core.entity;

public interface Deletable extends Updatable {

    public Long getIsDelete();

    public void setIsDelete(Long isDelete);
}
