/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.utils;

import com.dgtt.core.entity.*;
import com.dgtt.core.model.UserTokenInfo;
import com.dgtt.core.utils.Constants;
import com.dgtt.core.utils.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 *
 * @author sadfsafbhsaid
 */
@Service
public class UtilsService {

    public <DomainType extends Creatable, IDFieldType extends Serializable> void save(
            JpaRepository<DomainType, IDFieldType> repository, List<DomainType> models) {
        UserTokenInfo token = ApplicationUtils.getUserTokenInfo();
        User user = token.getUser();
        Role role = token.getRole();
        StringUtils.each(models, (index, model) -> populateForSave(model, user, role));
        repository.saveAll(models);
    }

    public <DomainType extends Creatable, IDFieldType extends Serializable> void save(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {

        UserTokenInfo token = ApplicationUtils.getUserTokenInfo();
        User user = token.getUser();
        Role role = token.getRole();
        populateForSave(model, user, role);
        repository.save(model);
    }

    public <DomainType extends Updatable, IDFieldType extends Serializable> void update(
            JpaRepository<DomainType, IDFieldType> repository, List<DomainType> models) {
        UserTokenInfo token = ApplicationUtils.getUserTokenInfo();
        User user = token.getUser();
        Role role = token.getRole();

        StringUtils.each(models, (index, model) -> populateForUpdate(model, user, role));
        repository.saveAll(models);
    }

    public <DomainType extends Updatable, IDFieldType extends Serializable> void update(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {

        UserTokenInfo token = ApplicationUtils.getUserTokenInfo();
        User user = token.getUser();
        Role role = token.getRole();
        populateForUpdate(model, user, role);
        repository.save(model);
    }

    public <DomainType extends Deletable, IDFieldType extends Serializable> void delete(
            JpaRepository<DomainType, IDFieldType> repository, DomainType model) {
        UserTokenInfo token = ApplicationUtils.getUserTokenInfo();
        User user = token.getUser();
        Role role = token.getRole();
        model.setIsDelete(Constants.DELETE.DELETED);
        populateForUpdate(model, user, role);
        repository.save(model);
    }

    public <DomainType extends Deletable, IDFieldType extends Serializable> void delete(
            JpaRepository<DomainType, IDFieldType> repository, List<DomainType> models) {
        if (!StringUtils.isTrue(models)) {
            return;
        }
        UserTokenInfo token = ApplicationUtils.getUserTokenInfo();
        User user = token.getUser();
        Role role = token.getRole();
        StringUtils.each(models, (index, model) -> {
            model.setIsDelete(Constants.DELETE.DELETED);
            populateForUpdate(model, user, role);
        });
        repository.saveAll(models);
    }

    private void populateForSave(Creatable model, User user, Role role) {
        if (model instanceof Deletable) {
            ((Deletable) model).setIsDelete(Constants.DELETE.NORMAL);
        }
        model.setCreatorId(user.getUserId());
        model.setCreateRoleId(role != null ? role.getRoleId() : null);

        this.populateForUpdate(model, user, role);

    }

    private void populateForUpdate(Updatable model, User user, Role role) {
        model.setUpdatorId(user.getUserId());
        model.setUpdateRoleId(role != null ? role.getRoleId() : null);
    }
}
