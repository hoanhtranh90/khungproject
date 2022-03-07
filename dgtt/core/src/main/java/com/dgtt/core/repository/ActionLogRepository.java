/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.core.repository;

import com.dgtt.core.entity.ActionLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
/**
 *
 * @author sadfsafbhsaid
 */
@Repository
public interface ActionLogRepository extends JpaRepository<ActionLog, Long>{
    
}
