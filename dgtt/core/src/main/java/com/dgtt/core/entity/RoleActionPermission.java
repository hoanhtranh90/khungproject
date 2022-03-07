package com.dgtt.core.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author sadfsafbhsaid
 */
@Entity
@Table(name = "role_action_permission")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class RoleActionPermission extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected RoleActionPermissionPK roleActionPermissionPK;

    @Column(name = "status", columnDefinition = "bigint default 1")
    private Long status;

    @Column(name = "is_delete", columnDefinition = "bigint default 0")
    private Long isDelete;

    @JsonIgnore
    @JoinColumn(name = "action_id", referencedColumnName = "action_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Action action;

    @JsonIgnore
    @JoinColumn(name = "role_id", referencedColumnName = "role_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Role ospRole;

    @JsonIgnore
    @JoinColumn(name = "permission_id", referencedColumnName = "permission_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Permission permission;
}
