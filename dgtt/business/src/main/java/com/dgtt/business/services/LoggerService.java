package com.dgtt.business.services;
/**
 * Welcome developer friend. LuongTN ospAdfilex-smartTeleSale-service CallLogService.java 5:40:43 PM
 */

import com.dgtt.core.model.LoggerRegister;
import com.dgtt.core.model.LoggerSearch;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 *
 * @author Nguyen_Toan
 */
public interface LoggerService {

  void intTable();

  void createAll(List<LoggerRegister> callLoggers);

  int deleteAll(List<Long> callLoggers);

  Page<LoggerSearch> searchCallLogger(int page, int size, String sortByProperties, String sortBy,
      LoggerSearch callLogger);
}
