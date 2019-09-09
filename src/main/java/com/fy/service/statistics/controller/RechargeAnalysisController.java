package com.fy.service.statistics.controller;


import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.Member.PaymentAnalysis;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.service.RechargeAnalysisService;
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
@RequestMapping({"/recharge"})
@Api(value = "会员状态分析")
public class RechargeAnalysisController {

    @Autowired
    RechargeAnalysisService analysis;


    @ApiOperation("充值渗透")
    @PostMapping(value = "/RechargePenetration")
    public R<List<PaymentAnalysis>> RechargePenetration(@RequestBody BaseQuery param) {
        return R.success(analysis.RechargePenetration(param));
    }


    @ApiOperation("充值排名")
    @PostMapping(value = "/TopUpRanking")
    public R<List<PaymentAnalysis>> TopUpRanking(@RequestBody BaseQuery param) {
        return R.success(analysis.TopUpRanking(param));
    }




}
