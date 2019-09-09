package com.fy.service.statistics.model.increase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台盈利
 */
@Data
public class PlatformIncreaseDayModel {

    @ApiModelProperty("日期")
    private Date createDate;


    @ApiModelProperty("会员调减金额(流入金额)")
    private BigDecimal reduceMemberMoneyIn;

    @ApiModelProperty("会员调增金额(流出金额)")
    private BigDecimal increaseMemberMoneyOut;

    @ApiModelProperty("会员调账")
    private BigDecimal reduceIncreaseProfit;

    @ApiModelProperty("会员调账合计")
    private BigDecimal reduceIncreaseMemberTotal;

    @ApiModelProperty("内部号调减金额(不计算在平台盈利)")
    private BigDecimal reduceInMoney;

    @ApiModelProperty("内部号调增金额(不计算在平台盈利)")
    private BigDecimal increaseInMoney;

    @ApiModelProperty("内部号调账合计")
    private BigDecimal reduceIncreaseInTotal;

    @ApiModelProperty("系统调账合计")
    private BigDecimal reduceIncreaseTotal;

}
