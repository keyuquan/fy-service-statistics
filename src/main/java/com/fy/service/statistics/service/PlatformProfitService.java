package com.fy.service.statistics.service;


import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.PlatformProfitQuery;
import com.fy.service.statistics.model.platformProfit.PlatformProfitDayModel;
import com.fy.service.statistics.utils.DateUtils;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 平台盈利
 */
@Service
@Slf4j
public class PlatformProfitService {
    @Autowired
    EsQueryUtils esQueryUtils;

    /**
     * 平台盈利
     *
     * @param param
     * @return
     */
    public List<PlatformProfitDayModel> PlatformProfit(PlatformProfitQuery param) {
        // 1. 需要查询的信息
        List<PlatformProfitDayModel> list = new ArrayList();

        //  2.查询
        String[] fields = new String[]{"create_date", "owner_code", "sumall_40", "lei_grab_money_out", "lei_payout_money_death_in", "lei_payout_money_other_in", "niu_payout_money_in", "niu_payout_money_out", "lei_grab_money_death_in", "lei_grab_money_other_in", "lei_payout_money_in", "lei_payout_money_death_out", "lei_payout_money_other_out", "lei_send_money_out", "jin_grab_money_in", "jin_payout_money_out", "sumall_36", "sumall_37", "reward_grab_money_in", "reward_grab_money_out", "sumall_32", "reduce_member_money_In", "increase_member_money_out", "reduce_in_money", "increase_in_money", "platform_money_in", "platform_money_out", "sumall_19", "sumall_20", "sumall_27", "sumall_28", "sumall_30", "sumall_21", "sumall_22", "sumall_23", "sumall_16", "reward_grab_money", "sumall_43", "sumall_8", "sumall_9", "sumall_31", "sumall_38"};
        SearchResponse results = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        //  3.组装查询结果
        SearchHit[] searchHits = results.getHits().getHits();
        Integer searcherModel = param.getSearcherModel();

        if ((searchHits != null) && (searchHits.length > 0)) {
            for (SearchHit thisSearchHit : searchHits) {
                Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();
                PlatformProfitDayModel model = new PlatformProfitDayModel();
                model.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));

                //  牛牛相关字段
                double niuFormalitiesMoneyIn = Math.abs(Double.parseDouble(thisElm.get("sumall_40").toString()));
                double niuPayoutMoneyIn = Double.parseDouble(thisElm.get("niu_payout_money_in").toString());
                double niuPayoutMoneyOut = Double.parseDouble(thisElm.get("niu_payout_money_out").toString());

                //  扫雷相关字段
                // 赔付相关
                double leiNoDeathPayoutMoneyIn = Double.parseDouble(thisElm.get("lei_payout_money_death_in").toString());
                double leiOtherPayoutMoneyIn = Double.parseDouble(thisElm.get("lei_payout_money_other_in").toString());
                double leiNoDeathPayoutMoneyOut = Double.parseDouble(thisElm.get("lei_payout_money_death_out").toString());
                double leiOtherPayoutMoneyOut = Double.parseDouble(thisElm.get("lei_payout_money_other_out").toString());

                // 抢包相关
                double leiNoDeathGrabMoneyIn = Double.parseDouble(thisElm.get("lei_grab_money_death_in").toString());
                double leiOtherGrabMoneyIn = Double.parseDouble(thisElm.get("lei_grab_money_other_in").toString());
                double leiGrabMoneyOut = Double.parseDouble(thisElm.get("lei_grab_money_out").toString());

                //  禁抢相关字段
                double jinGrabMoneyIn = Double.parseDouble(thisElm.get("jin_grab_money_in").toString());
                double jinPayoutOut = Double.parseDouble(thisElm.get("jin_payout_money_out").toString());

                //  水果机相关字段
                double fruitBetMoneyIn = Math.abs(Double.parseDouble(thisElm.get("sumall_36").toString()));
                double fruitWinMoneyOut = Math.abs(Double.parseDouble(thisElm.get("sumall_37").toString()));

                //  福利包 包含在 奖励发放中
                double rewardGrabInMoneyIn = Double.parseDouble(thisElm.get("reward_grab_money_in").toString());
                double rewardGrabMemberMoneyOut = Double.parseDouble(thisElm.get("reward_grab_money_out").toString());

                // 幸运大转盘
                double luckySpinWinMoneyOut = Math.abs(Double.parseDouble(thisElm.get("sumall_32").toString()));

                // 奖励发放
                double sumall_19 = Math.abs(Double.parseDouble(thisElm.get("sumall_19").toString()));
                double sumall_20 = Math.abs(Double.parseDouble(thisElm.get("sumall_20").toString()));
                double sumall_27 = Math.abs(Double.parseDouble(thisElm.get("sumall_27").toString()));
                double sumall_28 = Math.abs(Double.parseDouble(thisElm.get("sumall_28").toString()));
                double sumall_30 = Math.abs(Double.parseDouble(thisElm.get("sumall_30").toString()));
                double sumall_21 = Math.abs(Double.parseDouble(thisElm.get("sumall_21").toString()));
                double sumall_22 = Math.abs(Double.parseDouble(thisElm.get("sumall_22").toString()));
                double sumall_23 = Math.abs(Double.parseDouble(thisElm.get("sumall_23").toString()));
                double sumall_16 = Math.abs(Double.parseDouble(thisElm.get("sumall_16").toString()));
                double sumall_43 = Math.abs(Double.parseDouble(thisElm.get("sumall_43").toString()));
                double sumall_8 = Math.abs(Double.parseDouble(thisElm.get("sumall_8").toString()));
                double sumall_9 = Math.abs(Double.parseDouble(thisElm.get("sumall_9").toString()));
                double sumall_31 = Math.abs(Double.parseDouble(thisElm.get("sumall_31").toString()));
                double sumall_38 = Math.abs(Double.parseDouble(thisElm.get("sumall_38").toString()));
                BigDecimal rewardMoneyOut = MathUtils.toDecimalUp(sumall_19)
                        .add(MathUtils.toDecimalUp(sumall_20))
                        .add(MathUtils.toDecimalUp(sumall_27))
                        .add(MathUtils.toDecimalUp(sumall_28))
                        .add(MathUtils.toDecimalUp(sumall_30))
                        .add(MathUtils.toDecimalUp(sumall_21))
                        .add(MathUtils.toDecimalUp(sumall_22))
                        .add(MathUtils.toDecimalUp(sumall_23))
                        .add(MathUtils.toDecimalUp(sumall_16))
                        .add(MathUtils.toDecimalUp(sumall_43))
                        .add(MathUtils.toDecimalUp(sumall_8))
                        .add(MathUtils.toDecimalUp(sumall_9))
                        .add(MathUtils.toDecimalUp(sumall_31))
                        .add(MathUtils.toDecimalUp(sumall_38))
                        .add(MathUtils.toDecimalUp(rewardGrabMemberMoneyOut));

                // 系统调减
                double reduceMemberMoneyIn = Double.parseDouble(thisElm.get("reduce_member_money_In").toString());
                double increaseMemberMoneyOut = Double.parseDouble(thisElm.get("increase_member_money_out").toString());

                double platformMoneyIn = Double.parseDouble(thisElm.get("platform_money_in").toString());
                double platformMoneyOut = Double.parseDouble(thisElm.get("platform_money_out").toString());

                if (searcherModel == 1) {
                    model.setPlatformMoneyIn(MathUtils.toDecimalUp(platformMoneyIn));//平台总资金流入
                    model.setPlatformMoneyOut(MathUtils.toDecimalUp(platformMoneyOut));//平台总资金流出
                    model.setPlatformProfit(MathUtils.toDecimalUp(platformMoneyIn).subtract(MathUtils.toDecimalUp(platformMoneyOut)));

                    model.setNiuProfit(MathUtils.toDecimalUp(niuFormalitiesMoneyIn)
                            .add(MathUtils.toDecimalUp(niuPayoutMoneyIn))
                            .subtract(MathUtils.toDecimalUp(niuPayoutMoneyOut)));//牛牛平台盈利

                    model.setLeiProfit(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyIn)
                            .add(MathUtils.toDecimalUp(leiOtherPayoutMoneyIn))
                            .add(MathUtils.toDecimalUp(leiNoDeathGrabMoneyIn))
                            .add(MathUtils.toDecimalUp(leiOtherGrabMoneyIn))
                            .subtract(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyOut))
                            .subtract(MathUtils.toDecimalUp(leiOtherPayoutMoneyOut))
                            .subtract(MathUtils.toDecimalUp(leiGrabMoneyOut)));//扫雷平台盈利

