/*
 * To change this license header, choose License Headers in Project Properties. To change this
 * template file, choose Tools | Templates and open the template in the editor.
 */
package com.dgtt.core.repository;

import com.dgtt.core.entity.User;
import java.util.List;
import java.util.Optional;
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
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(
            value = "SELECT new User( u.userName,u.phoneNumber,u.email ) FROM User u WHERE LOWER(u.userName)=:userName and u.status in (:listStatus) and u.isDelete=:isDelete")
    List<User> checkAlreadyUsernameExsit(String userName, List<Long> listStatus, Long isDelete);

    @Query(
            value = "SELECT new User( u.userName,u.phoneNumber,u.email ) FROM User u WHERE LOWER(u.email)=:email and u.status in (:listStatus) and u.isDelete=:isDelete")
    List<User> checkAlreadyEmailExsit(String email, List<Long> listStatus, Long isDelete);

    @Query(
            value = "SELECT new User( u.userName,u.phoneNumber,u.email ) FROM User u WHERE LOWER(u.phoneNumber)=:phoneNumber and u.status in (:listStatus) and u.isDelete=:isDelete")
    List<User> checkAlreadyPhoneExsit(String phoneNumber, List<Long> listStatus, Long isDelete);

    @Query(
            value = "SELECT u FROM User u WHERE LOWER(u.userName) =:userName and u.status in (:listStatus) and u.isDelete=:isDelete")
    User findByUserName(String userName, List<Long> listStatus, Long isDelete);

    @Override
    Optional<User> findById(Long userId);

    User findByUserIdAndIsDelete(Long userId, Long isDelete);

    @Query(value = "SELECT u FROM User u WHERE u.userId=:userId and u.status in (:listStatus) and u.isDelete=:isDelete")
    User findByIdCustom(Long userId, List<Long> listStatus, Long isDelete);

    @Query(value = "SELECT u  FROM User u WHERE u.status in (:listStatus) and u.isDelete=:isDelete "
            + "and (:keyword is null or (LOWER(u.userName) like :keyword or LOWER(u.fullName) like :keyword "
            + "or LOWER(u.phoneNumber) like :keyword "
            + "or LOWER(u.email) like :keyword) ) "
            //            + "AND (:createdDateFrom IS NULL OR  ( DATE_FORMAT(u.createdDate,'%Y-%m-%d') >= DATE_FORMAT(:createdDateFrom,'%Y-%m-%d'))) "
            //            + "AND (:createdDateTo IS NULL OR  ( DATE_FORMAT(u.createdDate,'%Y-%m-%d') <= DATE_FORMAT(:createdDateTo,'%Y-%m-%d'))) "
            + "and (:userName is null or (LOWER(u.userName) like :userName)) "
            + "and (:fullName is null or (LOWER(u.fullName) like :fullName)) "
            + "and (:phoneNumber is null or (LOWER(u.phoneNumber) like :fullName)) "
            + "and (:email is null or (LOWER(u.email) like :email)) ")
    Page<User> searchAllUser(Long isDelete, List<Long> listStatus, String keyword, String userName, String fullName, String phoneNumber, String email, Pageable pageable);
//    Page<User> searchAllUser(List<Integer> listStatus, String keyword, Date createdDateFrom,
//            Date createdDateTo, String userName, String fullName, String phoneNumber, String email);
}
