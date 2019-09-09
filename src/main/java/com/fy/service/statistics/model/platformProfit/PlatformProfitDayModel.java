package com.fy.service.statistics.model.platformProfit;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 平台盈利
 */
@Data
public class PlatformProfitDayModel {
    @ApiModelProperty("日期")
    private Date createDate;

    @ApiModelProperty("牛牛手续费金额")
    private BigDecimal niuFormalitiesMoneyIn;

    @ApiModelProperty("牛牛内部号获赔金额")
    private BigDecimal niuPayoutMoneyIn;

    @ApiModelProperty("牛牛流入金额(牛牛合计)")
    private BigDecimal niuMoneyIn;

    @ApiModelProperty("牛牛内部号赔付金额")
    private BigDecimal niuPayoutMoneyOut;

    @ApiModelProperty("牛牛平台盈利")
    private BigDecimal niuProfit;

    @ApiModelProperty("扫雷免死抢包金额")
    private BigDecimal leiNoDeathGrabMoneyIn;

    @ApiModelProperty("扫雷其他内部号抢包金额")
    private BigDecimal leiOtherGrabMoneyIn;

    @ApiModelProperty("扫雷内部号抢包金额")
    private BigDecimal leiGrabMoneyIn;

    @ApiModelProperty("扫雷免死内部号获赔金额")
    private BigDecimal leiNoDeathPayoutMoneyIn;

    @ApiModelProperty("扫雷其他免死内部号获赔金额")
    private BigDecimal leiOtherPayoutMoneyIn;

    @ApiModelProperty("扫雷内部号获赔金额")
    private BigDecimal leiPayoutMoneyIn;

    @ApiModelProperty("扫雷流入金额(扫雷合计)")
    private BigDecimal leiMoneyIn;

    @ApiModelProperty("扫雷免死赔付")
    private BigDecimal leiNoDeathPayoutMoneyOut;

    @ApiModelProperty("扫雷其他内部号赔付")
    private BigDecimal leiOtherPayoutMoneyOut;

    @ApiModelProperty("扫雷内部号赔付金额")
    private BigDecimal leiSumPayoutMoneyOut;

    @ApiModelProperty("扫雷赔付")
    private BigDecimal leiPayoutMoneyOut;

    @ApiModelProperty("扫雷会员抢包金额")
    private BigDecimal leiGrabMoneyOut;

    @ApiModelProperty("扫雷流出金额(合计)")
    private BigDecimal leiMoneyOut;

    @ApiModelProperty("扫雷平台盈利")
    private BigDecimal leiProfit;

    @ApiModelProperty("禁抢内部号抢包金额(流入金额)")
    private BigDecimal jinGrabMoneyIn;

    @ApiModelProperty("禁抢内部号赔付金额(流出金额)")
    private BigDecimal jinPayoutOut;

    @ApiModelProperty("禁抢平台盈利")
    private BigDecimal jinProfit;

    @ApiModelProperty("水果机会员投注金额(流入金额)")
    private BigDecimal fruitBetMoneyIn;

    @ApiModelProperty("水果机会员中奖金额(流出金额)")
    private BigDecimal fruitWinMoneyOut;

    @ApiModelProperty("水果机盈利")
    private BigDecimal fruitProfit;

    @ApiModelProperty("内部号抢福利包金额(流入金额)")
    private BigDecimal rewardGrabInMoneyIn;

    @ApiModelProperty("会员抢福利包(流出金额)")
    private BigDecimal rewardGrabMemberMoneyOut;

    @ApiModelProperty("福利包盈利")
    private BigDecimal rewardGrabProfit;

    @ApiModelProperty("幸运大转盘(流出金额)")
    private BigDecimal luckySpinWinMoneyOut;

    @ApiModelProperty("幸运大转盘盈利")
    private BigDecimal luckySpinProfit;

    @ApiModelProperty("奖励发放")
    private BigDecimal rewardMoneyOut;

    @ApiModelProperty("奖励发放盈利")
    private BigDecimal rewardProfit;

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

    @ApiModelProperty("平台总资金流出")
    private BigDecimal platformMoneyOut;

    @ApiModelProperty("平台总资金流入")
    private BigDecimal platformMoneyIn;

    @ApiModelProperty("平台总盈利")
    private BigDecimal platformProfit;


    @ApiModelProperty("平台盈利项目")
    private BigDecimal totalMoneyIn;


}
