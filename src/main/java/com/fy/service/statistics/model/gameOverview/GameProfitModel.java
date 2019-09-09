package com.fy.service.statistics.model.gameOverview;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 游戏盈利金额
 */
@Data
public class GameProfitModel {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛游戏金额")
    private BigDecimal niu_game_money;

    @ApiModelProperty("扫雷游戏金额")
    private BigDecimal lei_game_money;

    @ApiModelProperty("禁抢游戏金额")
    private BigDecimal jin_game_money;

    @ApiModelProperty("水果机游戏金额")
    private BigDecimal fruit_game_money;

    @ApiModelProperty("总计金额")
    private BigDecimal total_game_money;

}
