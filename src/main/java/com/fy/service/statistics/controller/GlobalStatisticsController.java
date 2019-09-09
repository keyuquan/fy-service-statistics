package com.fy.service.statistics.controller;

import com.fy.framework.common.model.R;
import com.fy.service.common.web.controller.BaseController;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.model.global.GlobalModel;
import com.fy.service.statistics.service.GlobalStatisticsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/global"})
@Api(value = "全局统计")
public class GlobalStatisticsController extends BaseController {
    @Autowired
    GlobalStatisticsService globalStatisticsService;

    @ApiOperation("全局统计")
    @PostMapping(value = "/globalStatistics")
    public R<GlobalModel> globalStatistics(@RequestBody BaseQuery param) {
        return R.success(globalStatisticsService.globalStatistics(param));
    }




}
