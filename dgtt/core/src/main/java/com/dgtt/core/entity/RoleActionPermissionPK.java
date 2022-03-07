package com.dgtt.core.entity;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sadfsafbhsaid
 */
@Embeddable
public class RoleActionPermissionPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "action_id")
    private long actionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "permission_id")
    private long permissionId;
    @Basic(optional = false)
    @NotNull
    @Column(name = "role_id")
    private long roleId;

    public RoleActionPermissionPK() {
    }

    public RoleActionPermissionPK(long actionId, long permissionId, long roleId) {
        this.actionId = actionId;
        this.permissionId = permissionId;
        this.roleId = roleId;
    }

    public long getActionId() {
        return actionId;
    }

    public void setActionId(long actionId) {
        this.actionId = actionId;
    }

    public long getPermissionId() {
        return permissionId;
    }

    public void setPermissionId(long permissionId) {
        this.permissionId = permissionId;
    }

    public long getRoleId() {
        return roleId;
    }

    public void setRoleId(long roleId) {
        this.roleId = roleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) actionId;
        hash += (int) permissionId;
        hash += (int) roleId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof RoleActionPermissionPK)) {
            return false;
        }
        RoleActionPermissionPK other = (RoleActionPermissionPK) object;
        if (this.actionId != other.actionId) {
            return false;
        }
        if (this.permissionId != other.permissionId) {
            return false;
        }
        if (this.roleId != other.roleId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.RoleActionPermissionPK[ actionId=" + actionId + ", permissionId=" + permissionId + ", roleId=" + roleId + " ]";
    }

}
