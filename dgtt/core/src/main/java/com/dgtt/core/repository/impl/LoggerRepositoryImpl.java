/**
 * Welcome developer friend. LuongTN ospAdfilex-smartTeleSale-data CallLogFactory.java 2:01:10 PM
 */
package com.dgtt.core.repository.impl;

import com.dgtt.core.model.LoggerRegister;
import com.dgtt.core.model.LoggerSearch;
import com.dgtt.core.repository.LoggerRepository;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 *
 * @author Nguyen_Toan
 */
@Repository
public class LoggerRepositoryImpl implements LoggerRepository {

  private static final String LEFT_PARENTHESES = " ( ";

  private static final String RIGHT_PARENTHESES = " ) ";

  private static final String COMMA = " , ";

  @Autowired
  private EntityManager emManager;

  private String tableName;

  @Override
  @Transactional
  public void initTable() {
    final Date nowDate = new Date();
    String dateNow = new SimpleDateFormat("yyyyMM").format(nowDate);
    this.tableName = new StringBuilder("dgtt_log_").append(dateNow).toString();

    StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS " + this.tableName
        + " ( logger_id BIGINT(20) NOT NULL AUTO_INCREMENT ,"
        + " code_id  SMALLINT(20) NOT NULL DEFAULT 0,"
        + " code_name VARCHAR(5) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL  DEFAULT '',"
        + " text VARCHAR(1024) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL  DEFAULT '',"
        + " created_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,"
        + " PRIMARY KEY (`logger_id`)"
        + ") ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;");

    Query query = emManager.createNativeQuery(sql.toString());
    query.executeUpdate();


  }

  @Override
  @Transactional
  public void createAll(List<LoggerRegister> loggerRegisters) {

    for (int i = 0; i < loggerRegisters.size(); i++) {

      LoggerRegister callLogger = loggerRegisters.get(i);

      StringBuilder sql = new StringBuilder(
          "INSERT INTO " + this.tableName + "  ( code_id, code_name , text )  VALUES ");
      sql.append(LEFT_PARENTHESES).append(" :codeId ").append(" , :codeName ").append(" , :text ")
          .append(RIGHT_PARENTHESES);

      Query query = emManager.createNativeQuery(sql.toString());

      query.setParameter("codeId", callLogger.getCodeId());
      query.setParameter("codeName",
          callLogger.getCodeName().length() <= 5 ? callLogger.getCodeName()
              : callLogger.getCodeName().substring(0, 5));
      query.setParameter("text", callLogger.getText().length() <= 1024 ? callLogger.getText()
          : callLogger.getText().substring(0, 1024));

      query.executeUpdate();
    }

  }

  @Override
  @Transactional
  public int deleteAll(List<Long> loggerIds) {

    if (!loggerIds.isEmpty()) {

      StringBuilder sql = new StringBuilder(
          "DELETE FROM  " + this.tableName + "  WHERE logger_id  IN ( :loggersId ) ; ");

      Query query = emManager.createNativeQuery(sql.toString());
      return query.setParameter("loggersId", loggerIds).executeUpdate();
    }
    return 0;

  }

  @SuppressWarnings("unchecked")
  @Override
  public Page<LoggerSearch> searchLogger(Long codeId, String codeName, final String text,
      Date createdDateFrom, Date createdDateTo, int page, int size, String sortByProperties,
      String sortBy) {

    codeName = ObjectUtils.isEmpty(codeName) ? StringUtils.EMPTY : codeName;

    StringBuilder sql = new StringBuilder("SELECT c.logger_id, c.code_id , c.code_name,"
        + "c.text  , DATE_FORMAT(c.created_date,'%d-%l-%Y %H:%i:%s' ) as created_date  FROM  "
        + this.tableName + " c WHERE c.code_name LIKE CONCAT('%',:codeName,'%') "
        + " AND (:text IS NULL OR c.text  LIKE CONCAT('%',:text,'%')  ) "
        + " AND (:codeId IS NULL OR c.code_id = :codeId ) "
        + " AND (:createdDateFrom IS NULL OR DATE_FORMAT(c.created_date,'%Y-%m-%d' ) >= DATE_FORMAT(:createdDateFrom,'%Y-%m-%d' )  ) "
        + " AND (:createdDateTo IS NULL OR DATE_FORMAT(c.created_date,'%Y-%m-%d' ) <= DATE_FORMAT(:createdDateTo,'%Y-%m-%d' )  ) ");

    appendOrderAndSort(sortByProperties, sortBy, sql);

    Query query = emManager.createNativeQuery(sql.toString());
    query.setParameter("codeName", codeName);
    query.setParameter("codeId", codeId);
    query.setParameter("text", text);
    query.setParameter("createdDateFrom", createdDateFrom);
    query.setParameter("createdDateTo", createdDateTo);

    int totalRows = query.getResultList().size();

    query.setFirstResult(page * size);
    query.setMaxResults(size);

    List<Object[]> results = query.getResultList();
    List<LoggerSearch> callLoggerList = convertToLogger(results);

    Pageable pageable = getPageable(page, size, sortByProperties, sortBy);
    return new PageImpl<>(callLoggerList, pageable, totalRows);
  }

  /**
   * @param sortByProperties
   * @param sortBy
   * @param sql
   */
  private StringBuilder appendOrderAndSort(final String sortByProperties, final String sortBy,
      StringBuilder sql) {

    sql.append(" ORDER BY ");
    String[] properties = sortByProperties.split(COMMA.trim());

    int j = 0;
    for (int i = 0; i < properties.length; i++) {
      j += 1;
      sql.append(properties[i]);
      if (j != properties.length) {
        sql.append(COMMA);
      }
    }

    sql.append(" ".concat(sortBy));
    return sql;
  }

  /**
   * @param page
   * @param size
   * @param sortByProperties
   * @param sortBy
   * @return
   */
  private Pageable getPageable(int page, int size, String sortByProperties, String sortBy) {
    Sort sort = null;
    if (Direction.ASC.toString().equals(sortBy)) {
      sort = Sort.by(sortByProperties.split(COMMA.trim())).ascending();
    } else {
      sort = Sort.by(sortByProperties.split(COMMA.trim())).descending();
    }
    return PageRequest.of(page, size, sort);
  }

  /**
   * @param callLoggerList
   * @param results
   */
  private List<LoggerSearch> convertToLogger(List<Object[]> results) {
    List<LoggerSearch> loggerList = new ArrayList<>();
    results.stream().forEach(record -> {

      Long logId1 = ((BigInteger) record[0]).longValue();
      Long codeId2 = ((BigInteger) record[1]).longValue();
      String codeName3 = record[2].toString();
      String text4 = record[3].toString();
      String createdDate5 = record[4].toString();

      loggerList.add(LoggerSearch.builder().callLogId(logId1).codeId(codeId2)
          .codeName(codeName3).text(text4).createdDate(createdDate5).build());

    });

    return loggerList;
  }

}
