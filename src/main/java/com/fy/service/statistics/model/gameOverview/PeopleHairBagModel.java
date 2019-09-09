package com.fy.service.statistics.model.gameOverview;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 发包人数
 */
@Data
public class PeopleHairBagModel implements Serializable {

    @ApiModelProperty("时间")
    private String create_date;

    @ApiModelProperty("牛牛发包人数")
    private Integer niu_people_number;

    @ApiModelProperty("扫雷发包人数")
    private Integer lei_people_number;

    @ApiModelProperty("禁抢发包人数")
    private Integer jin_people_number;

}
