package com.fy.service.statistics.service;

import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.Reward.*;
import com.fy.service.statistics.model.dto.RewardQuery;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 奖励金额、人数统计
 * 维度：天 、月 、年
 */
@Service
@Slf4j
public class RewardStatisticsService {

    @Autowired
    EsQueryUtils esQueryUtils;

    /**
     * 所有奖励统计
     *
     * @param param
     * @return
     */
    public List<RewardModel> RewardStatistics(RewardQuery param) {
        SearchResponse Search = null;

        if (param.getIdentification().equals("1")) {
            List<AggregationBuilder> aggs = new ArrayList<>();
            TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
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
            aggregation.subAggregation(AggregationBuilders.sum("grab_reward_money").field("grab_reward_money"));
            aggregation.subAggregation(AggregationBuilders.sum("sumall_32").field("sumall_32"));  //  幸运大转盘

            aggs.add(aggregation);
            String[] fields = new String[]{"owner_code", "create_date", "sumall_19", "sumall_20", "sumall_27", "sumall_28", "sumall_30", "sumall_21", "sumall_22", "sumall_23", "sumall_16", "sumall_43", "sumall_8", "sumall_9", "sumall_31", "sumall_38", "grab_reward_money", "sumall_32"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), aggs, fields);
        } else if (param.getIdentification().equals("2")) {
            List<AggregationBuilder> aggs = new ArrayList<>();
            TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
            aggregation.subAggregation(AggregationBuilders.sum("count_19").field("count_19"));
            aggregation.subAggregation(AggregationBuilders.sum("count_20").field("count_20"));
            aggregation.subAggregation(AggregationBuilders.sum("count_27").field("count_27"));
            aggregation.subAggregation(AggregationBuilders.sum("count_28").field("count_28"));
            aggregation.subAggregation(AggregationBuilders.sum("count_30").field("count_30"));
            aggregation.subAggregation(AggregationBuilders.sum("count_21").field("count_21"));
            aggregation.subAggregation(AggregationBuilders.sum("count_22").field("count_22"));
            aggregation.subAggregation(AggregationBuilders.sum("count_23").field("count_23"));
            aggregation.subAggregation(AggregationBuilders.sum("count_16").field("count_16"));
            aggregation.subAggregation(AggregationBuilders.sum("count_43").field("count_43"));
            aggregation.subAggregation(AggregationBuilders.sum("count_9").field("count_9"));
            aggregation.subAggregation(AggregationBuilders.sum("count_8").field("count_8"));
            aggregation.subAggregation(AggregationBuilders.sum("count_31").field("count_31"));
            aggregation.subAggregation(AggregationBuilders.sum("count_38").field("count_38"));
            aggregation.subAggregation(AggregationBuilders.sum("grab_reward_user_count").field("grab_reward_user_count"));
            aggregation.subAggregation(AggregationBuilders.sum("count_32").field("count_32"));
            aggs.add(aggregation);
            String[] fields = new String[]{"owner_code", "create_date", "count_19", "count_20", "count_27", "count_28", "count_30", "count_21", "count_22", "count_23", "count_16", "count_43", "count_9", "count_8", "count_31", "count_38", "grab_reward_user_count", "count_32"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), aggs, fields);
        }
        List<RewardModel> esResultList = new ArrayList();
        SearchHits hits = Search.getHits();
        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            if (param.getIdentification().equals("1")) {

                for (SearchHit hit : hits) {
                    RewardModel rewardModel = new RewardModel();

                    //充值奖励金额
                    RechargeRewardModel rechargeRewardModel = new RechargeRewardModel();
                    rechargeRewardModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());
                    rechargeRewardModel.setFirst_reward_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_19")));//首充奖励金额
                    rechargeRewardModel.setSecond_reward_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_20")));//二充奖励金额
                    rechargeRewardModel.setInvite_members_first_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_27")));//邀请会员首充奖励金额
                    rechargeRewardModel.setInvite_members_second_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_28")));//邀请会员二充奖励金额
                    rechargeRewardModel.setReward_total_amount(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_19"))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_20")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_27")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_28")))
                    );//充值合计金额
                    rewardModel.setRechargeRewardModel(rechargeRewardModel);

                    //注册奖励金额
                    RegisteredModel registeredModel = new RegisteredModel();
                    registeredModel.setRegistered_login_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_30")));//注册登陆奖励金额
                    registeredModel.setInvitation_registration_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_21")));//邀请注册金额
                    registeredModel.setRegistered_total_amount(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_30"))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_21")))
                    );//注册合计金额
                    rewardModel.setRegisteredModel(registeredModel);

                    //游戏奖励金额
                    GameModel gameModel = new GameModel();
                    gameModel.setFull_package_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_22")));//发包满额奖励金额
                    gameModel.setGrab_package_amountt(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_23")));//抢包满额奖励金额
                    gameModel.setLeopard_straight_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_16")));//豹子顺子奖励金额
                    gameModel.setRescue_fund_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_43")));//赠送救援金额
                    gameModel.setGrab_reward_money(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("grab_reward_money"))); // 领取福利包金额
                    gameModel.setLuck_span_money(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_32"))); // 幸运大转盘

                    gameModel.setGame_total_amount(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_22"))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_23")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_16")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_43")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("grab_reward_money")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_32")))
                    );//游戏合计金额
                    rewardModel.setGameModel(gameModel);

                    //佣金奖励
                    Commission commission = new Commission();
