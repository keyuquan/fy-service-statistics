package com.fy.service.statistics.model.game;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class GameNiuModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛手续费金额")
    private BigDecimal niu_formalities_amount;

    @ApiModelProperty("牛牛赔付金额")
    private BigDecimal niu_Pay_amount;

    @ApiModelProperty("牛牛获赔金额")
    private BigDecimal niu_get_payout_money;


    @ApiModelProperty("牛牛发包人数")
    private Integer niu_people_number;

    @ApiModelProperty("牛牛抢包人数")
    private Integer niu_grabbing_people_number;

    @ApiModelProperty("牛牛游戏人数")
    private Integer niu_game_people_number;

    @ApiModelProperty("牛牛发包次数（频次）")
    private Integer niu_frequency_number;

    @ApiModelProperty("牛牛抢包次数（频次）")
    private Integer niu_grabbing_frequency_number;





}
