package com.fy.service.statistics.model.Reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 *佣金奖励
 */
@Data
public class Commission implements Serializable {



    @ApiModelProperty("直接佣金奖励金额")
    private BigDecimal direct_commission_amount;

    @ApiModelProperty("牛牛佣金金额")
    private BigDecimal niu_total_amount;

    @ApiModelProperty("扫雷佣金金额")
    private BigDecimal lei_total_amount;

    @ApiModelProperty("禁抢佣金金额")
    private BigDecimal jin_total_amount;

    @ApiModelProperty("佣金奖励合计金额")
    private BigDecimal commission_total_amount;



    //================人数==================



    @ApiModelProperty("直接佣金奖励人数")
    private Integer direct_commission_number;

    @ApiModelProperty("牛牛佣金人数")
    private Integer niu_total_number;

    @ApiModelProperty("扫雷佣金人数")
    private Integer lei_total_number;

    @ApiModelProperty("禁抢佣金人数")
    private Integer jin_total_number;

    @ApiModelProperty("佣金奖励合计人数")
    private Integer commission_total_number;


    //================总和======================


    @ApiModelProperty("总和直接佣金奖励金额")
    private BigDecimal sum_direct_commission_amount;

    @ApiModelProperty("总和牛牛佣金金额")
    private BigDecimal sum_niu_total_amount;

    @ApiModelProperty("总和扫雷佣金金额")
    private BigDecimal sum_lei_total_amount;

    @ApiModelProperty("总和禁抢佣金金额")
    private BigDecimal sum_jin_total_amount;




    @ApiModelProperty("总和佣金奖励合计金额")
    private BigDecimal sum_commission_total_amount;


    //================人数==================



    @ApiModelProperty("总和直接佣金奖励人数")
    private Integer sum_direct_commission_number;

    @ApiModelProperty("总和扫雷佣金人数")
    private Integer sum_lei_total_number;

    @ApiModelProperty("总和禁抢佣金人数")
    private Integer sum_jin_total_number;

    @ApiModelProperty("总和牛牛佣金人数")
    private Integer sum_niu_total_number;


    @ApiModelProperty("总和佣金奖励合计人数")
    private Integer sum_commission_total_number;




}
