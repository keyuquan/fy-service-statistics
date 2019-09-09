package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 新增会员明细
 */
@Data
public class MemberDetailsQuery extends BaseQuery {

    @ApiModelProperty("会员ID")
    @NotNull
    private String user_id;

    @ApiModelProperty("会员昵称")
    @NotNull
    private String nick;

    @ApiModelProperty("手机号")
    @NotNull
    private String mobile;

    @ApiModelProperty("会员类型 0.普通会员  1.代理会员 ")
    @NotNull
    private String agent_flag;


    @ApiModelProperty("页码 ")
    @NotNull
    private Integer page;

    @ApiModelProperty("每页条数")
    @NotNull
    private Integer size;


    @ApiModelProperty("1.新增会员明细 " +
            "2.充值新增会员明细 " +
            "5.有效会员明细/第二次充值会员明细  " +
            "8.潜在流失会员明细 " +
            "14.回归会员明细 " +
            "9.未充值流失用户明细  " +
            "10.充值流失用户明细 " +
            "11首充 流失会员明细  " +
            "12二次充值会员明细  " +
            "13大于二次充值会员明细 " +
            "14.回归会员明细" +
            "15.次日留存会员" +
            "16.3日留存会员" +
            "17.7日留存会员" +
            "18.14日留存会员" +
            "19.30日留存会员")
    @NotNull
    private String identification;

    @ApiModelProperty("9.流失会员")
    @NotNull
    private String rank;


    @ApiModelProperty("最小充值金额")
    private Integer rechargeMoneyMin;

    @ApiModelProperty("最大充值金额")
    private Integer rechargeMoneyMax;


}
