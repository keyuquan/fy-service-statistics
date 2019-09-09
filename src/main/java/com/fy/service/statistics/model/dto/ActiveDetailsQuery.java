package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 活跃会员明细
 */
@Data
public class ActiveDetailsQuery extends BaseQuery {

    @ApiModelProperty("会员ID")
    @NotNull
    private String user_id;

    @ApiModelProperty("会员昵称")
    @NotNull
    private String nick;

    @ApiModelProperty("手机号")
    @NotNull
    private String mobile;

    @ApiModelProperty("会员类型 0.普通用户 1.代理用户")
    @NotNull
    private String agent_flag;


    @ApiModelProperty("页码 ")
    @NotNull
    private Integer page;

    @ApiModelProperty("每页条数")
    @NotNull
    private Integer size;


    @ApiModelProperty("3.活跃会员明细 、活跃的新增会员明细  4.7天登录中≥3天的会员 ")
    @NotNull
    private String identification;


    @ApiModelProperty("查询分类  1.会员活跃趋势/7天登录中≥3天的会员   2.分时段活跃会员 ")
    @NotNull
    private String classification;

    @ApiModelProperty("3.活跃的新增会员明细")
    @NotNull
    private String rank;


    @ApiModelProperty("24时段查询  1.1点  2.两点...... ")
    @NotNull
    private String hour_type;




}
