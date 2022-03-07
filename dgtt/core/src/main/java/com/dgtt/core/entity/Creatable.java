package com.dgtt.core.entity;

public interface Creatable extends Updatable {

    public Long getCreatorId();

    public void setCreatorId(Long creatorId);

    public Long getCreateDeptId();

    public void setCreateDeptId(Long createDeptId);

    public Long getCreateRoleId();

    public void setCreateRoleId(Long createRoleId);

}
