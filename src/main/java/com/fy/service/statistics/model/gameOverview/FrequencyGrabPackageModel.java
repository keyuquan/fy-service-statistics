package com.fy.service.statistics.model.gameOverview;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class FrequencyGrabPackageModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛抢包频次")
    private Integer niu_grabbing_frequency_number;


    @ApiModelProperty("扫雷抢包频次")
    private Integer lei_grabbing_frequency_number;

    @ApiModelProperty("禁抢抢包频次")
    private Integer jin_grabbing_frequency_number;

}
