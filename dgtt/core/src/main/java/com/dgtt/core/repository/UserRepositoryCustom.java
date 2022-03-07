/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.dgtt.core.repository;

import com.dgtt.core.entity.User;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.sun.istack.NotNull;

/**
 * 
 * @author DELL
 */
public interface UserRepositoryCustom {

    Page<User> searchAllUserByRoleCodeAndCreateBy(List<String> usernameList, Integer status,
            Date createdDateFrom, Date createdDateTo, String roleCode, @NotNull Long createByUserId,
            Pageable pageable);

    int editUserFree(User user);
    
    String getIsdn(String q);

}
