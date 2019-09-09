package com.fy.service.statistics.model.Reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 游戏奖励
 */
@Data
public class GameModel implements Serializable {

    @ApiModelProperty("发包满额奖励金额")
    private BigDecimal full_package_amount;

    @ApiModelProperty("抢包满额奖励金额")
    private BigDecimal grab_package_amountt;

    @ApiModelProperty("豹子顺子奖励金额")
    private BigDecimal leopard_straight_amount;


    @ApiModelProperty("群福利包金额")
    private BigDecimal welfare_package_amount;

    @ApiModelProperty("赠送救援金额")
    private BigDecimal rescue_fund_amount;

    @ApiModelProperty("领取福利包金额")
    private BigDecimal grab_reward_money;

    @ApiModelProperty("幸运大转盘金额")
    private BigDecimal luck_span_money;

    @ApiModelProperty("游戏合计金额")
    private BigDecimal game_total_amount;


    //===============人数=============


    @ApiModelProperty("发包满额奖励人数")
    private Integer full_package_number;

    @ApiModelProperty("抢包满额奖励人数")
    private Integer grab_package_number;

    @ApiModelProperty("豹子顺子奖励人数")
    private Integer leopard_straight_number;

    @ApiModelProperty("赠送救援人数")
    private Integer rescue_fund_number;

    @ApiModelProperty("领取福利包人数")
    private Integer grab_reward_user_count;

    @ApiModelProperty("幸运大转盘人数")
    private Integer luck_span_number;

    @ApiModelProperty("游戏合计人数")
    private Integer game_total_number;


    //================总和=============================


    @ApiModelProperty("总和发包满额奖励金额")
    private BigDecimal sum_full_package_amount;

    @ApiModelProperty("总和抢包满额奖励金额")
    private BigDecimal sum_grab_package_amountt;

    @ApiModelProperty("总和豹子顺子奖励金额")
    private BigDecimal sum_leopard_straight_amount;


    @ApiModelProperty("总和群福利包金额")
    private BigDecimal sum_welfare_package_amount;

    @ApiModelProperty("总和赠送救援金额")
    private BigDecimal sum_rescue_fund_amount;

    @ApiModelProperty("总和游戏合计金额")
    private BigDecimal sum_game_total_amount;

    @ApiModelProperty("总和领取福利包金额")
    private BigDecimal sum_grab_reward_money;

    @ApiModelProperty("总和幸运大转盘金额")
    private BigDecimal sum_luck_span_money;

    //===============人数=============


    @ApiModelProperty("总和发包满额奖励人数")
    private Integer sum_full_package_number;

    @ApiModelProperty("总和抢包满额奖励人数")
    private Integer sum_grab_package_number;

    @ApiModelProperty("总和豹子顺子奖励人数")
    private Integer sum_leopard_straight_number;

    @ApiModelProperty("总和领取福利包人数")
    private Integer sum_grab_reward_user_count;

    @ApiModelProperty("总和幸运大转盘人数")
    private Integer sum_luck_span_number;

    @ApiModelProperty("总和赠送救援人数")
    private Integer sum_rescue_fund_number;

    @ApiModelProperty("总和游戏合计人数")
    private Integer sum_game_total_number;


}
