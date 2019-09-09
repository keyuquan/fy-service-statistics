package com.fy.service.statistics.service;

import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.RechargeDetailsQuery;
import com.fy.service.statistics.model.dto.RechargeQuery;
import com.fy.service.statistics.model.recharge.ReChargeDetailAllModel;
import com.fy.service.statistics.model.recharge.RechargeCashModel;
import com.fy.service.statistics.model.recharge.RechargeDetailsMode;
import com.fy.service.statistics.utils.DateUtils;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.ParsedCardinality;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * 充值提现统计
 */
@Service
@Slf4j
public class RechargeCashStatisticService {
    @Autowired
    EsQueryUtils esQueryUtils;

    public List<RechargeCashModel> rechargeCashStatistic(RechargeQuery param) {
        // 1. 需要查询的信息
        List<RechargeCashModel> list = new ArrayList();

        //  2.查询
        String[] fields = new String[]{"create_date", "owner_code", "recharge_money", "recharge_user_count", "recharge_money_1", "recharge_user_count_1", "recharge_money_2", "recharge_user_count_2", "recharge_money_3", "recharge_user_count_3", "first_recharge_money", "first_recharge_count", "second_recharge_money", "second_recharge_count", "cash_money", "cash_user_count"};
        SearchResponse results = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), null, fields);

        //  3.组装查询结果
        SearchHit[] searchHits = results.getHits().getHits();
        if ((searchHits != null) && (searchHits.length > 0)) {
            for (SearchHit thisSearchHit : searchHits) {
                Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();
                RechargeCashModel model = new RechargeCashModel();
                model.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));

                model.setRechargeMoney(MathUtils.toDecimalUp(thisElm.get("recharge_money").toString()));
                model.setRechargeUserCount(Integer.valueOf(thisElm.get("recharge_user_count").toString()));

                model.setRechargeMoneyThird(MathUtils.toDecimalUp(thisElm.get("recharge_money_1").toString()));
                model.setRechargeUserCountThird(Integer.valueOf(thisElm.get("recharge_user_count_1").toString()));

                model.setRechargeMoneyBank(MathUtils.toDecimalUp(thisElm.get("recharge_money_2").toString()));
                model.setRechargeUserCountBank(Integer.valueOf(thisElm.get("recharge_user_count_2").toString()));

                model.setRechargeMoneyMan(MathUtils.toDecimalUp(thisElm.get("recharge_money_3").toString()));
                model.setRechargeUserCountMan(Integer.valueOf(thisElm.get("recharge_user_count_3").toString()));

                model.setFirstRechargeMoney(MathUtils.toDecimalUp(thisElm.get("first_recharge_money").toString()));
                model.setFirstRechargeCount(Integer.valueOf(thisElm.get("first_recharge_count").toString()));

                model.setSecondRechargeMoney(MathUtils.toDecimalUp(thisElm.get("second_recharge_money").toString()));
                model.setSecondRechargeCount(Integer.valueOf(thisElm.get("second_recharge_count").toString()));

                model.setCashMoney(MathUtils.toDecimalUp(thisElm.get("cash_money").toString()));
                model.setCashCount(Integer.valueOf(thisElm.get("cash_user_count").toString()));

                list.add(model);
            }
        }

        return list;
    }


    /**
     * 充值明细查询
     *
     * @param param
     * @return
     */
    public ReChargeDetailAllModel rechargeDetails(RechargeDetailsQuery param) {
        // 1. 需要查询的信息
        ReChargeDetailAllModel reChargeAllModel = new ReChargeDetailAllModel();
        List<RechargeDetailsMode> ModelList = new ArrayList();

        //  2.查询
        List<TermQueryBuilder> terms = new ArrayList<>();
        if (param.getUserId() != null) {
            terms.add(QueryBuilders.termQuery("user_id", param.getUserId()));
        }
        if (StringUtils.isNoneBlank(param.getUserName())) {
            terms.add(QueryBuilders.termQuery("nick", param.getUserName()));
        }
        if (StringUtils.isNoneBlank(param.getAccount())) {
            terms.add(QueryBuilders.termQuery("mobile", param.getAccount()));
        }
        //  充值金额
        List<RangeQueryBuilder> ranges = new ArrayList<>();
        Integer minP = param.getRechargeMoneyMin();
        Integer maxP = param.getRechargeMoneyMax();
        if (minP != null || maxP != null) {
            Integer min = 0;
            Integer max = Integer.MAX_VALUE;
            if (minP != null) {
                min = minP;
            }
            if (maxP != null) {
                max = maxP;
            }
            RangeQueryBuilder amountRangeQuery = QueryBuilders.rangeQuery("amount");
            amountRangeQuery.from(min);
            amountRangeQuery.to(max);
            ranges.add(amountRangeQuery);
        }

        // 首充 二充
        Integer rank = param.getRank();
        if (rank == 1 || rank == 2) {
            terms.add(QueryBuilders.termQuery("rank", rank));
        }

        String[] fields = new String[]{"user_id", "nick", "mobile", "invitecode", "amount", "rank"};

        //  全部数据
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
        aggregation.subAggregation(AggregationBuilders.cardinality("totalCount").field("user_id"));//数据条数
        aggs.add(aggregation);


        //  查询条数  为分页做准备
        Long firstRechargeCount = 0l;
        Long secondRechargeCount = 0l;
        Long totalCount = 0l;

        SearchResponse results_before = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.DETAILS_RECHARGE.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), DateUtils.addSecond(param.getEndTime(), 1), aggs, terms, ranges, fields, 0, 0);
        Terms agg = results_before.getAggregations().get("agg");

        if (agg.getBuckets().size() > 0) {
            Aggregations list = agg.getBuckets().get(0).getAggregations();
            totalCount = ((ParsedCardinality) list.get("totalCount")).getValue();
            if (rank == 1) {
                firstRechargeCount = totalCount;
            } else if (rank == 2) {
                secondRechargeCount = totalCount;
            }
        }


        //  根据数据条数 和 参数  求取分页的   from 和 size
        Integer page = param.getPage();
        Integer size = param.getSize();
        if (page == null) {
            page = 1;
        }
        if (size > totalCount) {
            page = 1;
        }

        if (size == null) {
            size = 10;
        }

        Integer from = 0;
        if (page > 1) {
            from = (page - 1) * size;
        }

        Integer sizeOne = size;
        if (totalCount < page * size) {
            sizeOne = (int) (size - ((page * size) - totalCount));
        }


        //  查询数据
        if (rank == 1 || rank == 2) {
            //  首充 或者 二充 从es 开始分页
            SearchResponse results = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.DETAILS_RECHARGE.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, ranges, fields, from, sizeOne);
            //  3.组装查询结果
            SearchHit[] searchHits = results.getHits().getHits();
            if ((searchHits != null) && (searchHits.length > 0)) {
                for (SearchHit thisSearchHit : searchHits) {
                    Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();
                    Integer rankOne = Integer.valueOf(thisElm.get("rank").toString());
                    RechargeDetailsMode model = GetModel(thisElm);
                    if (rankOne == 1) {
                        //  首充
                        model.setFirstRechargeMoney(MathUtils.toDecimalUp(thisElm.get("amount").toString()));
                        model.setSecondRechargeMoney(MathUtils.toDecimalUp(0d));
                    } else if (rankOne == 2) {
                        //  二充
                        model.setFirstRechargeMoney(MathUtils.toDecimalUp(0d));
                        model.setSecondRechargeMoney(MathUtils.toDecimalUp(thisElm.get("amount").toString()));
                    }
                    ModelList.add(model);
                }
            }

        } else if (rank == 0) {
            //  首充 和 二充  从结果集分页 （ 合并的时候 存在数据合并 ，从数据库角度分页，最后结果集 条数 size/2 到 size  ,所以从结果集分页）
            SearchResponse results = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.DETAILS_RECHARGE.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, ranges, fields, 0, 1000000);
            //  3.组装查询结果
            Map<Long, RechargeDetailsMode> map = new HashMap<>();
            SearchHit[] searchHits = results.getHits().getHits();
            if ((searchHits != null) && (searchHits.length > 0)) {
                for (SearchHit thisSearchHit : searchHits) {
                    Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();
                    Integer rankOne = Integer.valueOf(thisElm.get("rank").toString());
                    RechargeDetailsMode model = GetModel(thisElm);
                    Long userId = model.getUserId();
                    if (rankOne == 1) {
                        firstRechargeCount = firstRechargeCount + 1;
                        //  首充
                        RechargeDetailsMode modelMap = map.get(userId);
                        if (modelMap != null) {
                            modelMap.setFirstRechargeMoney(MathUtils.toDecimalUp(thisElm.get("amount").toString()));
                            map.put(userId, modelMap);
                        } else {
                            model.setFirstRechargeMoney(MathUtils.toDecimalUp(thisElm.get("amount").toString()));
                            model.setSecondRechargeMoney(MathUtils.toDecimalUp(0d));
                            map.put(userId, model);
                        }

                    } else if (rankOne == 2) {
                        secondRechargeCount = secondRechargeCount + 1;
                        //  二充
                        RechargeDetailsMode modelMap = map.get(userId);
                        if (modelMap != null) {
                            modelMap.setSecondRechargeMoney(MathUtils.toDecimalUp(thisElm.get("amount").toString()));
                            map.put(userId, modelMap);
                        } else {
                            model.setFirstRechargeMoney(MathUtils.toDecimalUp(0d));
                            model.setSecondRechargeMoney(MathUtils.toDecimalUp(thisElm.get("amount").toString()));
                            map.put(userId, model);
                        }
                    }
                }
            }

            //  获取全部数据
            List<RechargeDetailsMode> ModelListAll = new ArrayList();
            for (RechargeDetailsMode model : map.values()) {
                ModelListAll.add(model);
            }

            //  排序
            Collections.sort(ModelListAll, new Comparator<RechargeDetailsMode>() {
                //  user_id  再次排序 （本来是拍好的  但是 map 给打乱了）
                @Override
                public int compare(RechargeDetailsMode o1, RechargeDetailsMode o2) {
                    return (int) (o2.getUserId() - o1.getUserId());
                }
            });

            //  获取分页的数据
            for (int i = from; i < from + sizeOne; i++) {
                if (i < totalCount) {
                    ModelList.add(ModelListAll.get(i));
                }
            }

        }

        reChargeAllModel.setTotalCount(totalCount);
        reChargeAllModel.setFirstRechargeCount(firstRechargeCount);
        reChargeAllModel.setSecondRechargeCount(secondRechargeCount);
        reChargeAllModel.setRechargeDetailsModes(ModelList);

        return reChargeAllModel;
    }

    public RechargeDetailsMode GetModel(Map<String, Object> thisElm) {
        RechargeDetailsMode model = new RechargeDetailsMode();
        model.setUserId(Long.parseLong(thisElm.get("user_id").toString()));
        model.setAccount(thisElm.get("mobile").toString());
        model.setUserName(thisElm.get("nick").toString());
        model.setInviteCode(thisElm.get("invitecode").toString());
        return model;
    }

}
