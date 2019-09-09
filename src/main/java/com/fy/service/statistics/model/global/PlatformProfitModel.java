package com.fy.service.statistics.model.global;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * (\__/)
 * ( ^-^)
 * /つ @author asheng
 *
 * @date 2019/7/6 10:42
 * 平台盈亏统计
 */
@Data
public class PlatformProfitModel implements Serializable {

    @ApiModelProperty("活动支出")
    private BigDecimal ReWardProfit;

    @ApiModelProperty("游戏盈亏")
    private BigDecimal GameProfit;

    @ApiModelProperty("系统调账")
    private BigDecimal reduceIncreaseProfit;

    @ApiModelProperty("会员福利包")
    private BigDecimal ReWardRedBonusProfit;

    @ApiModelProperty("平台总盈利")
    private BigDecimal platformProfit;

    @ApiModelProperty("游戏报表合计")
    private BigDecimal GameNetProfit;

}
