package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class PlatformProfitQuery extends BaseQuery {
    @ApiModelProperty("资金流入流出 1.全部  2.资金流入 3.资金流出")
    @NotNull
    private Integer searcherModel;
}
