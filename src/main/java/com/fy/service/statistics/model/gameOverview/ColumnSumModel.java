package com.fy.service.statistics.model.gameOverview;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ColumnSumModel {



    @ApiModelProperty("牛牛游戏合计金额")
    private BigDecimal niu_game_money;

    @ApiModelProperty("扫雷游戏合计金额")
    private BigDecimal lei_game_money;

    @ApiModelProperty("禁抢游戏合计金额")
    private BigDecimal jin_game_money;

    @ApiModelProperty("水果机游戏合计金额")
    private BigDecimal fruit_game_money;


}
