/**
 * Welcome developer friend. LuongTN ospAdfilex-smartTeleSale-service CallLogServiceImpl.java
 * 5:41:17 PM
 */
package com.dgtt.business.services.impl;

import com.dgtt.business.services.LoggerService;
import com.dgtt.core.model.LoggerRegister;
import com.dgtt.core.model.LoggerSearch;
import com.dgtt.core.repository.LoggerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 *
 * @author Nguyen_Toan
 */
@Service
@Primary
@Qualifier("LoggerServiceImpl_Main")
@Transactional
public class LoggerServiceImpl implements LoggerService {

  @Autowired
  private LoggerRepository callLogRepository;

  @Override
  public void intTable() {
    callLogRepository.initTable();
  }

  @Override
  public void createAll(List<LoggerRegister> callLoggers) {
    callLogRepository.createAll(callLoggers);
  }

  @Override
  public int deleteAll(List<Long> callLoggers) {
    return callLogRepository.deleteAll(callLoggers);
  }

  @Override
  public Page<LoggerSearch> searchCallLogger(int page, int size, String sortByProperties,
      String sortBy, LoggerSearch callLogger) {

    return callLogRepository.searchLogger(callLogger.getCodeId(), callLogger.getCodeName(),
        callLogger.getText(), callLogger.getCreatedDateFrom(), callLogger.getCreatedDateTo(), page,
        size, sortByProperties, sortBy);
  }

}
