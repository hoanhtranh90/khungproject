/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.dgtt.core.repository;

import com.dgtt.core.entity.Role;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author DELL
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query(value = "SELECT new Role(r.roleId) FROM Role r Where r.roleCode=:roleCode")
    Role findByRoleCode(String roleCode);

//    @Query(value = "SELECT new Role(r.roleId,r.description,r.roleCode,r.roleName,r.status) FROM Role r Where r.roleId=:roleId and r.isDelete=:isDelete")
    Role findByRoleIdAndIsDelete(Long roleId, Long isDelete);

    List<Role> findByRoleNameIgnoreCaseAndIsDeleteAndStatusIn(String roleName, Long isDelete, List<Long> listStatus);

    @Query(value = "SELECT new Role(r.roleId,r.description,r.roleCode,r.roleName,r.status,r.createdDate,r.modifiedDate,r.createdBy,r.modifiedBy) FROM Role r Where r.isDelete=:isDelete "
            + "and (:status is null or r.status=:status) and (:roleName is null or r.roleName like :roleName) ")
    Page<Role> searchAdvance(String roleName, Long isDelete, Long status, Pageable pageable);

    @Query(value = "SELECT new Role(r.roleId,r.description,r.roleCode,r.roleName,r.status,r.createdDate,r.modifiedDate,r.createdBy,r.modifiedBy) FROM Role r Where r.isDelete=:isDelete "
            + "and (:status is null or r.status=:status) and (:keyword is null or r.roleName like :keyword) ")
    Page<Role> search(String keyword, Long isDelete, Long status, Pageable pageable);

}