                    model.setJinProfit(MathUtils.toDecimalUp(jinGrabMoneyIn).subtract(MathUtils.toDecimalUp(jinPayoutOut)));//禁抢平台盈利
                    model.setFruitProfit(MathUtils.toDecimalUp(fruitBetMoneyIn).subtract(MathUtils.toDecimalUp(fruitWinMoneyOut)));//水果机盈利

                    model.setLuckySpinProfit(MathUtils.toDecimalUp(-luckySpinWinMoneyOut));//幸运大转盘盈利

                    model.setRewardGrabProfit(MathUtils.toDecimalUp(rewardGrabInMoneyIn));//福利包盈利

                    model.setRewardProfit(MathUtils.toDecimalUp(0d).subtract(rewardMoneyOut));//奖励发放

                    model.setReduceIncreaseProfit(MathUtils.toDecimalUp(reduceMemberMoneyIn).subtract(MathUtils.toDecimalUp(increaseMemberMoneyOut)));//会员调账

                    model.setTotalMoneyIn(MathUtils.toDecimalUp(platformMoneyIn).subtract(MathUtils.toDecimalUp(platformMoneyOut)));//平台盈利项目

                } else if (searcherModel == 2) {
                    model.setPlatformMoneyIn(MathUtils.toDecimalUp(platformMoneyIn));//平台总资金流入

                    model.setNiuFormalitiesMoneyIn(MathUtils.toDecimalUp(niuFormalitiesMoneyIn)); // 牛牛手续费金额
                    model.setNiuPayoutMoneyIn(MathUtils.toDecimalUp(niuPayoutMoneyIn)); // 牛牛内部号获赔金额
                    model.setNiuMoneyIn(MathUtils.toDecimalUp(niuFormalitiesMoneyIn).add(MathUtils.toDecimalUp(niuPayoutMoneyIn))); // 牛牛资金流入

                    model.setLeiNoDeathGrabMoneyIn(MathUtils.toDecimalUp(leiNoDeathGrabMoneyIn));// 扫雷免死抢包金额
                    model.setLeiOtherGrabMoneyIn(MathUtils.toDecimalUp(leiOtherGrabMoneyIn));// 扫雷其他内部号抢包金额
                    model.setLeiGrabMoneyIn(MathUtils.toDecimalUp(leiNoDeathGrabMoneyIn).add(MathUtils.toDecimalUp(leiOtherGrabMoneyIn)));// 扫雷内部号抢包金额

                    model.setLeiNoDeathPayoutMoneyIn(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyIn));//扫雷免死内部号获赔金额
                    model.setLeiOtherPayoutMoneyIn(MathUtils.toDecimalUp(leiOtherPayoutMoneyIn));//扫雷其他免死内部号获赔金额

                    model.setLeiPayoutMoneyIn(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyIn).add(MathUtils.toDecimalUp(leiOtherPayoutMoneyIn)));// 扫雷会员抢包金额

                    model.setLeiMoneyIn(MathUtils.toDecimalUp(leiNoDeathGrabMoneyIn).add(MathUtils.toDecimalUp(leiOtherGrabMoneyIn))
                            .add(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyIn))
                            .add(MathUtils.toDecimalUp(leiOtherPayoutMoneyIn)));//扫雷流入金额

                    model.setJinGrabMoneyIn(MathUtils.toDecimalUp(jinGrabMoneyIn));// 禁抢内部号抢包金额
                    model.setFruitBetMoneyIn(MathUtils.toDecimalUp(fruitBetMoneyIn));// 水果机会员投注金额
                    model.setRewardGrabInMoneyIn(MathUtils.toDecimalUp(rewardGrabInMoneyIn));// 内部号抢福利包金额
                    model.setReduceMemberMoneyIn(MathUtils.toDecimalUp(reduceMemberMoneyIn)); // 会员调减金额


                } else if (searcherModel == 3) {
                    model.setPlatformMoneyOut(MathUtils.toDecimalUp(platformMoneyOut));//平台总资金流出

                    model.setLeiNoDeathPayoutMoneyOut(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyOut));// 扫雷免死赔付
                    model.setLeiOtherPayoutMoneyOut(MathUtils.toDecimalUp(leiOtherPayoutMoneyOut));// 扫雷其他内部号赔付

                    model.setLeiSumPayoutMoneyOut(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyOut).add(MathUtils.toDecimalUp(leiOtherPayoutMoneyOut)));//扫雷内部号赔付金额

                    model.setLeiPayoutMoneyOut(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyOut).add(MathUtils.toDecimalUp(leiOtherPayoutMoneyOut)));//扫雷赔付
                    model.setLeiGrabMoneyOut(MathUtils.toDecimalUp(leiGrabMoneyOut));//扫雷会员抢包金额

                    model.setLeiMoneyOut(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyOut).add(MathUtils.toDecimalUp(leiOtherPayoutMoneyOut)).add(MathUtils.toDecimalUp(leiGrabMoneyOut)));//扫雷流出金额

                    model.setNiuPayoutMoneyOut(MathUtils.toDecimalUp(niuPayoutMoneyOut));// 牛牛内部号赔付金额

                    model.setJinPayoutOut(MathUtils.toDecimalUp(jinPayoutOut));// 禁抢内部号赔付金额
                    model.setFruitWinMoneyOut(MathUtils.toDecimalUp(fruitWinMoneyOut));// 水果机会员中奖金额
                    model.setRewardGrabMemberMoneyOut(MathUtils.toDecimalUp(rewardGrabMemberMoneyOut));// 会员抢福利包
                    model.setLuckySpinWinMoneyOut(MathUtils.toDecimalUp(luckySpinWinMoneyOut));// 幸运大转盘(流出金额)
                    model.setRewardMoneyOut(rewardMoneyOut);//奖励发放
                    model.setIncreaseMemberMoneyOut(MathUtils.toDecimalUp(increaseMemberMoneyOut)); // 会员调增金额

                }

                list.add(model);
            }
        }

        return list;
    }


}
