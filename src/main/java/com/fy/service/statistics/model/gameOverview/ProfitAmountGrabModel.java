package com.fy.service.statistics.model.gameOverview;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProfitAmountGrabModel {


    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛抢包金额")
    private BigDecimal niu_game_grab_amount;

    @ApiModelProperty("扫雷抢包金额")
    private BigDecimal lei_game_grab_amount;

    @ApiModelProperty("禁枪抢包金额")
    private BigDecimal jin_game_grab_amount;






}
