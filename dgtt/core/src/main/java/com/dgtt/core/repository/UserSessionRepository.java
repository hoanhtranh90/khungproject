/**
 * Welcome developer friend. PC ospAdfilex-smartTeleSale-data UserSessionRepository.java 1:00:24 PM
 */
package com.dgtt.core.repository;

import com.dgtt.core.entity.UserSession;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserSessionRepository extends JpaRepository<UserSession, Long> {

  /**
  * 
  * @param userId
  * @return
  */
  UserSession findAllByUserUserId(Long userId);

  /**
   * 
   * @param userId
   */
  void deleteByUserUserId(Long userId);
}
