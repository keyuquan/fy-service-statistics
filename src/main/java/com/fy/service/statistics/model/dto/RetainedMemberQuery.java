package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class RetainedMemberQuery  extends BaseQuery {

    @ApiModelProperty("1.数量 2.比率 ")
    @NotNull
    private String probability;


}
