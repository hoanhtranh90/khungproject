/**
 * Welcome developer friend. LuongTN ospAdfilex-smartTeleSale-data CallLogFactory.java 2:01:46 PM
 */
package com.dgtt.core.repository;

import com.dgtt.core.model.LoggerRegister;
import com.dgtt.core.model.LoggerSearch;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Page;


/**
 *
 * @author Nguyen_Toan
 */
public interface LoggerRepository {

  void initTable();

  void createAll(List<LoggerRegister> callLoggers);

  int deleteAll(List<Long> callLoggers);

  Page<LoggerSearch> searchLogger(Long codeId, String codeName, String text,
      Date createdDateFrom, Date createdDateTo, int page, int size, String sortByProperties,
      String sortBy);

}
