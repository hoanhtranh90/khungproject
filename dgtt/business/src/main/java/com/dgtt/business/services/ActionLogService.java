/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.services;
/**
 * 
 * @author sadfsafbhsaid
 */
public interface ActionLogService {

    void addLog(String data,Long action, Long type, Long objectId);
}
