package com.fy.service.statistics.model.gameOverview;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FrequencyHairBagModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛发包频次")
    private Integer niu_frequency_number;

    @ApiModelProperty("扫雷发包频次")
    private Integer lei_frequency_number;

    @ApiModelProperty("禁抢发包频次")
    private Integer jin_frequency_number;

}
