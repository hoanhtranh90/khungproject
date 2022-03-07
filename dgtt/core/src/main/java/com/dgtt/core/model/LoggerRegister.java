/**
 * Welcome developer friend. LuongTN ospAdfilex-smartTeleSale-data CallLog.java 2:04:15 PM
 */
package com.dgtt.core.model;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class LoggerRegister {

  @JsonProperty(value = "code_id")
  private Long codeId;

  @JsonProperty(value = "code_name")
  @NotEmpty(message = "Không bỏ trống code_name")
  @NotNull(message = "Không bỏ trống code_name")
  private String codeName;

  @JsonProperty(value = "text")
  @NotEmpty(message = "Không bỏ trống text")
  @NotNull(message = "Không bỏ trống text")
  private String text;


}
