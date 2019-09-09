package com.fy.service.statistics.service;


import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.model.dto.PlatformProfitQuery;
import com.fy.service.statistics.model.increase.PlatformIncreaseDayModel;
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
public class PlatformIncreaseService {
    @Autowired
    EsQueryUtils esQueryUtils;

    /**
     * 平台盈利
     *
     * @param param
     * @return
     */
    public List<PlatformIncreaseDayModel> PlatformIncreaseProfit(BaseQuery param) {
        // 1. 需要查询的信息
        List<PlatformIncreaseDayModel> list = new ArrayList();

        //  2.查询
        String[] fields = new String[]{"reduce_member_money_In", "increase_member_money_out", "reduce_in_money", "increase_in_money"};
        SearchResponse results = esQueryUtils.getSearchResponse(AppCode.REDBONUS.getValue(), EventCode.ST_DAY.getValue(), Constant.USER_FLAG_REAL, param.getBeginTime(), param.getEndTime(), null, fields);

        //  3.组装查询结果
        SearchHit[] searchHits = results.getHits().getHits();

        if ((searchHits != null) && (searchHits.length > 0)) {
            for (SearchHit thisSearchHit : searchHits) {
                Map<String, Object> thisElm = thisSearchHit.getSourceAsMap();
                PlatformIncreaseDayModel model = new PlatformIncreaseDayModel();
                model.setCreateDate((DateUtils.parse(thisElm.get("create_date").toString(), DateUtils.DATE_FULL_FORMAT)));

                // 系统调减
                double reduceMemberMoneyIn = Double.parseDouble(thisElm.get("reduce_member_money_In").toString());
                double increaseMemberMoneyOut = Double.parseDouble(thisElm.get("increase_member_money_out").toString());

                double reduceInMoney = Double.parseDouble(thisElm.get("reduce_in_money").toString());
                double increaseInMoney = Double.parseDouble(thisElm.get("increase_in_money").toString());

                model.setReduceMemberMoneyIn(MathUtils.toDecimalUp(reduceMemberMoneyIn)); // 会员调减金额
                model.setIncreaseMemberMoneyOut(MathUtils.toDecimalUp(increaseMemberMoneyOut)); // 会员调增金额
                model.setReduceIncreaseMemberTotal(MathUtils.toDecimalUp(increaseMemberMoneyOut).subtract(MathUtils.toDecimalUp(reduceMemberMoneyIn)));//会员调账合计
                model.setReduceInMoney(MathUtils.toDecimalUp(reduceInMoney)); // 内部号调减金额
                model.setIncreaseInMoney(MathUtils.toDecimalUp(increaseInMoney)); // 内部号调增金额

                model.setReduceIncreaseInTotal(MathUtils.toDecimalUp(increaseInMoney).subtract(MathUtils.toDecimalUp(reduceInMoney)));//内部号调账合计

                model.setReduceIncreaseTotal(MathUtils.toDecimalUp(increaseInMoney)
                        .add(MathUtils.toDecimalUp(increaseMemberMoneyOut))
                        .subtract(MathUtils.toDecimalUp(reduceInMoney))
                        .subtract(MathUtils.toDecimalUp(reduceMemberMoneyIn)));//系统调账合计


                list.add(model);
            }
        }

        return list;
    }


}
