package com.fy.service.statistics.model.recharge;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值提现
 */

@Data
public class RechargeCashModel {
    @ApiModelProperty("日期")
    private Date createDate;

    @ApiModelProperty("充值金额")
    private BigDecimal rechargeMoney;

    @ApiModelProperty("充值人数")
    private Integer rechargeUserCount;

    @ApiModelProperty("银行充值金额")
    private BigDecimal rechargeMoneyBank;

    @ApiModelProperty("银行充值人数")
    private Integer rechargeUserCountBank;

    @ApiModelProperty("第三方充值金额")
    private BigDecimal rechargeMoneyThird;

    @ApiModelProperty("第三方充值人数")
    private Integer rechargeUserCountThird;

    @ApiModelProperty("人工充值金额")
    private BigDecimal rechargeMoneyMan;

    @ApiModelProperty("人工充值人数")
    private Integer rechargeUserCountMan;

    @ApiModelProperty("首充金额")
    private BigDecimal firstRechargeMoney;

    @ApiModelProperty("首充次数")
    private Integer firstRechargeCount;

    @ApiModelProperty("二充金额")
    private BigDecimal secondRechargeMoney;

    @ApiModelProperty("二充次数")
    private Integer secondRechargeCount;

    @ApiModelProperty("提现金额")
    private BigDecimal cashMoney;

    @ApiModelProperty("提现人数")
    private Integer cashCount;

}
