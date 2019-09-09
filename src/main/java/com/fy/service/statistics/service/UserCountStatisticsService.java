package com.fy.service.statistics.service;

import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.RechargeQuery;
import com.fy.service.statistics.model.user.UserCountModel;
import com.fy.service.statistics.utils.DateUtils;
import com.fy.service.statistics.utils.EsQueryUtils;
import com.fy.service.statistics.utils.MathUtils;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用户数量统计
 */
@Service
@Slf4j
public class UserCountStatisticsService {
    @Autowired
    EsQueryUtils esQueryUtils;

    /**
     * 用户数量统计
     *
     * @returng
     */
    public List<UserCountModel> userCountStatistics(RechargeQuery param) {
        // 1. 需要查询的信息
        List<UserCountModel> list = new ArrayList();

        //  2.查询
        String[] fields = new String[]{"create_date", "owner_code", "regi_user_count", "active_user_count", "regi_user_count_all", "final_balance", "initial_balance", "user_profit", "recharge_money", "cash_money"};
        SearchResponse results = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), param.getUserFlag(), param.getBeginTime(), param.getEndTime(), null, fields);

        //  3.组装查询结果
        SearchHit[] searchHits = results.getHits().getHits();
        if ((searchHits != null) && (searchHits.length > 0)) {
            for (SearchHit thisSearchHit : searchHits) {
                Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();
                UserCountModel model = new UserCountModel();
                model.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));
                model.setUserCount(Integer.valueOf(thisElm.get("regi_user_count").toString()));
                model.setActiveUserCount(Integer.valueOf(thisElm.get("active_user_count").toString()));
                model.setUserCountAll(Integer.valueOf(thisElm.get("regi_user_count_all").toString()));

                model.setFinalBalance(MathUtils.toDecimalUp(thisElm.get("final_balance").toString()));
                model.setInitialBalance(MathUtils.toDecimalUp(thisElm.get("initial_balance").toString()));

                double user_profit = Double.parseDouble(thisElm.get("user_profit").toString());
                double recharge_money = Double.parseDouble(thisElm.get("recharge_money").toString());
                double cash_money = Double.parseDouble(thisElm.get("cash_money").toString());

                model.setUserProfit(MathUtils.toDecimalUp(user_profit)
                        .subtract(MathUtils.toDecimalUp(recharge_money))
                        .add(MathUtils.toDecimalUp(cash_money)));

                list.add(model);
            }
        }

        return list;
    }
}
