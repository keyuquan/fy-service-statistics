package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;


@Data
public class GameQuery extends BaseQuery {

    @ApiModelProperty("用户标识 0.三种情况都有 1.游戏盈利 2.会员用户 3.内部号 ")
    @NotNull
    private Integer userFlag;

    @ApiModelProperty("游戏标识 1.安游戏显示 2.按金额/人数/次数显示 3.游戏盈利 ")
    @NotNull
    private String game;

}
