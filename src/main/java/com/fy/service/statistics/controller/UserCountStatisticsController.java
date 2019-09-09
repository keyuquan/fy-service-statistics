package com.fy.service.statistics.controller;

import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.dto.RechargeQuery;
import com.fy.service.statistics.model.user.UserCountModel;
import com.fy.service.statistics.service.UserCountStatisticsService;
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
@RequestMapping({"/userCount"})
@Api(value = "用户数量")
public class UserCountStatisticsController {

    @Autowired
    UserCountStatisticsService userCountStatisticsService;

    @ApiOperation("用户数量")
    @PostMapping(value = "/userCount")
    public R<List<UserCountModel>> userCountStatistics(@RequestBody RechargeQuery param) {
        return R.success(userCountStatisticsService.userCountStatistics(param));
    }


}
