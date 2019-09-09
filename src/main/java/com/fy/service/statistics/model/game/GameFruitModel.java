package com.fy.service.statistics.model.game;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameFruitModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("水果机投注金额")
    private BigDecimal fruit_Betting_amount;

    @ApiModelProperty("水果机中奖金额")
    private BigDecimal fruit_Winning_amount;

    @ApiModelProperty("水果机游戏人数")
    private Integer fruit_game_people_number;

    @ApiModelProperty("水果机投注次数")
    private Integer fruit_Betting_number;

    @ApiModelProperty("水果机中奖次数")
    private Integer fruit_Winning_number;
}
