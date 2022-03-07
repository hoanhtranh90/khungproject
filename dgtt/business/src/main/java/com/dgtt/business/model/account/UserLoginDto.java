package com.dgtt.business.model.account;

import com.dgtt.core.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * @author DELL
 */
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "JSon Object Basic DTO cho User Login")
public class UserLoginDto {

  @JsonProperty(value = "uname",defaultValue = "admin")
  @NotNull
  @NotBlank
  private String uname;

  @JsonProperty(value = "password",defaultValue = "admin")
  @NotNull
  @NotBlank
  private String pwd;
  @JsonIgnore
  private String ip;
  @JsonIgnore
  private String userAgent;

    public void setUname(String uname) {
        this.uname = StringUtils.stringLowerCaseNotTrim(uname);
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }


}

