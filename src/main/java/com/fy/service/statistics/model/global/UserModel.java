package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class UserModel implements Serializable {
    @ApiModelProperty("新增会员")
    private Integer userCount;

    @ApiModelProperty("会员总数")
    private Integer userCountAll;

    @ApiModelProperty("活跃会员数")
    private Integer activeUserCount;

    @ApiModelProperty("在线会员数")
    private String onlineUsersCount;

    @ApiModelProperty("会员盈利")
    private BigDecimal userProfit;

    @ApiModelProperty("期初余额")
    private BigDecimal initialBalance;

    @ApiModelProperty("期末余额")
    private BigDecimal finalBalance;

}
