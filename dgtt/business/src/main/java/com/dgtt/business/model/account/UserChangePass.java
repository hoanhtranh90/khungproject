package com.dgtt.business.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * 
 * vn.osp.adfilex.model.account 
 * @author LuongTN : 9:12:00 AM
 * 
 * 
 * UserChangePass.java
 */
// @JsonInclude(value = Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "JSon Object Basic DTO cho User đổi mật khẩu")
public class UserChangePass {

  @NotNull
  @JsonProperty(value = "user_id")
  private Long userId;

  @JsonProperty(value = "username")
  @NotNull
  @NotBlank
  private String username;

  @JsonProperty(value = "password_new")
  @NotNull
  @NotBlank
  private String passwordNew;

  @JsonProperty(value = "password_current")
  private String passwordCurrent;

}

