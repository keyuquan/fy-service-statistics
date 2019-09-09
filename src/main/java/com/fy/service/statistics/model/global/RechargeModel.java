package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class RechargeModel {

    @ApiModelProperty("充值金额集合")
    private List<RechargeDayModel> rechargeDayModels;

    @ApiModelProperty("支付金额")
    private BigDecimal rechargeMoney;

    @ApiModelProperty("银行充值金额")
    private BigDecimal rechargeMoneyBank;

    @ApiModelProperty("第三方充值金额")
    private BigDecimal rechargeMoneyThird;

    @ApiModelProperty("人工充值金额")
    private BigDecimal rechargeMoneyMan;


}
