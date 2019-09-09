package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class RechargeDayModel {
    @ApiModelProperty("日期")
    private Date createDate;

    @ApiModelProperty("充值金额")
    private BigDecimal rechargeMoney;

    @ApiModelProperty("银行充值金额")
    private BigDecimal rechargeMoneyBank;

    @ApiModelProperty("第三方充值金额")
    private BigDecimal rechargeMoneyThird;

    @ApiModelProperty("人工充值金额")
    private BigDecimal rechargeMoneyMan;

}
