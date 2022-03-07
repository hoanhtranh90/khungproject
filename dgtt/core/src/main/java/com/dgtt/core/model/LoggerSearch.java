/**
 * Welcome developer friend. LuongTN ospAdfilex-smartTeleSale-data CallLog.java 2:04:15 PM
 */
package com.dgtt.core.model;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * @author DELL
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@JsonInclude(value = Include.NON_NULL)
public class LoggerSearch {

  @JsonProperty(value = "call_log_id")
  private Long callLogId;

  @JsonProperty(value = "code_id")
  private Long codeId;

  @JsonProperty(value = "code_name")
  private String codeName;

  @JsonProperty(value = "text")
  private String text;

  @JsonProperty(value = "created_date_from")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdDateFrom;
  
  @JsonProperty(value = "created_date_to")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
  private Date createdDateTo;

  @JsonProperty(value = "created_date")
  private String createdDate;

}
