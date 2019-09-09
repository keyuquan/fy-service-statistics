package com.fy.service.statistics.controller;


import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.dto.PlatformProfitQuery;
import com.fy.service.statistics.model.platformProfit.PlatformProfitDayModel;
import com.fy.service.statistics.service.PlatformProfitService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping({"/platform"})
@Api(value = "平台统计")
public class PlatformProfitStatisticController {

    @Autowired
    PlatformProfitService platform;

    @ApiOperation("平台盈利")
    @PostMapping(value = "/profit")
    public R<List<PlatformProfitDayModel>> PlatformProfit(@RequestBody PlatformProfitQuery param) {
        return R.success(platform.PlatformProfit(param));
    }


}
