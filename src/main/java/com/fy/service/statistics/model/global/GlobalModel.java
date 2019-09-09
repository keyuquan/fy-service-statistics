package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class GlobalModel implements Serializable {
    @ApiModelProperty("会员统计")
    private UserModel memberModel;

    @ApiModelProperty("充值提现统计")
    private RechargeCashGModel rechargeCashModel;

    @ApiModelProperty("平台盈亏")
    private PlatformProfitModel platformProfitModel;

    @ApiModelProperty("新增用户列表")
    private UserNewModel userNewModel;

    @ApiModelProperty("活跃用户列表")
    private UserActiveModel userActiveModel;

    @ApiModelProperty("支付列表")
    private RechargeModel rechargeModel;

    @ApiModelProperty("盈亏列表")
    private ProfitModel profitModel;
}
