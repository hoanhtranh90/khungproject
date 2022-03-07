package com.dgtt.core.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import java.io.Serializable;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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
@Table(name = "user_role")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class UserRole extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = -793769159059711100L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_role_id")
    private Long userRoleId;

    @JsonIgnore
//    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

//    @JsonIgnore
//    @JsonBackReference
    @JsonManagedReference
    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @Column(name = "status", columnDefinition = "bigint default 1")
    private Long status;

    @Column(name = "is_delete", columnDefinition = "bigint default 0")
    private Long isDelete;

    public UserRole(Long userRoleId) {
        this.userRoleId = userRoleId;
    }

}
