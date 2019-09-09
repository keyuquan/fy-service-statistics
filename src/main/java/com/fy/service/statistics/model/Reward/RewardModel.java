package com.fy.service.statistics.model.Reward;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class RewardModel {



    @ApiModelProperty("幸运大转盘游戏")
    private Sumtotal sumtotal;

    @ApiModelProperty("幸运大转盘游戏")
    private RegisteredModel registeredModel;

    @ApiModelProperty("幸运大转盘游戏")
    private RechargeRewardModel rechargeRewardModel;

    @ApiModelProperty("幸运大转盘游戏")
    private GameModel gameModel;

    @ApiModelProperty("幸运大转盘游戏")
    private Commission commission;




}
