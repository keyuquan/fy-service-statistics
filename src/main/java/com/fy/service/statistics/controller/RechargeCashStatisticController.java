package com.fy.service.statistics.controller;

import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.dto.RechargeDetailsQuery;
import com.fy.service.statistics.model.dto.RechargeQuery;
import com.fy.service.statistics.model.recharge.ReChargeDetailAllModel;
import com.fy.service.statistics.model.recharge.RechargeCashModel;
import com.fy.service.statistics.service.RechargeCashStatisticService;
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
@Api(value = "充值提现")
public class RechargeCashStatisticController {
    @Autowired
    RechargeCashStatisticService rechargeCashStatisticService;

    @ApiOperation("充值提现")
    @PostMapping(value = "/recharge")
    public R<List<RechargeCashModel>> rechargeCashStatistic(@RequestBody RechargeQuery param) {
        return R.success(rechargeCashStatisticService.rechargeCashStatistic(param));
    }

    @ApiOperation("首充/二充会员列表")
    @PostMapping(value = "/details")
    public R<ReChargeDetailAllModel> rechargeDetails(@RequestBody RechargeDetailsQuery param) {
        return R.success(rechargeCashStatisticService.rechargeDetails(param));
    }
}
