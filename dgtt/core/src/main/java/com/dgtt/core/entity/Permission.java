package com.dgtt.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
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
@Table(name = "permission", indexes = {
        @Index(name = "index_permission", columnList = "permission,status,is_delete")})
@Setter
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Permission extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "permission_id")
    private Long permissionId;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "order_")
    private Long order;

    @Column(name = "is_delete", columnDefinition = "bigint default 0")
    private Long isDelete;

    @Column(name = "status", columnDefinition = "bigint default 1")
    private Long status;

    @Column(name = "permission", length = 150)
    private String permission;

    @Column(name = "name", length = 150)
    private String name;

    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "permission")
    private List<RoleActionPermission> roleActionPermissionList;

}
