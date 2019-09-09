package com.fy.service.statistics.model.gameOverview;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 抢包人数
 */
@Data
public class PeopleGrabPackageModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛抢包人数")
    private Integer niu_grabbing_people_number;

    @ApiModelProperty("扫雷抢包人数")
    private Integer lei_grabbing_people_number;

    @ApiModelProperty("禁抢抢包人数")
    private Integer jin_grabbing_people_number;

}
