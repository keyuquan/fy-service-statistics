package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class NewMemberQuery  extends BaseQuery{

    @ApiModelProperty("1.未充值流失 2.充值后流失 ")
    @NotNull
    private String lossmemberdimension;

}
