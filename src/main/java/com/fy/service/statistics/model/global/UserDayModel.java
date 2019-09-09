package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserDayModel {
    @ApiModelProperty("日期")
    private Date createDate;

    @ApiModelProperty("用户数")
    private Integer userCount;

}
