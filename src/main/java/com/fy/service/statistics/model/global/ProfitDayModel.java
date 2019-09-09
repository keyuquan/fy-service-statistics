package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProfitDayModel {
    @ApiModelProperty("日期")
    private Date createDate;

    @ApiModelProperty("盈利")
    private BigDecimal Profit;
}