//                    commission.setDirect_commission_amount(
//                    MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_8"))
//                            .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_9")))
//                            .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_31")))
//                            .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_38")))
//                    );//佣金奖励金额
                    commission.setNiu_total_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_31")));//牛牛佣金金额
                    commission.setLei_total_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_9")));//扫雷佣金金额
                    commission.setJin_total_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_38")));//禁抢佣金金额
                    commission.setDirect_commission_amount(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_8")));//直接佣金奖励金额

                    commission.setCommission_total_amount(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_8"))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_9")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_31")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_38")))
                    );//佣金奖励合计金额
                    rewardModel.setCommission(commission);
                    //合计总和统计
                    Sumtotal sumtotal = new Sumtotal();
                    sumtotal.setSum_total_amount(
                            MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_19"))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_20")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_27")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_28")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_30")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_21")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_22")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_23")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_16")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_43")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_8")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_9")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_31")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_38")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("grab_reward_money")))
                                    .add(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("sumall_32")))
                    );//合计总和奖励金额
                    rewardModel.setSumtotal(sumtotal);

                    esResultList.add(rewardModel);
                }

                Terms agg = Search.getAggregations().get("agg");
                if (agg.getBuckets().size() > 0) {
                    RewardModel rewardModel = new RewardModel();
                    Aggregations list = agg.getBuckets().get(0).getAggregations();
                    double sumall_19 = ((Sum) list.get("sumall_19")).getValue();
                    double sumall_20 = ((Sum) list.get("sumall_20")).getValue();
                    double sumall_27 = ((Sum) list.get("sumall_27")).getValue();
                    double sumall_28 = ((Sum) list.get("sumall_28")).getValue();
                    double sumall_30 = ((Sum) list.get("sumall_30")).getValue();
                    double sumall_21 = ((Sum) list.get("sumall_21")).getValue();
                    double sumall_22 = ((Sum) list.get("sumall_22")).getValue();
                    double sumall_23 = ((Sum) list.get("sumall_23")).getValue();
                    double sumall_16 = ((Sum) list.get("sumall_16")).getValue();
                    double sumall_43 = ((Sum) list.get("sumall_43")).getValue();
                    double sumall_8 = ((Sum) list.get("sumall_8")).getValue();
                    double sumall_9 = ((Sum) list.get("sumall_9")).getValue();
                    double sumall_31 = ((Sum) list.get("sumall_31")).getValue();
                    double sumall_38 = ((Sum) list.get("sumall_38")).getValue();
                    double grab_reward_money = ((Sum) list.get("grab_reward_money")).getValue();
                    double sumall_32 = ((Sum) list.get("sumall_32")).getValue();
                    //总和充值奖励金额
                    RechargeRewardModel rechargeRewardModel = new RechargeRewardModel();
                    rechargeRewardModel.setSum_first_reward_amount(MathUtils.toDecimalUp(sumall_19));//总和首充奖励金额
                    rechargeRewardModel.setSum_second_reward_amount(MathUtils.toDecimalUp(sumall_20));//二充奖励金额
                    rechargeRewardModel.setSum_invite_members_first_amount(MathUtils.toDecimalUp(sumall_27));//邀请会员首充奖励金额
                    rechargeRewardModel.setSum_invite_members_second_amount(MathUtils.toDecimalUp(sumall_28)); //邀请会员二充奖励金额
                    rechargeRewardModel.setSum_reward_total_amount(
                            MathUtils.toDecimalUp(sumall_19)
                                    .add(MathUtils.toDecimalUp(sumall_20))
                                    .add(MathUtils.toDecimalUp(sumall_27))
                                    .add(MathUtils.toDecimalUp(sumall_28))
                    );//充值合计金额
                    rewardModel.setRechargeRewardModel(rechargeRewardModel);

                    //注册奖励金额
                    RegisteredModel registeredModel = new RegisteredModel();
                    registeredModel.setSum_registered_login_amount(MathUtils.toDecimalUp(sumall_30));//注册登陆奖励金额
                    registeredModel.setSum_invitation_registration_amount(MathUtils.toDecimalUp(sumall_21));//邀请注册金额
                    registeredModel.setSum_registered_total_amount(
                            MathUtils.toDecimalUp(sumall_30)
                                    .add(MathUtils.toDecimalUp(sumall_21))
                    );//注册合计金额
                    rewardModel.setRegisteredModel(registeredModel);

                    //游戏奖励金额
                    GameModel gameModel = new GameModel();
                    gameModel.setSum_full_package_amount(MathUtils.toDecimalUp(sumall_22));//发包满额奖励金额
                    gameModel.setSum_grab_package_amountt(MathUtils.toDecimalUp(sumall_23));//抢包满额奖励金额
                    gameModel.setSum_leopard_straight_amount(MathUtils.toDecimalUp(sumall_16));//豹子顺子奖励金额
                    gameModel.setSum_rescue_fund_amount(MathUtils.toDecimalUp(sumall_43));//赠送救援金额
                    gameModel.setSum_grab_reward_money(MathUtils.toDecimalUp(grab_reward_money));
                    gameModel.setSum_luck_span_money(MathUtils.toDecimalUp(sumall_32));
                    gameModel.setSum_game_total_amount(
                            MathUtils.toDecimalUp(sumall_22)
                                    .add(MathUtils.toDecimalUp(sumall_23))
                                    .add(MathUtils.toDecimalUp(sumall_16))
                                    .add(MathUtils.toDecimalUp(sumall_43))
                                    .add(MathUtils.toDecimalUp(grab_reward_money))
                                    .add(MathUtils.toDecimalUp(sumall_32))
                    );//游戏合计金额
                    rewardModel.setGameModel(gameModel);

                    //佣金奖励
                    Commission commission = new Commission();
                    commission.setSum_niu_total_amount(MathUtils.toDecimalUp(sumall_31));//总和牛牛佣金金额
                    commission.setSum_lei_total_amount(MathUtils.toDecimalUp(sumall_9));//总和扫雷佣金金额
                    commission.setSum_jin_total_amount(MathUtils.toDecimalUp(sumall_38));//总和禁抢佣金金额
                    commission.setSum_direct_commission_amount(MathUtils.toDecimalUp(sumall_8));//佣金奖励金额
                    commission.setSum_commission_total_amount(
                            MathUtils.toDecimalUp(sumall_8)
                                    .add(MathUtils.toDecimalUp(sumall_9))
                                    .add(MathUtils.toDecimalUp(sumall_31))
                                    .add(MathUtils.toDecimalUp(sumall_38))
                    );//佣金奖励合计金额
                    rewardModel.setCommission(commission);

                    //合计总和统计
                    Sumtotal sumtotal = new Sumtotal();
                    sumtotal.setAll_sum_total_amount(
                            MathUtils.toDecimalUp(sumall_19)
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
                                    .add(MathUtils.toDecimalUp(grab_reward_money))
                                    .add(MathUtils.toDecimalUp(sumall_32))
                    );//合计总和奖励金额
                    rewardModel.setSumtotal(sumtotal);

                    esResultList.add(rewardModel);
                }

            } else if (param.getIdentification().equals("2")) {
                for (SearchHit hit : hits) {
                    RewardModel rewardModel = new RewardModel();
                    //充值奖励人数
                    RechargeRewardModel rechargeRewardModel = new RechargeRewardModel();
                    rechargeRewardModel.setCreate_date(hit.getSourceAsMap().get("create_date").toString());
                    rechargeRewardModel.setFirst_reward_number((Integer) hit.getSourceAsMap().get("count_19"));//首充奖励人数
                    rechargeRewardModel.setSecond_reward_number((Integer) hit.getSourceAsMap().get("count_20"));//二充奖励人数
                    rechargeRewardModel.setInvite_members_first_number((Integer) hit.getSourceAsMap().get("count_27"));//邀请会员首充奖励人数
                    rechargeRewardModel.setInvite_members_second_number((Integer) hit.getSourceAsMap().get("count_28"));//邀请会员二充奖励人数
                    rechargeRewardModel.setReward_total_number(
                            (Integer) hit.getSourceAsMap().get("count_19")
                                    + (Integer) hit.getSourceAsMap().get("count_20")
                                    + (Integer) hit.getSourceAsMap().get("count_27")
                                    + (Integer) hit.getSourceAsMap().get("count_28")
                    );//充值合计人数
                    rewardModel.setRechargeRewardModel(rechargeRewardModel);

                    //注册奖励人数
                    RegisteredModel registeredModel = new RegisteredModel();
                    registeredModel.setRegistered_login_number((Integer) hit.getSourceAsMap().get("count_30"));//注册登陆奖励人数
                    registeredModel.setInvitation_registration_number((Integer) hit.getSourceAsMap().get("count_21"));//邀请注册奖励人数
                    registeredModel.setRegistered_total_number(
                            (Integer) hit.getSourceAsMap().get("count_30")
                                    + (Integer) hit.getSourceAsMap().get("count_21")
                    );//注册合计人数
                    rewardModel.setRegisteredModel(registeredModel);
                    //游戏奖励人数
                    GameModel gameModel = new GameModel();
                    gameModel.setFull_package_number((Integer) hit.getSourceAsMap().get("count_22"));//发包满额奖励人数
                    gameModel.setGrab_package_number((Integer) hit.getSourceAsMap().get("count_23"));//抢包满额奖励人数
                    gameModel.setLeopard_straight_number((Integer) hit.getSourceAsMap().get("count_16"));//豹子顺子奖励人数
                    gameModel.setRescue_fund_number((Integer) hit.getSourceAsMap().get("count_43"));//赠送救援人数
                    gameModel.setGrab_reward_user_count((Integer) hit.getSourceAsMap().get("grab_reward_user_count"));
                    gameModel.setLuck_span_number((Integer) hit.getSourceAsMap().get("count_32"));
                    gameModel.setGame_total_number(
                            (Integer) hit.getSourceAsMap().get("count_22")
                                    + (Integer) hit.getSourceAsMap().get("count_23")
                                    + (Integer) hit.getSourceAsMap().get("count_16")
                                    + (Integer) hit.getSourceAsMap().get("count_43")
                                    + (Integer) hit.getSourceAsMap().get("grab_reward_user_count")
                                    + (Integer) hit.getSourceAsMap().get("count_32")
                    );//游戏合计人数
                    rewardModel.setGameModel(gameModel);
                    //佣金奖励
                    Commission commission = new Commission();
                    commission.setJin_total_number((Integer) hit.getSourceAsMap().get("count_38"));
                    commission.setNiu_total_number((Integer) hit.getSourceAsMap().get("count_31"));
                    commission.setLei_total_number((Integer) hit.getSourceAsMap().get("count_9"));
                    commission.setDirect_commission_number((Integer) hit.getSourceAsMap().get("count_8"));//佣金奖励人数

                    commission.setCommission_total_number(
                            (Integer) hit.getSourceAsMap().get("count_38")
                                    + (Integer) hit.getSourceAsMap().get("count_31")
                                    + (Integer) hit.getSourceAsMap().get("count_9")
                                    + (Integer) hit.getSourceAsMap().get("count_8")
                    );//佣金奖励合计人数
                    rewardModel.setCommission(commission);
                    //合计总和统计
                    Sumtotal sumtotal = new Sumtotal();
                    sumtotal.setSum_total_number(
                            (Integer) hit.getSourceAsMap().get("count_19")
                                    + (Integer) hit.getSourceAsMap().get("count_20")
                                    + (Integer) hit.getSourceAsMap().get("count_27")
                                    + (Integer) hit.getSourceAsMap().get("count_28")
                                    + (Integer) hit.getSourceAsMap().get("count_30")
                                    + (Integer) hit.getSourceAsMap().get("count_21")
                                    + (Integer) hit.getSourceAsMap().get("count_22")
                                    + (Integer) hit.getSourceAsMap().get("count_23")
                                    + (Integer) hit.getSourceAsMap().get("count_16")
                                    + (Integer) hit.getSourceAsMap().get("count_43")
                                    + (Integer) hit.getSourceAsMap().get("count_38")
                                    + (Integer) hit.getSourceAsMap().get("count_31")
                                    + (Integer) hit.getSourceAsMap().get("count_9")
                                    + (Integer) hit.getSourceAsMap().get("count_8")
                                    + (Integer) hit.getSourceAsMap().get("grab_reward_user_count")
                                    + (Integer) hit.getSourceAsMap().get("count_32")
                    );//合计总和奖励人数
                    rewardModel.setSumtotal(sumtotal);

                    esResultList.add(rewardModel);
                }

                Terms agg = Search.getAggregations().get("agg");
                if (agg.getBuckets().size() > 0) {
                    RewardModel rewardModel = new RewardModel();
                    Aggregations list = agg.getBuckets().get(0).getAggregations();
                    int count_19 = ((Double) ((Sum) list.get("count_19")).getValue()).intValue();
                    int count_20 = ((Double) ((Sum) list.get("count_20")).getValue()).intValue();
                    int count_27 = ((Double) ((Sum) list.get("count_27")).getValue()).intValue();
                    int count_28 = ((Double) ((Sum) list.get("count_28")).getValue()).intValue();
                    int count_30 = ((Double) ((Sum) list.get("count_30")).getValue()).intValue();
                    int count_21 = ((Double) ((Sum) list.get("count_21")).getValue()).intValue();
                    int count_22 = ((Double) ((Sum) list.get("count_22")).getValue()).intValue();
                    int count_23 = ((Double) ((Sum) list.get("count_23")).getValue()).intValue();
                    int count_16 = ((Double) ((Sum) list.get("count_16")).getValue()).intValue();
                    int count_43 = ((Double) ((Sum) list.get("count_43")).getValue()).intValue();
                    int count_9 = ((Double) ((Sum) list.get("count_9")).getValue()).intValue();
                    int count_8 = ((Double) ((Sum) list.get("count_8")).getValue()).intValue();
                    int count_31 = ((Double) ((Sum) list.get("count_31")).getValue()).intValue();
                    int count_38 = ((Double) ((Sum) list.get("count_38")).getValue()).intValue();
                    int grab_reward_user_count = ((Double) ((Sum) list.get("grab_reward_user_count")).getValue()).intValue();
                    int count_32 = ((Double) ((Sum) list.get("count_32")).getValue()).intValue();
                    //充值奖励人数
                    RechargeRewardModel rechargeRewardModel = new RechargeRewardModel();
                    rechargeRewardModel.setSum_first_reward_number(count_19);//首充奖励人数
                    rechargeRewardModel.setSum_second_reward_number(count_20);//二充奖励人数
                    rechargeRewardModel.setSum_invite_members_first_number(count_27);//邀请会员首充奖励人数
                    rechargeRewardModel.setSum_invite_members_second_number(count_28);//邀请会员二充奖励人数
                    rechargeRewardModel.setSum_reward_total_number(
                            count_19 + count_20 + count_27 + count_28
                    );//充值合计人数
                    rewardModel.setRechargeRewardModel(rechargeRewardModel);

                    //注册奖励人数
                    RegisteredModel registeredModel = new RegisteredModel();
                    registeredModel.setSum_registered_login_number(count_30);//注册登陆奖励人数
                    registeredModel.setSum_invitation_registration_number(count_21);//邀请注册奖励人数
                    registeredModel.setSum_registered_total_number(count_30 + count_21);//注册合计人数
                    rewardModel.setRegisteredModel(registeredModel);
                    //游戏奖励人数
                    GameModel gameModel = new GameModel();
                    gameModel.setSum_full_package_number(count_22);//发包满额奖励人数
                    gameModel.setSum_grab_package_number(count_23);//抢包满额奖励人数
                    gameModel.setSum_leopard_straight_number(count_16);//豹子顺子奖励人数
                    gameModel.setSum_rescue_fund_number(count_43);//赠送救援人数
                    gameModel.setSum_grab_reward_user_count(grab_reward_user_count);
                    gameModel.setSum_luck_span_number(count_32);
                    gameModel.setSum_game_total_number(
                            count_22 + count_23 + count_16 + count_43 + grab_reward_user_count + count_32
                    );//游戏合计人数
                    rewardModel.setGameModel(gameModel);
                    //佣金奖励
                    Commission commission = new Commission();

                    commission.setSum_jin_total_number(count_38);//总和禁抢佣金人数
                    commission.setSum_lei_total_number(count_9);//总和扫雷佣金人数
                    commission.setSum_niu_total_number(count_31);//总和牛牛佣金人数
                    commission.setSum_direct_commission_number(count_8);//佣金奖励人数
                    commission.setSum_commission_total_number(
                            count_38 + count_9 + count_31 + count_8
                    );//佣金奖励合计人数
                    rewardModel.setCommission(commission);
                    //合计总和统计
                    Sumtotal sumtotal = new Sumtotal();
                    sumtotal.setAll_sum_total_number(
                            count_19 + count_20 + count_27 + count_28 + count_30 + count_21 + count_22 +
                                    count_23 + count_16 + count_43 + count_38 + count_9 + count_31 + count_8
                                    + grab_reward_user_count + count_32
                    );//合计总和奖励人数
                    rewardModel.setSumtotal(sumtotal);
                    esResultList.add(rewardModel);
                }

            }
        }
        return esResultList;
    }


}
