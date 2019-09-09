package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserActiveModel {
    @ApiModelProperty("活跃会员集合")
    private List<UserDayModel> UserDayModels;

    @ApiModelProperty("活跃会员")
    private Integer UserActive;
}
