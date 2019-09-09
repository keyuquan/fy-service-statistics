package com.fy.service.statistics.controller;


import com.fy.framework.common.model.R;
import com.fy.service.statistics.model.Member.MemberStatusAnalysis;
import com.fy.service.statistics.model.dto.ActiveMemberQuery;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.model.dto.NewMemberQuery;
import com.fy.service.statistics.model.dto.RetainedMemberQuery;
import com.fy.service.statistics.service.MemberStatusAnalysisService;
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
@RequestMapping({"/member"})
@Api(value = "会员状态分析")
public class MemberStatusAnalysisController {

    @Autowired
    MemberStatusAnalysisService member;

    @ApiOperation("新增会员")
    @PostMapping(value = "/NewMember")
    public R<List<MemberStatusAnalysis>> NewMember(@RequestBody BaseQuery param) {
        return R.success(member.NewMember(param));
    }



    @ApiOperation("活跃会员")
    @PostMapping(value = "/ActiveMember")
    public R<List<MemberStatusAnalysis>> ActiveMember(@RequestBody ActiveMemberQuery param) {
        return R.success(member.ActiveMember(param));
    }


    @ApiOperation("有效会员")
    @PostMapping(value = "/EffectiveMember")
    public R<List<MemberStatusAnalysis>> EffectiveMember(@RequestBody BaseQuery param) {
        return R.success(member.EffectiveMember(param));
    }



    @ApiOperation("潜在流失会员")
    @PostMapping(value = "/PotentialMemberLoss")
    public R<List<MemberStatusAnalysis>> PotentialMemberLoss(@RequestBody BaseQuery param) {
        return R.success(member.PotentialMemberLoss(param));
    }


    @ApiOperation("流失会员")
    @PostMapping(value = "/LossMember")
    public R<List<MemberStatusAnalysis>> LossMember(@RequestBody NewMemberQuery param) {
        return R.success(member.LossMember(param));
    }


    @ApiOperation("回归会员")
    @PostMapping(value = "/ReturningMember")
    public R<List<MemberStatusAnalysis>> ReturningMember(@RequestBody BaseQuery param) {
        return R.success(member.ReturningMember(param));
    }


    @ApiOperation("留存会员")
    @PostMapping(value = "/RetainedMember")
    public R<List<MemberStatusAnalysis>> RetainedMember(@RequestBody RetainedMemberQuery param) {
        return R.success(member.RetainedMember(param));
    }




}
