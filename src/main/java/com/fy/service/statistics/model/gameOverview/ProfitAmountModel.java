package com.fy.service.statistics.model.gameOverview;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfitAmountModel {

    @ApiModelProperty("时间")
    private String create_date;


    @ApiModelProperty("牛牛发包金额")
    private BigDecimal niu_game_amount;

    @ApiModelProperty("扫雷发包金额")
    private BigDecimal lei_game_amount;

    @ApiModelProperty("禁枪发包金额")
    private BigDecimal jin_game_amount;



}
