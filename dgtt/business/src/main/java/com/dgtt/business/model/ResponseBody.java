package com.dgtt.business.model;

import com.dgtt.core.utils.StringUtils;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 *
 * @author DELL
 * @param <T>
 */
@Builder
@Getter
@Setter
@Data
@ApiModel(description = "JSon Object DTO cho data common")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class ResponseBody<T> {

    private T body;
    private String message;

    private String path;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = StringUtils.DF_YYYY_MM_DD_HH_MM, timezone = StringUtils.TIME_ZONE)
    private Date time;
    private int status;

    public ResponseBody(T body, String message, String path, Date time, int status) {
        this.body = body;
        this.message = message;
        this.path = path;
        this.time = new Date();
        this.status = status <= 0 ? 200 : status;
    }

    public ResponseBody() {
        super();
        this.time = new Date();
    }
}
