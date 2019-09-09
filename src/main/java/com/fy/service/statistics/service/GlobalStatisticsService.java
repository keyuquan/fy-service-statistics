package com.fy.service.statistics.service;

import com.fy.service.component.service.ImService;
import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.model.global.*;
import com.fy.service.statistics.utils.DateUtils;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Max;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * (\__/)
 * ( ^-^)
 * /つ @author carney
 *
 * @date 2019/7/6 9:59
 * 平台统计
 */
@Service
@Slf4j
public class GlobalStatisticsService {

    @Autowired
    EsQueryUtils esQueryUtils;

    @Autowired
    private ImService imService;


    /**
     * 全局统计
     *
     * @returng
     */
    public GlobalModel globalStatistics(BaseQuery param) {

        // 1. 需要查询的信息
        GlobalModel global = new GlobalModel();
        //  顶部 统计
        UserModel memberModel = new UserModel();
        RechargeCashGModel rechargeCashModel = new RechargeCashGModel();
        PlatformProfitModel platformProfitModel = new PlatformProfitModel();

        // 底部四个图 和 今日数据
        UserNewModel userNewModel = new UserNewModel();
        UserActiveModel userActiveModel = new UserActiveModel();
        RechargeModel rechargeModel = new RechargeModel();
        ProfitModel profitModel = new ProfitModel();

        // 2. 需要查询的数据
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");

        //会员统计
        aggregation.subAggregation(AggregationBuilders.max("regi_user_count_all").field("regi_user_count_all"));//累计注册用户数
        aggregation.subAggregation(AggregationBuilders.sum("regi_user_count").field("regi_user_count"));//新注册用户
        aggregation.subAggregation(AggregationBuilders.sum("active_user_count").field("active_user_count"));//活跃用户数

        //充值提现
        aggregation.subAggregation(AggregationBuilders.sum("user_profit").field("user_profit"));//会员盈利
        aggregation.subAggregation(AggregationBuilders.sum("recharge_money").field("recharge_money"));//充值金额
        aggregation.subAggregation(AggregationBuilders.sum("first_recharge_money").field("first_recharge_money"));//首充金额
        aggregation.subAggregation(AggregationBuilders.sum("first_recharge_count").field("first_recharge_count"));//首充次数
        aggregation.subAggregation(AggregationBuilders.sum("second_recharge_money").field("second_recharge_money"));//二充金额
        aggregation.subAggregation(AggregationBuilders.sum("second_recharge_count").field("second_recharge_count"));//二充次数
        aggregation.subAggregation(AggregationBuilders.sum("cash_money").field("cash_money"));//提现

        // 平台盈利
        aggregation.subAggregation(AggregationBuilders.sum("platform_money_in").field("platform_money_in"));
        aggregation.subAggregation(AggregationBuilders.sum("platform_money_out").field("platform_money_out"));

        // 平台盈利-奖励统计
        aggregation.subAggregation(AggregationBuilders.sum("sumall_19").field("sumall_19"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_20").field("sumall_20"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_27").field("sumall_27"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_28").field("sumall_28"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_30").field("sumall_30"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_21").field("sumall_21"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_22").field("sumall_22"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_23").field("sumall_23"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_16").field("sumall_16"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_43").field("sumall_43"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_8").field("sumall_8"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_9").field("sumall_9"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_31").field("sumall_31"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_38").field("sumall_38"));

        aggregation.subAggregation(AggregationBuilders.sum("reward_user_count").field("reward_user_count")); //  奖励人数 不包含福利包
        aggregation.subAggregation(AggregationBuilders.sum("reward_user_count_all").field("reward_user_count_all"));// 奖励人数 包含福利包

        aggregation.subAggregation(AggregationBuilders.sum("recharge_money_1").field("recharge_money_1"));
        aggregation.subAggregation(AggregationBuilders.sum("recharge_money_2").field("recharge_money_2"));
        aggregation.subAggregation(AggregationBuilders.sum("recharge_money_3").field("recharge_money_3"));
        aggregation.subAggregation(AggregationBuilders.sum("sumall_32").field("sumall_32")); // 幸运大转盘 正数
        aggregation.subAggregation(AggregationBuilders.sum("reward_grab_money_out").field("reward_grab_money_out"));
        aggregation.subAggregation(AggregationBuilders.sum("grab_reward_user_count").field("grab_reward_user_count")); //  收取福利包人数


        // 平台盈利- 游戏概况
        aggregation.subAggregation(AggregationBuilders.sum("sumall_40").field("sumall_40")); // 牛牛手续费
        aggregation.subAggregation(AggregationBuilders.sum("niu_payout_money_in").field("niu_payout_money_in"));
        aggregation.subAggregation(AggregationBuilders.sum("niu_payout_money_out").field("niu_payout_money_out"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_death_in").field("lei_payout_money_death_in"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_other_in").field("lei_payout_money_other_in"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_death_out").field("lei_payout_money_death_out"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_payout_money_other_out").field("lei_payout_money_other_out"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_grab_money_death_in").field("lei_grab_money_death_in"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_grab_money_other_in").field("lei_grab_money_other_in"));
        aggregation.subAggregation(AggregationBuilders.sum("lei_grab_money_out").field("lei_grab_money_out"));
        aggregation.subAggregation(AggregationBuilders.sum("jin_grab_money_in").field("jin_grab_money_in"));
        aggregation.subAggregation(AggregationBuilders.sum("jin_payout_money_out").field("jin_payout_money_out"));

        aggregation.subAggregation(AggregationBuilders.sum("sumall_36").field("sumall_36")); // 水果机投注  负数
        aggregation.subAggregation(AggregationBuilders.sum("sumall_37").field("sumall_37")); // 水果机中奖  正数

        // 平台盈利- 调增 调减
        aggregation.subAggregation(AggregationBuilders.sum("increase_member_money_out").field("increase_member_money_out")); // 水果机投注  负数
        aggregation.subAggregation(AggregationBuilders.sum("reduce_member_money_In").field("reduce_member_money_In")); // 水果机中奖  正数

        // 平台盈利- 会员发福利包
        aggregation.subAggregation(AggregationBuilders.sum("reward_grab_money_in").field("reward_grab_money_in"));

        //  3.1  头部 统计数据
        aggs.add(aggregation);
        String[] fields = new String[]{"create_date", "owner_code", "user_flag", "grab_money_1", "regi_user_count_all", "regi_user_count", "active_user_count", "user_profit", "recharge_money", "first_recharge_money", "second_recharge_money", "cash_money", "recharge_money_1", "recharge_money_2", "recharge_money_3", "initial_balance", "final_balance", "platform_money_in", "platform_money_out", "sumall_19", "sumall_20", "sumall_27", "sumall_28", "sumall_30", "sumall_21", "sumall_22", "sumall_23", "sumall_16", "sumall_43", "sumall_8", "count_19", "count_20", "count_27", "count_28", "count_30", "count_21", "count_22", "count_23", "count_16", "count_45", "count_43", "count_8"};
        SearchResponse results = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, fields, "create_date", SortOrder.ASC);

        Terms agg = results.getAggregations().get("agg");
        if (agg.getBuckets().size() > 0) {
            Aggregations list = agg.getBuckets().get(0).getAggregations();

            //充值提现
            double recharge_money = ((Sum) list.get("recharge_money")).getValue();
            double first_recharge_money = ((Sum) list.get("first_recharge_money")).getValue();
            int first_recharge_count = ((Double) ((Sum) list.get("first_recharge_count")).getValue()).intValue();
            double second_recharge_money = ((Sum) list.get("second_recharge_money")).getValue();
            int second_recharge_count = ((Double) ((Sum) list.get("second_recharge_count")).getValue()).intValue();
            double cash_money = ((Sum) list.get("cash_money")).getValue();

            rechargeCashModel.setRechargeMoney(MathUtils.toDecimalUp(recharge_money));
            rechargeCashModel.setFirstRechargeMoney(MathUtils.toDecimalUp(first_recharge_money));
            rechargeCashModel.setFirstRechargeCount(first_recharge_count);
            rechargeCashModel.setSecondRechargeMoney(MathUtils.toDecimalUp(second_recharge_money));
            rechargeCashModel.setSecondRechargeCount(second_recharge_count);
            rechargeCashModel.setCashMoney(MathUtils.toDecimalUp(cash_money));

            //用户统计
            int regi_user_count_all = ((Double) ((Max) list.get("regi_user_count_all")).getValue()).intValue();
            int regi_user_count = ((Double) ((Sum) list.get("regi_user_count")).getValue()).intValue();
            int active_user_count = ((Double) ((Sum) list.get("active_user_count")).getValue()).intValue();
            double user_profit = ((Sum) list.get("user_profit")).getValue();

            memberModel.setUserCountAll(regi_user_count_all);
            memberModel.setUserCount(regi_user_count);
            memberModel.setActiveUserCount(active_user_count);
            memberModel.setOnlineUsersCount(imService.onlineUserInfo().split(",")[4].split(":")[1]);//真实在线用户

            memberModel.setUserProfit(MathUtils.toDecimalUp(user_profit).subtract(MathUtils.toDecimalUp(recharge_money)).add(MathUtils.toDecimalUp(cash_money)));

            // 奖励统计
            double sumall_19 = Math.abs(((Sum) list.get("sumall_19")).getValue());
            double sumall_20 = Math.abs(((Sum) list.get("sumall_20")).getValue());
            double sumall_27 = Math.abs(((Sum) list.get("sumall_27")).getValue());
            double sumall_28 = Math.abs(((Sum) list.get("sumall_28")).getValue());
            double sumall_30 = Math.abs(((Sum) list.get("sumall_30")).getValue());
            double sumall_21 = Math.abs(((Sum) list.get("sumall_21")).getValue());
            double sumall_22 = Math.abs(((Sum) list.get("sumall_22")).getValue());
            double sumall_23 = Math.abs(((Sum) list.get("sumall_23")).getValue());
            double sumall_16 = Math.abs(((Sum) list.get("sumall_16")).getValue());
            double sumall_43 = Math.abs(((Sum) list.get("sumall_43")).getValue());
            double sumall_8 = Math.abs(((Sum) list.get("sumall_8")).getValue());
            double sumall_31 = Math.abs(((Sum) list.get("sumall_31")).getValue());
            double sumall_9 = Math.abs(((Sum) list.get("sumall_9")).getValue());
            double sumall_38 = Math.abs(((Sum) list.get("sumall_38")).getValue());
            double reward_grab_money_out = Math.abs(((Sum) list.get("reward_grab_money_out")).getValue());
            double sumall_32 = ((Sum) list.get("sumall_32")).getValue();   //  幸运大转盘

            platformProfitModel.setReWardProfit(MathUtils.toDecimalUp(sumall_19)
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
                    .add(MathUtils.toDecimalUp(sumall_31))
                    .add(MathUtils.toDecimalUp(sumall_9))
                    .add(MathUtils.toDecimalUp(sumall_38))
                    .add(MathUtils.toDecimalUp(reward_grab_money_out))
                    .add(MathUtils.toDecimalUp(sumall_32)));

            // 游戏统计
            //  牛牛
            double niuFormalitiesMoneyIn = Math.abs(((Sum) list.get("sumall_40")).getValue());
            double niuPayoutMoneyIn = ((Sum) list.get("niu_payout_money_in")).getValue();
            double niuPayoutMoneyOut = ((Sum) list.get("niu_payout_money_out")).getValue();

            //  扫雷相关字段
            // 赔付相关
            double leiNoDeathPayoutMoneyIn = ((Sum) list.get("lei_payout_money_death_in")).getValue();
            double leiOtherPayoutMoneyIn = ((Sum) list.get("lei_payout_money_other_in")).getValue();
            double leiNoDeathPayoutMoneyOut = ((Sum) list.get("lei_payout_money_death_out")).getValue();
            double leiOtherPayoutMoneyOut = ((Sum) list.get("lei_payout_money_other_out")).getValue();

            // 抢包相关
            double leiNoDeathGrabMoneyIn = ((Sum) list.get("lei_grab_money_death_in")).getValue();
            double leiOtherGrabMoneyIn = ((Sum) list.get("lei_grab_money_other_in")).getValue();
            double leiGrabMoneyOut = ((Sum) list.get("lei_grab_money_out")).getValue();

            //  禁抢相关字段
            double jinGrabMoneyIn = ((Sum) list.get("jin_grab_money_in")).getValue();
            double jinPayoutOut = ((Sum) list.get("jin_payout_money_out")).getValue();

            //  水果机
            double fruitBetMoneyIn = Math.abs(((Sum) list.get("sumall_36")).getValue());
            double fruitWinMoneyOut = Math.abs(((Sum) list.get("sumall_37")).getValue());

            platformProfitModel.setGameProfit(
                    MathUtils.toDecimalUp(niuFormalitiesMoneyIn)
                            .add(MathUtils.toDecimalUp(niuPayoutMoneyIn))
                            .subtract(MathUtils.toDecimalUp(niuPayoutMoneyOut))
                            .add(MathUtils.toDecimalUp(jinGrabMoneyIn))
                            .subtract(MathUtils.toDecimalUp(jinPayoutOut))
                            .add(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyIn))
                            .add(MathUtils.toDecimalUp(leiOtherPayoutMoneyIn))
                            .add(MathUtils.toDecimalUp(leiNoDeathGrabMoneyIn))
                            .add(MathUtils.toDecimalUp(leiOtherGrabMoneyIn))
                            .subtract(MathUtils.toDecimalUp(leiNoDeathPayoutMoneyOut))
                            .subtract(MathUtils.toDecimalUp(leiOtherPayoutMoneyOut))
                            .subtract(MathUtils.toDecimalUp(leiGrabMoneyOut))
                            .add(MathUtils.toDecimalUp(fruitBetMoneyIn))
                            .subtract(MathUtils.toDecimalUp(fruitWinMoneyOut))

            );//游戏 合计

            //  系统调账
            double increase_member_money_out = Math.abs(((Sum) list.get("increase_member_money_out")).getValue());
            double reduce_member_money_In = Math.abs(((Sum) list.get("reduce_member_money_In")).getValue());
            platformProfitModel.setReduceIncreaseProfit(MathUtils.toDecimalUp(reduce_member_money_In).subtract(MathUtils.toDecimalUp(increase_member_money_out)));

            //  会员发福利包
            double reward_grab_money_in = Math.abs(((Sum) list.get("reward_grab_money_in")).getValue());
            platformProfitModel.setReWardRedBonusProfit(MathUtils.toDecimalUp(reward_grab_money_in));

            // 平台盈利
            double platformMoneyIn = ((Sum) list.get("platform_money_in")).getValue();
            double platformMoneyOut = ((Sum) list.get("platform_money_out")).getValue();
            platformProfitModel.setPlatformProfit(MathUtils.toDecimalUp(platformMoneyIn).subtract(MathUtils.toDecimalUp(platformMoneyOut)));
        }

        //  3.3  底部图形数据
        String beginTime = param.getBeginTime();
        String endTime = param.getEndTime();
        if (DateUtils.getDaysByCompare(beginTime, endTime) <= 1) {
            beginTime = DateUtils.addDay(endTime, -8);
        }
        //  底部 新增用户的明细 活跃用户的明细 支付的汇总
        SearchResponse results_details = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), Constant.USER_FLAG_REAL, beginTime, endTime, aggs, fields, "create_date", SortOrder.ASC);
        Terms agg_details = results.getAggregations().get("agg");
        if (agg_details.getBuckets().size() > 0) {
            Aggregations list = agg_details.getBuckets().get(0).getAggregations();
            int regi_user_count = ((Double) ((Sum) list.get("regi_user_count")).getValue()).intValue();
            int active_user_count = ((Double) ((Sum) list.get("active_user_count")).getValue()).intValue();

            userNewModel.setUserCount(regi_user_count);
            userActiveModel.setUserActive(active_user_count);

            double recharge_money = ((Sum) list.get("recharge_money")).getValue();
            double recharge_money_1 = ((Sum) list.get("recharge_money_1")).getValue();
            double recharge_money_2 = ((Sum) list.get("recharge_money_2")).getValue();
            double recharge_money_3 = ((Sum) list.get("recharge_money_3")).getValue();
            double platformMoneyIn = ((Sum) list.get("platform_money_in")).getValue();
            double platformMoneyOut = ((Sum) list.get("platform_money_out")).getValue();

            rechargeModel.setRechargeMoney(MathUtils.toDecimalUp(recharge_money));
            rechargeModel.setRechargeMoneyThird(MathUtils.toDecimalUp(recharge_money_1));
            rechargeModel.setRechargeMoneyBank(MathUtils.toDecimalUp(recharge_money_2));
            rechargeModel.setRechargeMoneyMan(MathUtils.toDecimalUp(recharge_money_3));
            profitModel.setProfit(MathUtils.toDecimalUp(platformMoneyIn - platformMoneyOut));

        }

        //  底部 新增用户的明细 活跃用户的明细 支付的明细
        SearchHit[] searchHits = results_details.getHits().getHits();
        List<UserDayModel> listUserNew = new ArrayList();
        List<UserDayModel> listUserActive = new ArrayList();
        List<RechargeDayModel> listRecharge = new ArrayList();
        List<ProfitDayModel> listProfit = new ArrayList();

        if ((searchHits != null) && (searchHits.length > 0)) {
            for (int i = 0; i < searchHits.length; i++) {

                SearchHit thisSearchHit = searchHits[i];
                Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();

                UserDayModel userDayModel = new UserDayModel();
                UserDayModel userActiveDayModel = new UserDayModel();
                RechargeDayModel rechargeDayModel = new RechargeDayModel();

                userDayModel.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));
                userDayModel.setUserCount(Integer.valueOf(thisElm.get("regi_user_count").toString()));
                listUserNew.add(userDayModel);

                userActiveDayModel.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));
                userActiveDayModel.setUserCount(Integer.valueOf(thisElm.get("active_user_count").toString()));
                listUserActive.add(userActiveDayModel);

                rechargeDayModel.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));
                rechargeDayModel.setRechargeMoney(MathUtils.toDecimalUp(thisElm.get("recharge_money").toString()));
                rechargeDayModel.setRechargeMoneyThird(MathUtils.toDecimalUp(thisElm.get("recharge_money_1").toString()));
                rechargeDayModel.setRechargeMoneyBank(MathUtils.toDecimalUp(thisElm.get("recharge_money_2").toString()));
                rechargeDayModel.setRechargeMoneyMan(MathUtils.toDecimalUp(thisElm.get("recharge_money_3").toString()));
                listRecharge.add(rechargeDayModel);

                // 平台盈利
                ProfitDayModel profitDayModel = new ProfitDayModel();
                profitDayModel.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));
                double platformMoneyIn = Double.parseDouble(thisElm.get("platform_money_in").toString());
                double platformMoneyOut = Double.parseDouble(thisElm.get("platform_money_out").toString());
                profitDayModel.setProfit(MathUtils.toDecimalUp(platformMoneyIn - platformMoneyOut));
                listProfit.add(profitDayModel);

                //  期初余额
                if (i == 0) {
                    memberModel.setInitialBalance(MathUtils.toDecimalUp(thisElm.get("initial_balance").toString()));
                }
                // 期末余额
                if (i == searchHits.length - 1) {
                    memberModel.setFinalBalance(MathUtils.toDecimalUp(thisElm.get("final_balance").toString()));
                }

            }
        }

        // 新增用户的明细
        userNewModel.setUserDayModels(listUserNew);
        // 活跃用户的明细
        userActiveModel.setUserDayModels(listUserActive);
        // 支付的明细
        rechargeModel.setRechargeDayModels(listRecharge);
        // 今日平台盈利
        profitModel.setProfitDayModels(listProfit);

        global.setMemberModel(memberModel);
        global.setRechargeCashModel(rechargeCashModel);
        global.setPlatformProfitModel(platformProfitModel);
        global.setUserNewModel(userNewModel);
        global.setUserActiveModel(userActiveModel);
        global.setRechargeModel(rechargeModel);
        global.setProfitModel(profitModel);

        return global;
    }

}
