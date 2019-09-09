package com.fy.service.statistics.controller;


import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.Reward.RewardModel;
import com.fy.service.statistics.model.dto.RewardQuery;
import com.fy.service.statistics.service.RewardStatisticsService;
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
@RequestMapping({"/reward"})
@Api(value = "活动统计")
public class RewardStaticsController {

    @Autowired
    RewardStatisticsService rewardStatisticsService;

    @ApiOperation("奖励统计")
    @PostMapping(value = "/reward")
    public R<List<RewardModel>> RewardStatistics(@RequestBody RewardQuery param) {
        return R.success(rewardStatisticsService.RewardStatistics(param));
    }

}
