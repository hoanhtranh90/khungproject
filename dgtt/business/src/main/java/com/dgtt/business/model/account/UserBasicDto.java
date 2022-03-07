package com.dgtt.business.model.account;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.util.Date;
import java.util.List;

@JsonInclude(value = Include.NON_NULL)
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(description = "JSon Object Basic DTO cho User")
public class UserBasicDto {

    private Long userId;

    private String phoneNumber;

    private String email;

    private String userName;
    
    private String fullName;

    private List<String> roleCodes;

    private Integer sex;

    private String avatar;

    private Date dateOfBirth;

    private String address;

    private Long status;
    
    private String statusStr;

    private String createdBy;

    private String modifiedBy;

    private Date createdDate;

    private Date modifiedDate;

}
