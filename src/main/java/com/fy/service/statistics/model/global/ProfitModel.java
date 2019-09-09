package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ProfitModel {
    @ApiModelProperty("盈利的集合")
    private List<ProfitDayModel> profitDayModels;

    @ApiModelProperty("盈利金额")
    private BigDecimal Profit;

}
