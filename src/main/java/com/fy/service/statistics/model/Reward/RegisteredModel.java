package com.fy.service.statistics.model.Reward;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 注册奖励
 */
@Data
public class RegisteredModel implements Serializable {

    @ApiModelProperty("注册登陆奖励金额")
    private BigDecimal registered_login_amount;

    @ApiModelProperty("邀请注册金额")
    private BigDecimal invitation_registration_amount;

    @ApiModelProperty("注册合计金额")
    private BigDecimal registered_total_amount;

    //===========================================

    @ApiModelProperty("注册登陆奖励人数")
    private Integer registered_login_number;

    @ApiModelProperty("邀请注册奖励人数")
    private Integer invitation_registration_number;

    @ApiModelProperty("注册合计人数")
    private Integer registered_total_number;


    //====================总和=============================

    @ApiModelProperty("总和注册登陆奖励金额")
    private BigDecimal sum_registered_login_amount;

    @ApiModelProperty("总和邀请注册金额")
    private BigDecimal sum_invitation_registration_amount;

    @ApiModelProperty("总和注册合计金额")
    private BigDecimal sum_registered_total_amount;

    //===========================================

    @ApiModelProperty("总和注册登陆奖励人数")
    private Integer sum_registered_login_number;

    @ApiModelProperty("总和邀请注册奖励人数")
    private Integer sum_invitation_registration_number;

    @ApiModelProperty("总和注册合计人数")
    private Integer sum_registered_total_number;

}
