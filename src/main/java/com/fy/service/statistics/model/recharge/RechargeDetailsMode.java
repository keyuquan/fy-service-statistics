package com.fy.service.statistics.model.recharge;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 充值明细
 */
@Data
public class RechargeDetailsMode {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("邀请码")
    private String inviteCode;

    @ApiModelProperty("首充")
    private BigDecimal firstRechargeMoney;

    @ApiModelProperty("二充金额")
    private BigDecimal secondRechargeMoney;
}
