package com.fy.service.statistics.model.Member;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 付费分析
 */
@Data
public class PaymentAnalysis {

    @ApiModelProperty("日期")
    private String create_date;


//================ 充值渗透 ==================

    @ApiModelProperty("充值渗透 充值金额")
    private BigDecimal  recharge_member_money;

    //===========每个活跃会员的平均充值
    @ApiModelProperty("活跃会员")
    private Integer active_member;

    @ApiModelProperty("每个活跃会员的平均充值")
    private BigDecimal active_member_arpu;

    //===========每个充值会员的平均充值

    @ApiModelProperty("充值会员")
    private Integer  recharge_member;

    @ApiModelProperty("每个充值会员的平均充值")
    private BigDecimal  recharge_member_arppu;


//================ 充值排名 ==================

    @ApiModelProperty("充值排名")
    private Integer  top_up_ranking;

    @ApiModelProperty("用户ID")
    private String  user_id;

    @ApiModelProperty("用户账号")
    private String  user_account;

    @ApiModelProperty("用户名")
    private String  user_name;

    @ApiModelProperty("充值排名 充值金额")
    private BigDecimal  recharge_amount_money;



}
