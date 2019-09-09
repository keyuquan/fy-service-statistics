package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 带用户类型的 查询
 * 1 :     所有用户
 * 2 ：  真实玩家
 * 3 ：  内部号/ 机器人
 */
@Data
public class RechargeQuery extends BaseQuery {
    @ApiModelProperty("用户标识")
    @NotNull
    private Integer userFlag;
}
