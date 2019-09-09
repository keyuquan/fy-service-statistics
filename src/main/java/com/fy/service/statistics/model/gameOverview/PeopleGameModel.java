package com.fy.service.statistics.model.gameOverview;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


/**
 * 游戏人数
 */
@Data
public class PeopleGameModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛游戏总人数")
    private Integer niu_game_people_number;

    @ApiModelProperty("扫雷游戏人数")
    private Integer lei_game_people_number;

    @ApiModelProperty("禁抢游戏人数")
    private Integer jin_game_people_number;

    @ApiModelProperty("水果机游戏人数")
    private Integer fruit_game_people_number;

    @ApiModelProperty("幸运大转盘游戏人数")
    private Integer lucky_game_people_number;

}
