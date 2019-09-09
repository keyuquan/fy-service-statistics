package com.fy.service.statistics.model.Reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 合计总和统计
 */
@Data
public class Sumtotal implements Serializable {

    @ApiModelProperty("合计总和奖励金额")
    private BigDecimal sum_total_amount;


    @ApiModelProperty("合计总和奖励人数")
    private Integer sum_total_number;

        //===============总和=================


    @ApiModelProperty("总和合计总和奖励金额")
    private BigDecimal all_sum_total_amount;


    @ApiModelProperty("总和合计总和奖励人数")
    private Integer all_sum_total_number;















}
