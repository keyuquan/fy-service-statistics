package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RechargeDetailsQuery extends BaseQuery {
    @ApiModelProperty("充值方式：0：全部；1：首充；2二充")
    @NotNull
    private Integer rank;

    @ApiModelProperty("每页条数")
    @NotNull
    private Integer size;

    @ApiModelProperty("页数")
    @NotNull
    private Integer page;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String userName;

    @ApiModelProperty("账号")
    private String account;

    @ApiModelProperty("最小充值金额")
    private Integer rechargeMoneyMin;

    @ApiModelProperty("最大充值金额")
    private Integer rechargeMoneyMax;

}
