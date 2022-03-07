package com.dgtt.business.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;
import java.util.List;

/**
 * 
 * @author DELL
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ApiModel(description = "JSon Object Basic DTO cho authorization")
public class JwtAuthDto {

  private String issuer;
  private Long ui;
  private String uname;
  private String role;
  private String email;
  private Date expi;
  private String usAgent;
  private String ip;
  private String ss;
  private String fullname;
  private Double voiceResource;
  private Date expireResource;
  private Integer smsResource;
  private List<Long> hobby;
  private List<Long> uiSubs;

}
