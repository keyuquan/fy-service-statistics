package com.fy.service.statistics.model.user;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserCountModel {

    @ApiModelProperty("日期")
    private Date createDate;

    @ApiModelProperty("新增用户")
    private Integer userCount;

    @ApiModelProperty("活跃用户数")
    private Integer activeUserCount;

    @ApiModelProperty("用户总数")
    private Integer userCountAll;

    @ApiModelProperty("会员盈利")
    private BigDecimal userProfit;

    @ApiModelProperty("期初余额")
    private BigDecimal initialBalance;

    @ApiModelProperty("期末余额")
    private BigDecimal finalBalance;
}
