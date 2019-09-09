package com.fy.service.statistics.service;


import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.Member.MemberStatusAnalysis;
import com.fy.service.statistics.model.dto.ActiveMemberQuery;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.model.dto.NewMemberQuery;
import com.fy.service.statistics.model.dto.RetainedMemberQuery;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 会员状态分析
 */
@Service
@Slf4j
public class MemberStatusAnalysisService {


    @Autowired
    EsQueryUtils esQueryUtils;

    /**
     *  新增会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> NewMember(BaseQuery param) {

        String[] fields = new String[]{"create_date","new_member","ltv_1","ltv_2","ltv_3","ltv_4","ltv_5","ltv_6","ltv_7","ltv_14","ltv_30","ltv_all_1","ltv_all_2","ltv_all_3","ltv_all_4","ltv_all_5","ltv_all_6","ltv_all_7","ltv_all_14","ltv_all_30","ltv_1_rate","ltv_2_rate","ltv_3_rate","ltv_4_rate","ltv_5_rate","ltv_6_rate","ltv_7_rate","ltv_14_rate","ltv_30_rate"};
        SearchResponse Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.DATA_ANALYSIS.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                MemberStatusAnalysis member=new MemberStatusAnalysis();
                int new_member=(Integer) hit.getSourceAsMap().get("new_member");
                int ltv_1=(Integer) hit.getSourceAsMap().get("ltv_1");
                int ltv_2=(Integer) hit.getSourceAsMap().get("ltv_2");
                int ltv_3=(Integer) hit.getSourceAsMap().get("ltv_3");
                int ltv_4=(Integer) hit.getSourceAsMap().get("ltv_4");
                int ltv_5=(Integer) hit.getSourceAsMap().get("ltv_5");
                int ltv_6=(Integer) hit.getSourceAsMap().get("ltv_6");
                int ltv_7=(Integer) hit.getSourceAsMap().get("ltv_7");
                int ltv_14=(Integer) hit.getSourceAsMap().get("ltv_14");
                int ltv_30=(Integer) hit.getSourceAsMap().get("ltv_30");

                int ltv_all_1=(Integer) hit.getSourceAsMap().get("ltv_all_1");
                int ltv_all_2=(Integer) hit.getSourceAsMap().get("ltv_all_2");
                int ltv_all_3=(Integer) hit.getSourceAsMap().get("ltv_all_3");
                int ltv_all_4=(Integer) hit.getSourceAsMap().get("ltv_all_4");
                int ltv_all_5=(Integer) hit.getSourceAsMap().get("ltv_all_5");
                int ltv_all_6=(Integer) hit.getSourceAsMap().get("ltv_all_6");
                int ltv_all_7=(Integer) hit.getSourceAsMap().get("ltv_all_7");
                int ltv_all_14=(Integer) hit.getSourceAsMap().get("ltv_all_14");
                int ltv_all_30=(Integer) hit.getSourceAsMap().get("ltv_all_30");

                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                //新增会员单日充值趋势
                member.setNew_member(new_member);//新增会员
                member.setLtv_1(ltv_1);//当日新增充值会员
                member.setLtv_2(ltv_2);
                member.setLtv_3(ltv_3);
                member.setLtv_4(ltv_4);
                member.setLtv_5(ltv_5);
                member.setLtv_6(ltv_6);
                member.setLtv_7(ltv_7);
                member.setLtv_14(ltv_14);
                member.setLtv_30(ltv_30);

                member.setLtv_all_1(ltv_all_1);//当日累计充值会员
                member.setLtv_all_2(ltv_all_2);
                member.setLtv_all_3(ltv_all_3);
                member.setLtv_all_4(ltv_all_4);
                member.setLtv_all_5(ltv_all_5);
                member.setLtv_all_6(ltv_all_6);
                member.setLtv_all_7(ltv_all_7);
                member.setLtv_all_14(ltv_all_14);
                member.setLtv_all_30(ltv_all_30);
                if(new_member==0 ){
                    member.setLtv_1_rate("0%");//当日累计充值转化率
                    member.setLtv_2_rate("0%");
                    member.setLtv_3_rate("0%");
                    member.setLtv_4_rate("0%");
                    member.setLtv_5_rate("0%");
                    member.setLtv_6_rate("0%");
                    member.setLtv_7_rate("0%");
                    member.setLtv_14_rate("0%");
                    member.setLtv_30_rate("0%");
                }else{
                    if(ltv_all_1==0){
                        member.setLtv_1_rate("0%");//当日累计充值转化率
                    }else{
                        member.setLtv_1_rate(MathUtils.toDecimalUpone(ltv_all_1/new_member*100)+"%");//当日累计充值转化率
                    }
                    if(ltv_all_2==0){
                        member.setLtv_2_rate("0%");
                    }else{
                        member.setLtv_2_rate(MathUtils.toDecimalUpone(ltv_all_2/new_member*100)+"%");
                    }
                    if(ltv_all_3==0){
                        member.setLtv_3_rate("0%");
                    }else{
                        member.setLtv_3_rate(MathUtils.toDecimalUpone(ltv_all_3/new_member*100)+"%");
                    }
                    if(ltv_all_4==0){
                        member.setLtv_4_rate("0%");
                    }else{
                        member.setLtv_4_rate(MathUtils.toDecimalUpone(ltv_all_4/new_member*100)+"%");
                    }
                    if(ltv_all_5==0){
                        member.setLtv_5_rate("0%");
                    }else{
                        member.setLtv_5_rate(MathUtils.toDecimalUpone(ltv_all_5/new_member*100)+"%");
                    }
                    if(ltv_all_6==0){
                        member.setLtv_6_rate("0%");
                    }else{
                        member.setLtv_6_rate(MathUtils.toDecimalUpone(ltv_all_6/new_member*100)+"%");
                    }
                    if(ltv_all_7==0){
                        member.setLtv_7_rate("0%");
                    }else{
                        member.setLtv_7_rate(MathUtils.toDecimalUpone(ltv_all_7/new_member*100)+"%");
                    }
                    if(ltv_all_14==0){
                        member.setLtv_14_rate("0%");
                    }else{
                        member.setLtv_14_rate(MathUtils.toDecimalUpone(ltv_all_14/new_member*100)+"%");
                    }
                    if(ltv_all_30==0){
                        member.setLtv_30_rate("0%");
                    }else{
                        member.setLtv_30_rate(MathUtils.toDecimalUpone(ltv_all_30/new_member*100)+"%");
                    }
                }
                analysis.add(member);
            }
        }
        return analysis;
    }


    /**
     *  活跃会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> ActiveMember(ActiveMemberQuery param) {

        SearchResponse Search=null;
        if(param.getDimension().equals("1")){//活跃趋势

            String[] fields = new String[]{"create_date","active_member","active_new_member"};
             Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ACTIVE_MEMBER_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);
        }else if(param.getDimension().equals("2")){//分时段

            String[] fields = new String[]{"create_date","active_member_1","active_member_2","active_member_3","active_member_4","active_member_5","active_member_6","active_member_7","active_member_8","active_member_9","active_member_10","active_member_11","active_member_12","active_member_13","active_member_14","active_member_15","active_member_16","active_member_17","active_member_18","active_member_19","active_member_20","active_member_21","active_member_22","active_member_23","active_member_00","active_member_all"};
             Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ACTIVE_MEMBER_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);
        }else if(param.getDimension().equals("3")){//忠诚度

            String[] fields = new String[]{"create_date","active_member_loyalty"};
             Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ACTIVE_MEMBER_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);
        }
        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {

            if(param.getDimension().equals("1")) {     //活跃趋势

                for (SearchHit hit : hits) {
                    MemberStatusAnalysis member = new MemberStatusAnalysis();

                    int active_member=(Integer) hit.getSourceAsMap().get("active_member");
                    int active_new_member=(Integer) hit.getSourceAsMap().get("active_new_member");
                    //新增会员活跃趋势
                    member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    member.setActive_member(active_member);//活跃会员
                    member.setActive_new_member(active_new_member);//活跃的新增会员
                    if(active_member==0){
                        member.setActivity_new_member_rate("0%");//新增会员活跃率
                    }else{
                        if(active_new_member==0){
                            member.setActivity_new_member_rate("0%");//新增会员活跃率
                        }else{
                            member.setActivity_new_member_rate(MathUtils.toDecimalUpone(active_new_member/active_member*100)+"%");//新增会员活跃率
                        }
                    }
                    analysis.add(member);
                }
            }else if(param.getDimension().equals("2")){   //分时段

                for (SearchHit hit : hits) {
                    MemberStatusAnalysis member = new MemberStatusAnalysis();
                    //新增会员活跃趋势
                    member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    member.setActive_member_1((Integer) hit.getSourceAsMap().get("active_member_1"));//活跃会员1点钟活跃人数
                    member.setActive_member_2((Integer) hit.getSourceAsMap().get("active_member_2"));
                    member.setActive_member_3((Integer) hit.getSourceAsMap().get("active_member_3"));
                    member.setActive_member_4((Integer) hit.getSourceAsMap().get("active_member_4"));
                    member.setActive_member_5((Integer) hit.getSourceAsMap().get("active_member_5"));
                    member.setActive_member_6((Integer) hit.getSourceAsMap().get("active_member_6"));
                    member.setActive_member_7((Integer) hit.getSourceAsMap().get("active_member_7"));
                    member.setActive_member_8((Integer) hit.getSourceAsMap().get("active_member_8"));
                    member.setActive_member_9((Integer) hit.getSourceAsMap().get("active_member_9"));
                    member.setActive_member_10((Integer) hit.getSourceAsMap().get("active_member_10"));
                    member.setActive_member_11((Integer) hit.getSourceAsMap().get("active_member_11"));
                    member.setActive_member_12((Integer) hit.getSourceAsMap().get("active_member_12"));
                    member.setActive_member_13((Integer) hit.getSourceAsMap().get("active_member_13"));
                    member.setActive_member_14((Integer) hit.getSourceAsMap().get("active_member_14"));
                    member.setActive_member_15((Integer) hit.getSourceAsMap().get("active_member_15"));
                    member.setActive_member_16((Integer) hit.getSourceAsMap().get("active_member_16"));
                    member.setActive_member_17((Integer) hit.getSourceAsMap().get("active_member_17"));
                    member.setActive_member_18((Integer) hit.getSourceAsMap().get("active_member_18"));
                    member.setActive_member_19((Integer) hit.getSourceAsMap().get("active_member_19"));
                    member.setActive_member_20((Integer) hit.getSourceAsMap().get("active_member_20"));
                    member.setActive_member_21((Integer) hit.getSourceAsMap().get("active_member_21"));
                    member.setActive_member_22((Integer) hit.getSourceAsMap().get("active_member_22"));
                    member.setActive_member_23((Integer) hit.getSourceAsMap().get("active_member_23"));
                    member.setActive_member_00((Integer) hit.getSourceAsMap().get("active_member_00"));
                    member.setActive_member_all((Integer) hit.getSourceAsMap().get("active_member_all"));//合计 0-23点

                    analysis.add(member);
                }
            }else if(param.getDimension().equals("3")){     //忠诚度

                for (SearchHit hit : hits) {
                    MemberStatusAnalysis member = new MemberStatusAnalysis();
                    //新增会员活跃趋势
                    member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                    member.setActive_member_loyalty((Integer) hit.getSourceAsMap().get("active_member_loyalty"));//周登录≥3天的会员

                    analysis.add(member);
                }
            }
        }
        return analysis;
    }

    /**
     *  有效会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> EffectiveMember(BaseQuery param) {

        String[] fields = new String[]{"create_date","total_member","effective_member"};
        SearchResponse Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_TASK_CHAIN_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                MemberStatusAnalysis member=new MemberStatusAnalysis();
                int total_member=(Integer) hit.getSourceAsMap().get("total_member");
                int effective_member=(Integer) hit.getSourceAsMap().get("effective_member");
                //有效会员趋势
                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                member.setTotal_member(total_member);//会员总数
                member.setEffective_member(effective_member);//有效会员
                if(total_member==0){
                    member.setEffective_member_rate("0%");//有效会员率
                }else{
                    if(effective_member==0){
                        member.setEffective_member_rate("0%");//有效会员率
                    }else{
                        member.setEffective_member_rate(MathUtils.toDecimalUpone(effective_member/total_member*100)+"%");//有效会员率
                    }
                }
                analysis.add(member);
            }
        }
        return analysis;
    }

    /**
     *  潜在流失会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> PotentialMemberLoss(BaseQuery param) {

        String[] fields = new String[]{"create_date","active_member","potential_loss_member"};
        SearchResponse Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_TASK_CHAIN_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                MemberStatusAnalysis member=new MemberStatusAnalysis();

                int active_member=(Integer) hit.getSourceAsMap().get("active_member");
                int potential_loss_member=(Integer) hit.getSourceAsMap().get("potential_loss_member");

                //潜在流失会员趋势
                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                member.setActive_member(active_member);//活跃会员
                member.setPotential_loss_member(potential_loss_member);//潜在流失会员
                if(active_member==0){
                    member.setPotential_loss_member_rate("0%");//潜在流失会员率
                }else{
                    if(potential_loss_member==0){
                        member.setPotential_loss_member_rate("0%");//潜在流失会员率
                    }else{
                        member.setPotential_loss_member_rate(MathUtils.toDecimalUpone(potential_loss_member/active_member*100)+"%");//潜在流失会员率
                    }
                }
                analysis.add(member);
            }
        }
        return analysis;
    }



    /**
     *  流失会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> LossMember(NewMemberQuery param) {

        SearchResponse Search=null;

        if(param.getLossmemberdimension().equals("1")){

            String[] fields = new String[]{"create_date","loss_member","not_charged_loss_member"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_TASK_CHAIN_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);
        }else if(param.getLossmemberdimension().equals("2")){

            String[] fields = new String[]{"create_date","loss_member","first_charge_loss_member","second_charge_loss_member","many_charge_loss_member"};
            Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_TASK_CHAIN_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);
        }

        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                MemberStatusAnalysis member=new MemberStatusAnalysis();

                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间

                //充值会员流失趋势
                if(param.getLossmemberdimension().equals("1")){
                    int loss_member=(Integer) hit.getSourceAsMap().get("loss_member");
                    int not_charged_loss_member=(Integer) hit.getSourceAsMap().get("not_charged_loss_member");
                    member.setLoss_member(loss_member);//流失会员
                    member.setNot_charged_loss_member(not_charged_loss_member);//未充值流失的会员
                    if(loss_member==0){
                        member.setNot_charged_loss_member_rate("0%");//未充值流失率
                    }else{
                        if(not_charged_loss_member==0){
                            member.setNot_charged_loss_member_rate("0%");//未充值流失率
                        }else{
                            member.setNot_charged_loss_member_rate(MathUtils.toDecimalUpone(not_charged_loss_member/loss_member*100)+"%");//未充值流失率
                        }
                    }
                }else if(param.getLossmemberdimension().equals("2")){
                    int loss_member=(Integer) hit.getSourceAsMap().get("loss_member");
                    int first_charge_loss_member=(Integer) hit.getSourceAsMap().get("first_charge_loss_member");
                    int second_charge_loss_member=(Integer) hit.getSourceAsMap().get("second_charge_loss_member");
                    int many_charge_loss_member=(Integer) hit.getSourceAsMap().get("many_charge_loss_member");

                    member.setLoss_member(loss_member);//流失会员
                    //首充流失趋势
                    member.setFirst_charge_loss_member(first_charge_loss_member);//首充流失的会员
                    //二充流失趋势
                    member.setSecond_charge_loss_member(second_charge_loss_member);//二充流失的会员
                    //大于二充流失趋势
                    member.setMany_charge_loss_member(many_charge_loss_member);//二充后流失会员
                    if(loss_member==0){
                        member.setFirst_charge_loss_member_rate("0%");//首充流失率
                        member.setSecond_charge_loss_member_rate("0%");//二充流失率
                        member.setMany_charge_loss_member_rate("0%");//二充后流失率
                    }else{
                        if(first_charge_loss_member==0){
                            member.setFirst_charge_loss_member_rate("0%");//首充流失率
                        }else{
                            member.setFirst_charge_loss_member_rate(MathUtils.toDecimalUpone(first_charge_loss_member/loss_member*100)+"%");//首充流失率
                        }
                        if(second_charge_loss_member==0){
                            member.setSecond_charge_loss_member_rate("0%");//二充流失率
                        }else{
                            member.setSecond_charge_loss_member_rate(MathUtils.toDecimalUpone(second_charge_loss_member/loss_member*100)+"%");//二充流失率
                        }
                        if(many_charge_loss_member==0){
                            member.setMany_charge_loss_member_rate("0%");//二充后流失率
                        }else{
                            member.setMany_charge_loss_member_rate(MathUtils.toDecimalUpone(many_charge_loss_member/loss_member*100)+"%");//二充后流失率
                        }
                    }
                }
                analysis.add(member);
            }
        }
        return analysis;
    }




    /**
     *  回归会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> ReturningMember(BaseQuery param) {

        String[] fields = new String[]{"create_date","loss_member","returning_member"};
        SearchResponse Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_TASK_CHAIN_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                MemberStatusAnalysis member=new MemberStatusAnalysis();
                int loss_member=(Integer) hit.getSourceAsMap().get("loss_member");
                int returning_member=(Integer) hit.getSourceAsMap().get("returning_member");
                //回归会员趋势
                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                member.setLoss_member(loss_member);//流失会员
                member.setReturning_member(returning_member);//回归会员
                if(loss_member==0){
                    member.setReturning_member_rate("0%");//回归率
                }else{
                    if(returning_member==0){
                        member.setReturning_member_rate("0%");//回归率
                    }else{
                        member.setReturning_member_rate(MathUtils.toDecimalUpone(returning_member/loss_member*100)+"%");//回归率
                    }
                }
                analysis.add(member);
            }
        }
        return analysis;
    }

    /**
     *  留存会员
     * @param param
     * @return
     */
    public List<MemberStatusAnalysis> RetainedMember(RetainedMemberQuery param) {

        String[] fields = new String[]{"create_date","new_member","retained_member_1","retained_member_3","retained_member_7","retained_member_14","retained_member_30"};
        SearchResponse Search = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.MEMBER_TASK_CHAIN_DAO.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        List<MemberStatusAnalysis> analysis = new ArrayList();
        SearchHits hits = Search.getHits();

        if ((hits.getHits() != null) && (hits.getHits().length > 0)) {
            for (SearchHit hit : hits) {
                MemberStatusAnalysis member=new MemberStatusAnalysis();

                int new_member=(Integer) hit.getSourceAsMap().get("new_member");
                int retained_member_1=(Integer) hit.getSourceAsMap().get("retained_member_1");
                int retained_member_3=(Integer) hit.getSourceAsMap().get("retained_member_3");
                int retained_member_7=(Integer) hit.getSourceAsMap().get("retained_member_7");
                int retained_member_14=(Integer) hit.getSourceAsMap().get("retained_member_14");
                int retained_member_30=(Integer) hit.getSourceAsMap().get("retained_member_30");

                //回归会员趋势
                member.setCreate_date(hit.getSourceAsMap().get("create_date").toString());//时间
                member.setNew_member(new_member);//新增会员
                if(param.getProbability().equals("1")){
                    member.setRetained_member_1(retained_member_1);//次日留存
                    member.setRetained_member_3(retained_member_3);//3日留存
                    member.setRetained_member_7(retained_member_7);//7日留存
                    member.setRetained_member_14(retained_member_14);//14日留存
                    member.setRetained_member_30(retained_member_30);//30日留存
                }else if(param.getProbability().equals("2")){
                    if(new_member==0){
                        member.setRetained_member_1_rate("0%");//次日留存率
                        member.setRetained_member_3_rate("0%");//3日留存率
                        member.setRetained_member_7_rate("0%");//7日留存率
                        member.setRetained_member_14_rate("0%");//14日留存率
                        member.setRetained_member_30_rate("0%");//30日留存率
                    }else{
                        if(retained_member_1==0){
                            member.setRetained_member_1_rate("0%");//次日留存率
                        }else{
                            member.setRetained_member_1_rate(MathUtils.toDecimalUpone(retained_member_1/new_member*100)+"%");//次日留存率
                        }
                        if(retained_member_3==0){
                            member.setRetained_member_3_rate("0%");//3日留存率
                        }else{
                            member.setRetained_member_3_rate(MathUtils.toDecimalUpone(retained_member_3/new_member*100)+"%");//3日留存率
                        }
                        if(retained_member_7==0){
                            member.setRetained_member_7_rate("0%");//7日留存率
                        }else{
                            member.setRetained_member_7_rate(MathUtils.toDecimalUpone(retained_member_7/new_member*100)+"%");//7日留存率
                        }
                        if(retained_member_14==0){
                            member.setRetained_member_14_rate("0%");//14日留存率
                        }else{
                            member.setRetained_member_14_rate(MathUtils.toDecimalUpone(retained_member_14/new_member*100)+"%");//14日留存率
                        }
                        if(retained_member_30==0){
                            member.setRetained_member_30_rate("0%");//30日留存率
                        }else{
                            member.setRetained_member_30_rate(MathUtils.toDecimalUpone(retained_member_30/new_member*100)+"%");//30日留存率
                        }
                    }
                }
                analysis.add(member);
            }
        }
        return analysis;
    }





}
