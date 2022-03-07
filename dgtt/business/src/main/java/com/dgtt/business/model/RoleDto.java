package com.dgtt.business.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Map;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(description = "JSon Object Basic DTO Role")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoleDto {

    private Long roleId;

    private Long status;

    private String statusStr;

    private String description;

    private String roleCode;

    @JsonProperty(value = "roleName")
    private String roleName;

//    List<PermissionAction> permissionActions;
    Map<String, Object> permissionAction;

}
