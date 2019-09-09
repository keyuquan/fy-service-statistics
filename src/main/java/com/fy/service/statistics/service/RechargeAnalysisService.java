package com.fy.service.statistics.service;


import org.elasticsearch.search.aggregations.BucketOrder;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.Member.PaymentAnalysis;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.TopHitsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值分析
 */
@Service
@Slf4j
public class RechargeAnalysisService {


    @Autowired
    EsQueryUtils esQueryUtils;
    private TopHitsAggregationBuilder money;


    /**
     *  充值渗透
     * @param param
     * @return
     */
    public List<PaymentAnalysis> RechargePenetration(BaseQuery param) {

        String[] fields = new String[]{"create_date","recharge_member_money","active_member","active_member_arpu","recharge_member","recharge_member_arppu"};
        SearchResponse Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY_MEMBER.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<PaymentAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                PaymentAnalysis member=new PaymentAnalysis();

                double recharge_member_money=(Double) hit.getSourceAsMap().get("recharge_member_money");
                int active_member=(Integer) hit.getSourceAsMap().get("active_member");
                int recharge_member=(Integer) hit.getSourceAsMap().get("recharge_member");


                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                member.setRecharge_member_money(MathUtils.toDecimalUp(recharge_member_money));//充值金额
                //ARPU
                member.setActive_member(active_member);//活跃会员

                if(active_member==0){
                    member.setActive_member_arpu(MathUtils.toDecimalUp(0));//每个活跃会员的平均充值
                }else{
                    member.setActive_member_arpu(MathUtils.toDecimalUp(recharge_member_money/active_member));//每个活跃会员的平均充值
                }
                //ARPPU
                member.setRecharge_member(recharge_member);//充值会员
                if(recharge_member==0){
                    member.setRecharge_member_arppu(MathUtils.toDecimalUp(0));//每个充值会员的平均充值
                }else{
                    member.setRecharge_member_arppu(MathUtils.toDecimalUp(recharge_member_money/recharge_member));//每个充值会员的平均充值
                }
                analysis.add(member);
            }
        }
        return analysis;
    }




    /**
     *  充值排名
     * @param param
     * @return
     */
    public List<PaymentAnalysis> TopUpRanking(BaseQuery param) {
        //全表group by 和 count() 条数
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("user_id").order(BucketOrder.aggregation("money", false));
        aggregation.subAggregation(AggregationBuilders.sum("money").field("money"));
        aggs.add(aggregation);

        String[] fields = new String[]{"user_id","mobile","nick","money"};
        //top10
        SearchResponse  Search = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_RECHARGE_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, null, null, fields, 1, 10);
        SearchResponse Search1 = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_RECHARGE_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<PaymentAnalysis> analysis = new ArrayList();
        List<PaymentAnalysis> analysis_sum = new ArrayList();
        List<PaymentAnalysis> analysis_all = new ArrayList();
        SearchHits hits = Search1.getHits();
        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                PaymentAnalysis member=new PaymentAnalysis();
                member.setUser_id(hit.getSourceAsMap().get("user_id").toString());
                member.setUser_account(hit.getSourceAsMap().get("mobile").toString());
                member.setUser_name(hit.getSourceAsMap().get("nick").toString());
                member.setRecharge_amount_money(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("money")));
                analysis_all.add(member);
            }
        }
        //求和并排序  取top10
        Terms agg = Search.getAggregations().get("agg");
        if (agg.getBuckets().size() > 0) {
            for (Terms.Bucket entry : agg.getBuckets()) {
                PaymentAnalysis member=new PaymentAnalysis();
                String key=entry.getKey().toString();
                double money =((Sum)entry.getAggregations().get("money")).getValue();
                member.setUser_id(key);
                member.setRecharge_amount_money(MathUtils.toDecimalUp(money));
                analysis_sum.add(member);
            }
        }
        int a =1;
        for(PaymentAnalysis suma:analysis_sum){
            int b =1;
            for(PaymentAnalysis all:analysis_all ){
                if(b==1){
                    if(suma.getUser_id().equals(all.getUser_id())){
                        PaymentAnalysis member=new PaymentAnalysis();
                        member.setTop_up_ranking(a);
                        member.setUser_id(all.getUser_id());
                        member.setUser_account(all.getUser_account());
                        member.setUser_name(all.getUser_name());
                        member.setRecharge_amount_money(suma.getRecharge_amount_money());
                        analysis.add(member);
                        b++;
                    }
                }
            }
            a++;
        }
        return analysis;
    }









}
