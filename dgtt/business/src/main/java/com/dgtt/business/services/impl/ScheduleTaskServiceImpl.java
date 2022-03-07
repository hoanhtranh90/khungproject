/**
 * Welcome developer friend. PC ospAdfilex-smartTeleSale-service ScheduleTaskServiceImpl.java
 * 4:17:28 PM
 */
package com.dgtt.business.services.impl;

import com.dgtt.business.services.ScheduleTaskService;
import com.dgtt.core.config.ApplicationConfig.MessageSourceVi;
import com.dgtt.core.repository.LoggerRepository;
import com.dgtt.core.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 
 * @author DELL
 */
@Component
@EnableAsync
@Slf4j
@Transactional
public class ScheduleTaskServiceImpl implements ScheduleTaskService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MessageSourceVi messageSourceVi;

    @Autowired
    private LoggerRepository callLogRepository;

    @Scheduled(cron = "${auto_run_service}")
    @Transactional
    @Override
    public void autoRunAt1stMonth() {
        log.info(" RUN THE SERVICE ON THE FIRST DAY OF THE MONTH ");
        callLogRepository.initTable();
    }


}
