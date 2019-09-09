package com.fy.service.statistics.model.game;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameJinModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;


    @ApiModelProperty("禁抢发包金额")
    private BigDecimal jin_hair_bag_amount;

    @ApiModelProperty("禁抢抢包金额")
    private BigDecimal jin_grab_package_amount;

    @ApiModelProperty("禁抢赔付金额")
    private BigDecimal jin_Pay_amount;

    @ApiModelProperty("禁抢获赔金额")
    private BigDecimal jin_get_Pay_amount;

    @ApiModelProperty("禁抢发包人数")
    private Integer jin_people_number;

    @ApiModelProperty("禁抢抢包人数")
    private Integer jin_grabbing_people_number;

    @ApiModelProperty("禁抢游戏人数")
    private Integer jin_game_people_number;

    @ApiModelProperty("禁抢发包频次")
    private Integer jin_frequency_number;

    @ApiModelProperty("禁抢抢包频次")
    private Integer jin_grabbing_frequency_number;

}
