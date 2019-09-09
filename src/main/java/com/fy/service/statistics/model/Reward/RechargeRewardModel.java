package com.fy.service.statistics.model.Reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 充值奖励
 */
@Data
public class RechargeRewardModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;


    @ApiModelProperty("首充奖励金额")
    private BigDecimal first_reward_amount;

    @ApiModelProperty("二充奖励金额")
    private BigDecimal second_reward_amount;

    @ApiModelProperty("邀请会员首充奖励金额")
    private BigDecimal invite_members_first_amount;

    @ApiModelProperty("邀请会员二充奖励金额")
    private BigDecimal invite_members_second_amount;

    @ApiModelProperty("充值合计金额")
    private BigDecimal reward_total_amount;

//================人数==============================

    @ApiModelProperty("邀请会员首充奖励人数")
    private Integer invite_members_first_number;

    @ApiModelProperty("邀请会员二充奖励人数")
    private Integer invite_members_second_number;

    @ApiModelProperty("首充奖励人数")
    private Integer first_reward_number;

    @ApiModelProperty("二充奖励人数")
    private Integer second_reward_number;

    @ApiModelProperty("充值合计人数")
    private Integer reward_total_number;




//================总和=========================

    @ApiModelProperty("总和首充奖励金额")
    private BigDecimal sum_first_reward_amount;

    @ApiModelProperty("总和二充奖励金额")
    private BigDecimal sum_second_reward_amount;

    @ApiModelProperty("总和邀请会员首充奖励金额")
    private BigDecimal sum_invite_members_first_amount;

    @ApiModelProperty("总和邀请会员二充奖励金额")
    private BigDecimal sum_invite_members_second_amount;

    @ApiModelProperty("总和充值合计金额")
    private BigDecimal sum_reward_total_amount;

//================人数==============================

    @ApiModelProperty("总和邀请会员首充奖励人数")
    private Integer sum_invite_members_first_number;

    @ApiModelProperty("总和邀请会员二充奖励人数")
    private Integer sum_invite_members_second_number;

    @ApiModelProperty("总和首充奖励人数")
    private Integer sum_first_reward_number;

    @ApiModelProperty("总和二充奖励人数")
    private Integer sum_second_reward_number;

    @ApiModelProperty("总和充值合计人数")
    private Integer sum_reward_total_number;




}
