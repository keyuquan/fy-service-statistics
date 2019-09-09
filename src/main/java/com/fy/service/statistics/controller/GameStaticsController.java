package com.fy.service.statistics.controller;


import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.dto.GameQuery;
import com.fy.service.statistics.model.game.GameOverviewModel;
import com.fy.service.statistics.service.GameStatisticService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping({"/game"})
@Api(value = "游戏统计")
public class GameStaticsController {

    @Autowired
    GameStatisticService gameStatisticService;

    @ApiOperation("游戏统计")
    @PostMapping(value = "/game")
    public  R<Map<String,List>> GameOverview(@RequestBody GameQuery param) {
        return R.success(gameStatisticService.GameOverview(param));
    }


}
