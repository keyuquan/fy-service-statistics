package com.fy.service.statistics.controller;

import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.dto.PlatformProfitQuery;
import com.fy.service.statistics.model.increase.PlatformIncreaseDayModel;
import com.fy.service.statistics.service.PlatformIncreaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统调增
 */

@Slf4j
@RestController
@RequestMapping({"/increase"})
@Api(value = "系统调账")
public class PlatformIncreaseStatisticController {

    @Autowired
    PlatformIncreaseService platform;

    @ApiOperation("平台盈利")
    @PostMapping(value = "/profit")
    public R<List<PlatformIncreaseDayModel>> PlatformProfit(@RequestBody PlatformProfitQuery param) {
        return R.success(platform.PlatformIncreaseProfit(param));
    }

}
