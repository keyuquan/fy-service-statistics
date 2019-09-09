package com.fy.service.statistics.model.Member;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 会员状态明细
 */
@Data
public class MemberDetails {

    @ApiModelProperty("会员ID")
    private Integer user_id;

    @ApiModelProperty("会员昵称")
    private String nick;

    @ApiModelProperty("手机号")
    private String mobile;

    @ApiModelProperty("邀请码")
    private String invitecode;

    @ApiModelProperty("会员类型 1.代理 0.普通会员")
    private String agent_flag;

    @ApiModelProperty("注册时间")
    private String create_time;

    @ApiModelProperty("查询时间")
    private String create_date;

    @ApiModelProperty("充值金额")
    private BigDecimal money;



}
