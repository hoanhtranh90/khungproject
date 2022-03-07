/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.model.account;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 *
 * @author Nguyen_Toan
 */
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "JSon Object lấy danh sách người dùng.")
public class UserDto {

    @JsonProperty(value = "user_id")
    private Long userId;

    @JsonProperty(value = "username")
    private String username;
}
