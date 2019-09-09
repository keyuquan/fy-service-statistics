package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class RechargeCashGModel implements Serializable {

    @ApiModelProperty("充值金额")
    private BigDecimal rechargeMoney;

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

}
