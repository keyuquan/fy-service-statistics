package com.fy.service.statistics.model.game;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameLuckyModel implements Serializable {


    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("幸运大转盘中奖金额")
    private BigDecimal lucky_Winning_amount;

    @ApiModelProperty("幸运大转盘游戏人数")
    private Integer lucky_game_people_number;

    @ApiModelProperty("幸运大转盘中奖次数")
    private Integer lucky_Winning_number;

}
