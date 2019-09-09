package com.fy.service.statistics.model.recharge;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class ReChargeDetailAllModel {

    @ApiModelProperty("充值明细列表")
    private List<RechargeDetailsMode> rechargeDetailsModes;

    @ApiModelProperty("首充比数")
    private Long firstRechargeCount;

    @ApiModelProperty("二充比数")
    private Long secondRechargeCount;

    @ApiModelProperty("数据条数")
    private long totalCount;

}
