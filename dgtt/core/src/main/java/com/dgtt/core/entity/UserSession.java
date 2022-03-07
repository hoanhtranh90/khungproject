/**
 * Welcome developer friend. PC ospAdfilex-smartTeleSale-data UserSession.java 11:09:31 AM
 */
package com.dgtt.core.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Table(name = "user_session",
    uniqueConstraints = {@UniqueConstraint(columnNames = {"user_id", "session"})})
@Setter
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor
public class UserSession extends Auditable<String> implements Serializable {

  private static final long serialVersionUID = -6998632842657040223L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "session_id")
  private Long sessionId;

  @Column(name = "session")
  private String session;

  @OneToOne
  @JoinColumn(name = "user_id", nullable = true)
  private User user;

}
