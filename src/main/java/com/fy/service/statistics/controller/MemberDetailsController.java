package com.fy.service.statistics.controller;


import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.Member.MemberDetails;
import com.fy.service.statistics.model.Member.MenberCount;
import com.fy.service.statistics.model.dto.ActiveDetailsQuery;
import com.fy.service.statistics.model.dto.MemberDetailsQuery;
import com.fy.service.statistics.service.MemberStatusDetailsService;
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
@RequestMapping({"/details"})
@Api(value = "会员状态明细")
public class MemberDetailsController {


    @Autowired
    MemberStatusDetailsService details;

    @ApiOperation("新增会员明细")
    @PostMapping(value = "/AddMemberDetails")
    public R<MenberCount> AddMemberDetails(@RequestBody MemberDetailsQuery param) {
        return R.success(details.AddMemberDetails(param));
    }

    @ApiOperation("活跃会员明细")
    @PostMapping(value = "/ActiveMemberDetails")
    public R<MenberCount> ActiveMemberDetails(@RequestBody ActiveDetailsQuery param) {
        return R.success(details.ActiveMemberDetails(param));
    }



    @ApiOperation("有效会员明细 潜在流失会员明细 流失会员明细 回归会员明细 留存会员明细")
    @PostMapping(value = "/EffectiveMemberDetails")
    public R<MenberCount> EffectiveMemberDetails(@RequestBody MemberDetailsQuery param) {
        return R.success(details.EffectiveMemberDetails(param));
    }



    @ApiOperation("会员充值明细")
    @PostMapping(value = "/MemberRechargeDetails")
    public R<MenberCount> MemberRechargeDetails(@RequestBody MemberDetailsQuery param) {
        return R.success(details.MemberRechargeDetails(param));
    }



}
