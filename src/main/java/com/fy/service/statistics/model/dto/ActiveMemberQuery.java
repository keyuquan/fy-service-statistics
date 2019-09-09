package com.fy.service.statistics.model.dto;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class ActiveMemberQuery extends BaseQuery{


    @ApiModelProperty("维度标识 1.活跃趋势 2.分时段 3.忠诚度 ")
    @NotNull
    private String dimension;


}
