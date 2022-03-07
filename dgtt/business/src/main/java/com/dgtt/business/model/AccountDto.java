/**
 * Welcome developer friend. PC ospAdfilex-smartTeleSale-service DataAccountExport.java 5:14:11 PM
 */
package com.dgtt.business.model;

import com.dgtt.core.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;


@JsonInclude(value = Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "JSon Object Basic DTO cho data User để xuất ra file excell")
@EqualsAndHashCode(callSuper = false, exclude = {"stt", "status", "statusText", "roleCode",
    "voiceResourcesAvaiable", "createdDate", "expiredResourcesDate", "username"})
public class AccountDto implements Comparable<AccountDto> {

  @JsonProperty(value = "stt")
  private String stt;

  @JsonProperty(value = "user_id")
  private Long userId;

  @JsonProperty(value = "username")
  private String username;

  @JsonProperty(value = "status")
  private Integer status;

  @JsonProperty(value = "status_text")
  private String statusText;

  @JsonProperty(value = "role_code")
  private String roleCode;

  @JsonProperty(value = "voice_resources_avaiable")
  private Double voiceResourcesAvaiable;

  @JsonProperty(value = "created_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringUtils.DF_YYYY_MM_DD_HH_MM_SS)
  protected Date createdDate;

  @JsonProperty(value = "expired_resources_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringUtils.DF_YYYY_MM_DD_HH_MM_SS)
  private Date expiredResourcesDate;

  @Override
  public int compareTo(AccountDto o) {
    return this.stt.compareTo(o.getStt());
  }

}
