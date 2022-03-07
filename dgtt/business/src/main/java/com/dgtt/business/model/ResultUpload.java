/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dgtt.business.model;

import io.swagger.annotations.ApiModel;
import lombok.*;

/**
 *
 * @author Nguyen_Toan
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ApiModel(description = "JSon Object cho upload file. ")
public class ResultUpload {

    private String name;
    private String path;
}
