package com.fy.service.statistics.model.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * (\__/)
 * ( ^-^)
 * /つ @author asheng
 *
 * @date 2019/7/6 9:36
 */
@Data
public class BaseQuery implements Serializable {

    @ApiModelProperty("开始时间")
    @NotNull
    private String beginTime;

    @ApiModelProperty("结束时间")
    @NotNull
    private String endTime;

    @ApiModelProperty("商户ID")
    private String tenantId;


}
