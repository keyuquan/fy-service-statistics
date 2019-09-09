package com.fy.service.statistics.model.game;

import com.fy.service.statistics.model.gameOverview.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameOverviewModel implements Serializable {

    @ApiModelProperty("发包金额")
    private ProfitAmountModel profitAmountModel;

    @ApiModelProperty("水果机游戏")
    private GameFruitModel gameFruitModel;

    @ApiModelProperty("禁抢游戏")
    private GameJinModel gameJinModel;

    @ApiModelProperty("扫雷游戏")
    private GameLeiModel gameLeiModel;

    @ApiModelProperty("幸运大转盘游戏")
    private GameLuckyModel gameLuckyModel;

    @ApiModelProperty("牛牛游侠")
    private GameNiuModel gameNiuModel;


    @ApiModelProperty("发包人数")
    private PeopleHairBagModel peopleHairBag;

    @ApiModelProperty("抢包人数")
    private PeopleGrabPackageModel peopleGrabPackage;

    @ApiModelProperty("游戏人数")
    private PeopleGameModel peopleGame;

    @ApiModelProperty("发包频次")
    private FrequencyHairBagModel frequencyHairBag;

    @ApiModelProperty("抢包频次")
    private FrequencyGrabPackageModel frequencyGrabPackage;

    @ApiModelProperty("游戏盈利金额")
    private GameProfitModel gameProfitModel;


    @ApiModelProperty("抢包金额")
    private ProfitAmountGrabModel profitAmountGrabModel;




    @ApiModelProperty("总和")
    private ColumnSumModel columnSumModel;



}
