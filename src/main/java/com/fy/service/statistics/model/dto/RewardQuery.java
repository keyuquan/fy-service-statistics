package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class RewardQuery extends BaseQuery {

    @ApiModelProperty("人数与金额的标识  1.金额  2.人数")
    @NotNull
    private String identification;

    @ApiModelProperty("用户标识 0.三种情况都有 1.所有用户 2.真实用户 3.内部号 ")
    @NotNull
    private Integer userFlag;

}
