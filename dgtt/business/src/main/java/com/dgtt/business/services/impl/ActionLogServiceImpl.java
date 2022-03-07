/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.services.impl;

import com.dgtt.business.services.ActionLogService;
import com.dgtt.business.utils.ApplicationUtils;
import com.dgtt.core.entity.ActionLog;
import com.dgtt.core.repository.ActionLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 *
 * @author sadfsafbhsaid
 */
@Service
@Transactional
public class ActionLogServiceImpl implements ActionLogService {
    
    @Autowired
    private ActionLogRepository actionLogRepository;
    
    @Override
    public void addLog(String data, Long action, Long type, Long objectId) {
        Map<String,String> map = ApplicationUtils.getCurrentMACAddress();
        ActionLog actionLog = new ActionLog();
        actionLog.setAction(action);
        actionLog.setObjectId(objectId);
        actionLog.setType(type);
        actionLog.setIpAddress(ApplicationUtils.getIPAddress());
        actionLog.setIpMac(map.get("ipMacAddress"));
        actionLog.setUserAgent(ApplicationUtils.getUserAgent());
        actionLog.setUserId(ApplicationUtils.getCurrentUser().getUserId());
        actionLog.setRoleId(ApplicationUtils.getCurrentRole() != null ? ApplicationUtils.getCurrentRole().getRoleId() : null);
        actionLog.setDataJson(data);
        actionLogRepository.save(actionLog);
    }
}
