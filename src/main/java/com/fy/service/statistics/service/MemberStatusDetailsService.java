package com.fy.service.statistics.service;


import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.Member.MemberDetails;
import com.fy.service.statistics.model.Member.MenberCount;
import com.fy.service.statistics.model.dto.ActiveDetailsQuery;
import com.fy.service.statistics.model.dto.MemberDetailsQuery;
import com.fy.service.statistics.model.es.Pagination;
import com.fy.service.statistics.utils.DateUtils;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import com.fy.service.statistics.utils.PageUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员状态明细
 */
@Service
@Slf4j
public class MemberStatusDetailsService {

    @Autowired
    EsQueryUtils esQueryUtils;

    /**
     *  新增会员明细
     * @param param
     * @return
     */
    public MenberCount AddMemberDetails(MemberDetailsQuery param) {

        //  2.查询
        List<TermQueryBuilder> terms = new ArrayList<>();
        if (StringUtils.isNoneBlank(param.getIdentification())) {
            terms.add(QueryBuilders.termQuery("identification", param.getIdentification()));//会员种类标识符
        }
        if (StringUtils.isNoneBlank(param.getUser_id())) {
            terms.add(QueryBuilders.termQuery("user_id", param.getUser_id()));//会员ID
        }
        if (StringUtils.isNoneBlank(param.getNick())) {
            terms.add(QueryBuilders.termQuery("nick", param.getNick()));//会员昵称
        }
        if (StringUtils.isNoneBlank(param.getMobile())) {
            terms.add(QueryBuilders.termQuery("mobile", param.getMobile()));//手机号
        }
        if (StringUtils.isNoneBlank(param.getAgent_flag())) {
            terms.add(QueryBuilders.termQuery("agent_flag", param.getAgent_flag()));//会员类型
        }

        //全表group by 和 count() 条数
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
        aggregation.subAggregation(AggregationBuilders.count("totalCount").field("user_id"));//数据条数
        aggs.add(aggregation);

        String[] fields = new String[]{"user_id","create_time","create_date","nick","invitecode","agent_flag","mobile"};
        //查询一共多少条
        SearchResponse results_before = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), DateUtils.addSecond(param.getEndTime(), 1), aggs, terms, null, fields, 0, 0);
        //分页计算
        Pagination page=PageUtils.Pagination(param.getPage(),param.getSize(),results_before,"agg","totalCount");
        //分页查询数据
        SearchResponse Search = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, null, fields, page.getFrom(), page.getSizeOne());

        List<MemberDetails> member = new ArrayList();
        SearchHits hits = Search.getHits();
        MenberCount menbercount=new MenberCount();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            for (SearchHit hit : hits) {
                MemberDetails details = new MemberDetails();

                details.setUser_id((Integer) hit.getSourceAsMap().get("user_id"));//会员ID
                details.setNick(hit.getSourceAsMap().get("nick").toString());
                details.setMobile(hit.getSourceAsMap().get("mobile").toString());
                details.setInvitecode(hit.getSourceAsMap().get("invitecode").toString());
                details.setAgent_flag(hit.getSourceAsMap().get("agent_flag").toString());
                details.setCreate_time(hit.getSourceAsMap().get("create_time").toString());
                details.setCreate_date(hit.getSourceAsMap().get("create_date").toString());
                member.add(details);
            }
        }
        menbercount.setMemberdetails(member);
        menbercount.setTotalCount(page.getTotalCount());
        return menbercount;
    }



    /**
     *  活跃会员明细
     * @param param
     * @return
     */
    public MenberCount ActiveMemberDetails(ActiveDetailsQuery param) {

        SearchResponse Search =null;

        //  2.查询
        List<TermQueryBuilder> terms = new ArrayList<>();

        if(param.getClassification().equals("1")){
            if (StringUtils.isNoneBlank(param.getIdentification())) {
                terms.add(QueryBuilders.termQuery("identification", param.getIdentification()));//会员种类标识符
            }
            if (StringUtils.isNoneBlank(param.getUser_id())) {
                terms.add(QueryBuilders.termQuery("user_id", param.getUser_id()));//会员ID
            }
            if (StringUtils.isNoneBlank(param.getNick())) {
                terms.add(QueryBuilders.termQuery("nick", param.getNick()));//会员昵称
            }
            if (StringUtils.isNoneBlank(param.getMobile())) {
                terms.add(QueryBuilders.termQuery("mobile", param.getMobile()));//手机号
            }
            if (StringUtils.isNoneBlank(param.getAgent_flag())) {
                terms.add(QueryBuilders.termQuery("agent_flag", param.getAgent_flag()));//会员类型
            }
            if (StringUtils.isNoneBlank(param.getRank())) {
                terms.add(QueryBuilders.termQuery("rank", param.getRank()));
            }
        }else if(param.getClassification().equals("2")){
            if (StringUtils.isNoneBlank(param.getUser_id())) {
                terms.add(QueryBuilders.termQuery("user_id", param.getUser_id()));//会员ID
            }
            if (StringUtils.isNoneBlank(param.getNick())) {
                terms.add(QueryBuilders.termQuery("nick", param.getNick()));//会员昵称
            }
            if (StringUtils.isNoneBlank(param.getMobile())) {
                terms.add(QueryBuilders.termQuery("mobile", param.getMobile()));//手机号
            }
            if (StringUtils.isNoneBlank(param.getAgent_flag())) {
                terms.add(QueryBuilders.termQuery("agent_flag", param.getAgent_flag()));//会员类型
            }
            if (StringUtils.isNoneBlank(param.getHour_type())) {
                terms.add(QueryBuilders.termQuery("hour", param.getHour_type()));//会员类型
            }
        }
        //全表group by 和 count() 条数
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
        aggregation.subAggregation(AggregationBuilders.count("totalCount").field("user_id"));//数据条数
        aggs.add(aggregation);

        String[] fields = new String[]{"identification","user_id","create_time","create_date","nick","invitecode","agent_flag","mobile"};
        Pagination page=new Pagination();
        if(param.getClassification().equals("1")){
            //查询一共多少条
            SearchResponse results_before = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), DateUtils.addSecond(param.getEndTime(), 1), aggs, terms, null, fields, 0, 0);
            //分页计算
             page=PageUtils.Pagination(param.getPage(),param.getSize(),results_before,"agg","totalCount");
            //分页查询数据
             Search = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, null, fields, page.getFrom(), page.getSizeOne());
        }else if(param.getClassification().equals("2")){
            //查询一共多少条
            SearchResponse results_before = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS_HOUR.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), DateUtils.addSecond(param.getEndTime(), 1), aggs, terms, null, fields, 0, 0);
            //分页计算
             page=PageUtils.Pagination(param.getPage(),param.getSize(),results_before,"agg","totalCount");
            //分页查询数据
             Search = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS_HOUR.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, null, fields, page.getFrom(), page.getSizeOne());
        }

        List<MemberDetails> member = new ArrayList();
        SearchHits hits = Search.getHits();
        MenberCount menbercount=new MenberCount();
        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            for (SearchHit hit : hits) {
                MemberDetails details = new MemberDetails();

                details.setUser_id((Integer) hit.getSourceAsMap().get("user_id"));//会员ID
                details.setNick(hit.getSourceAsMap().get("nick").toString());//会员昵称
                details.setMobile(hit.getSourceAsMap().get("mobile").toString());//手机号
                details.setInvitecode(hit.getSourceAsMap().get("invitecode").toString());//邀请码
                details.setAgent_flag(hit.getSourceAsMap().get("agent_flag").toString());//会员类型 1.代理 0.普通会员
                details.setCreate_time(hit.getSourceAsMap().get("create_time").toString());//注册时间
                details.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//查询时间

                member.add(details);
            }
        }
        menbercount.setMemberdetails(member);
        menbercount.setTotalCount(page.getTotalCount());
        return menbercount;
    }

    /**
     *  5.有效会员明细/第二次充值会员明细
     *  8.潜在流失会员明细
     *  14.回归会员明细
     *  9.未充值流失用户明细
     *  10.充值流失用户明细
     *  11首充 流失会员明细
     *  12二次充值会员明细
     *  13大于二次充值会员明细
     *  14.回归会员明细
     * 15.次日留存会员
     * 16.3日留存会员
     * 17.7日留存会员
     * 18.14日留存会员
     * 19.30日留存会员
     * @param param
     * @return
     */
    public MenberCount EffectiveMemberDetails(MemberDetailsQuery param) {
        SearchResponse Search =null;
        //  2.查询
        List<TermQueryBuilder> terms = new ArrayList<>();

        if (StringUtils.isNoneBlank(param.getIdentification())) {
            terms.add(QueryBuilders.termQuery("identification", param.getIdentification()));//会员种类标识符
        }
        if (StringUtils.isNoneBlank(param.getUser_id())) {
            terms.add(QueryBuilders.termQuery("user_id", param.getUser_id()));//会员ID
        }
        if (StringUtils.isNoneBlank(param.getNick())) {
            terms.add(QueryBuilders.termQuery("nick", param.getNick()));//会员昵称
        }
        if (StringUtils.isNoneBlank(param.getMobile())) {
            terms.add(QueryBuilders.termQuery("mobile", param.getMobile()));//手机号
        }
        if (StringUtils.isNoneBlank(param.getAgent_flag())) {
            terms.add(QueryBuilders.termQuery("agent_flag", param.getAgent_flag()));//会员类型
        }

        //全表group by 和 count() 条数
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
        aggregation.subAggregation(AggregationBuilders.count("totalCount").field("user_id"));//数据条数
        aggs.add(aggregation);

        String[] fields = new String[]{"identification","user_id","create_time","create_date","nick","invitecode","agent_flag","mobile"};

        //查询一共多少条
        SearchResponse results_before = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), DateUtils.addSecond(param.getEndTime(), 1), aggs, terms, null, fields, 0, 0);
        //分页计算
        Pagination page=PageUtils.Pagination(param.getPage(),param.getSize(),results_before,"agg","totalCount");
        //分页查询数据
        Search = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.LTV_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, null, fields, page.getFrom(), page.getSizeOne());

        List<MemberDetails> member = new ArrayList();
        SearchHits hits = Search.getHits();
        MenberCount menbercount=new MenberCount();
        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            for (SearchHit hit : hits) {
                MemberDetails details = new MemberDetails();

                details.setUser_id((Integer) hit.getSourceAsMap().get("user_id"));//会员ID
                details.setNick(hit.getSourceAsMap().get("nick").toString());//会员昵称
                details.setMobile(hit.getSourceAsMap().get("mobile").toString());//手机号
                details.setInvitecode(hit.getSourceAsMap().get("invitecode").toString());//邀请码
                details.setAgent_flag(hit.getSourceAsMap().get("agent_flag").toString());//会员类型 1.代理 0.普通会员
                details.setCreate_time(hit.getSourceAsMap().get("create_time").toString());//注册时间
                details.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//查询时间

                member.add(details);
            }
        }
        menbercount.setMemberdetails(member);
        menbercount.setTotalCount(page.getTotalCount());
        return menbercount;
    }

    /**
     * 会员充值明细
     * @param param
     * @return
     */
    public MenberCount MemberRechargeDetails(MemberDetailsQuery param) {
        SearchResponse Search =null;
        //  2.查询
        List<TermQueryBuilder> terms = new ArrayList<>();

        if (StringUtils.isNoneBlank(param.getUser_id())) {
            terms.add(QueryBuilders.termQuery("user_id", param.getUser_id()));//会员ID
        }
        if (StringUtils.isNoneBlank(param.getNick())) {
            terms.add(QueryBuilders.termQuery("nick", param.getNick()));//会员昵称
        }
        if (StringUtils.isNoneBlank(param.getMobile())) {
            terms.add(QueryBuilders.termQuery("mobile", param.getMobile()));//手机号
        }
        if (StringUtils.isNoneBlank(param.getAgent_flag())) {
            terms.add(QueryBuilders.termQuery("agent_flag", param.getAgent_flag()));//会员类型
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
            RangeQueryBuilder amountRangeQuery = QueryBuilders.rangeQuery("money");
            amountRangeQuery.from(min);
            amountRangeQuery.to(max);
            ranges.add(amountRangeQuery);
        }

        //全表group by 和 count() 条数
        List<AggregationBuilder> aggs = new ArrayList<>();
        TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
        aggregation.subAggregation(AggregationBuilders.count("totalCount").field("user_id"));//数据条数
        aggs.add(aggregation);

        String[] fields = new String[]{"user_id","create_time","create_date","nick","invitecode","mobile","agent_flag","money"};
        //查询一共多少条
        SearchResponse results_before = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_RECHARGE_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), DateUtils.addSecond(param.getEndTime(), 1), aggs, terms, ranges, fields, 0, 0);
        //分页计算
        Pagination page=PageUtils.Pagination(param.getPage(),param.getSize(),results_before,"agg","totalCount");
        //分页查询数据
        Search = esQueryUtils.getDetailsSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_RECHARGE_DETAILS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), aggs, terms, ranges, fields, page.getFrom(), page.getSizeOne());

        List<MemberDetails> member = new ArrayList();
        SearchHits hits = Search.getHits();
        MenberCount menbercount=new MenberCount();
        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            for (SearchHit hit : hits) {
                MemberDetails details = new MemberDetails();

                details.setUser_id((Integer) hit.getSourceAsMap().get("user_id"));//会员ID
                details.setNick(hit.getSourceAsMap().get("nick").toString());//会员昵称
                details.setMobile(hit.getSourceAsMap().get("mobile").toString());//手机号
                details.setInvitecode(hit.getSourceAsMap().get("invitecode").toString());//邀请码
                details.setAgent_flag(hit.getSourceAsMap().get("agent_flag").toString());//会员类型 1.代理 0.普通会员
                details.setCreate_time(hit.getSourceAsMap().get("create_time").toString());//注册时间
                details.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//查询时间
                details.setMoney(MathUtils.toDecimalUp((Double) hit.getSourceAsMap().get("money")));//充值金额

                member.add(details);
            }
        }
        menbercount.setMemberdetails(member);
        menbercount.setTotalCount(page.getTotalCount());
        return menbercount;
    }




}
