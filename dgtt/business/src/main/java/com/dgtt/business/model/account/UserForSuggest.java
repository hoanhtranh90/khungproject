package com.dgtt.business.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 * 
 * vn.osp.adfilex.model.account 
 * @author LuongTN : 9:12:03 AM
 * 
 * 
 * UserForSuggest.java
 */
// @JsonInclude(value = Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "JSon Object Basic DTO cho gợi ý và tìm kiếm User ")
public class UserForSuggest {

  @JsonProperty(value = "user_id")
  private Long userId;

  @JsonProperty(value = "username")
  private String username;

}
