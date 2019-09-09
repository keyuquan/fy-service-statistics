package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class UserNewModel {

    @ApiModelProperty("新增会员集合")
    private List<UserDayModel> UserDayModels;

    @ApiModelProperty("新增会员")
    private Integer UserCount;
}
