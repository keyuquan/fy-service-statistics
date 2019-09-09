package com.fy.service.statistics.model.game;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameLeiModel implements Serializable {


    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("扫雷发包金额")
    private BigDecimal lei_hair_bag_amount;

    @ApiModelProperty("扫雷抢包金额")
    private BigDecimal lei_grab_package_amount;

    @ApiModelProperty("扫雷赔付金额")
    private BigDecimal lei_Pay_amount;

    @ApiModelProperty("扫雷获赔金额")
    private BigDecimal lei_get_payout_money;


    @ApiModelProperty("扫雷发包人数")
    private Integer lei_people_number;

    @ApiModelProperty("扫雷抢包人数")
    private Integer lei_grabbing_people_number;

    @ApiModelProperty("扫雷游戏人数")
    private Integer lei_game_people_number;

    @ApiModelProperty("扫雷发包频次")
    private Integer lei_frequency_number;

    @ApiModelProperty("扫雷抢包频次")
    private Integer lei_grabbing_frequency_number;
}
