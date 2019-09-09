package com.fy.service.statistics.controller;

import com.fy.framework.common.model.R;
import com.fy.framework.component.config.TenantContextHolder;
import com.fy.service.common.web.controller.BaseController;
import com.fy.service.statistics.enums.AppCode;
import com.fy.service.statistics.enums.EventCode;
import com.fy.service.statistics.model.dto.BaseQuery;
import com.fy.service.statistics.utils.DateUtils;
import com.fy.service.statistics.utils.RouteUtils;
import com.google.gson.Gson;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.bucket.terms.TermsAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.Sum;
import org.elasticsearch.search.aggregations.metrics.SumAggregationBuilder;
import org.elasticsearch.search.aggregations.metrics.TopHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping({"/demo"})
@Api(value = "平台统计查询接口")
public class DemoStatisticsController extends BaseController {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    @ApiOperation("查询平台统计")
    @PostMapping(value = "/statistics")
    public R<Object> statistics(@RequestBody BaseQuery param) {
    	System.out.println("tenant:"+TenantContextHolder.getTenant());
    	
		/*PfUser selectById = pfUserService.selectById(1);
		
		selectById.setLastUpdateTime(new Date());
		
		boolean insertOrUpdate = pfUserService.insertOrUpdate(selectById);
		
		PfUser  pf = new PfUser();
		pf.setName(System.currentTimeMillis()+"");
		pf.setPassword("123456");
		pfUserService.insert(pf);*/
    	
        return R.success(param);
    }

    @RequestMapping("/where")
    public String test1() {
        try {
            //  select owner_code,create_date,recharge_user_count  from  st_redbonus where  create_date >="2019-06-06 00:00:00" and  create_date < "2019-07-08 00:00:00"
            String event_code = EventCode.ST_DAY.getValue();
            String owner_code = "pig";
            String beginTime = "2019-06-06 00:00:00";
            String endTime = "2019-07-08 00:00:00";

            String indexs = RouteUtils.queryRouteIndexNames(restHighLevelClient, AppCode.REDBONUS.getValue(), event_code, beginTime, endTime);

            //1.构建 searchRequest
            SearchRequest searchRequest = new SearchRequest(indexs);
            //2.构建 searchSourceBuilder
            SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder().fetchSource(new String[] {"owner_code", "create_date", "recharge_user_count"}, null);;
            // 分页查询
            searchSourceBuilder.from(10);
            searchSourceBuilder.size(10);

            //3.构建 bool 查询组件
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();

            //4.构建 owner_code,create_date 时间范围的查询条件
            TermQueryBuilder ownerCodeQuery = QueryBuilders.termQuery("owner_code", owner_code);
            RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery("create_date");
            timeRangeQuery.from(beginTime).format(DateUtils.DATE_FULL_FORMAT);
            timeRangeQuery.to(endTime).format(DateUtils.DATE_FULL_FORMAT);

            // 4.使用  owner_code,create_date 时间范围的查询条件
            boolQueryBuilder.must(ownerCodeQuery);
            boolQueryBuilder.must(timeRangeQuery);

            // 3 使用 构建 bool 查询组件
            searchSourceBuilder.sort("create_date", SortOrder.DESC).size(1);
            searchSourceBuilder.query(boolQueryBuilder);

            //2 .使用 searchSourceBuilder
            searchRequest.source(searchSourceBuilder);

            // 1 .使用 searchRequest
            SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            SearchHits hits = search.getHits();
            SearchHit[] hitArrays = hits.getHits();

            Gson gson = new Gson();
            String str = "[";
            for (int x = 0; x < hitArrays.length; x++) {
                if (x == hitArrays.length - 1) {
                    str = str + gson.toJson(hitArrays[x].getSourceAsMap());
                } else {
                    str = str + gson.toJson(hitArrays[x].getSourceAsMap()) + ",";
                }
            }
            str = str + "]";
            return str;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/group")
    public String test2() {
        try {
            //  select app_code,owner_code sum(regi_user_count)  from  st_day group by app_code,owner_code
            String beginTime = "2019-06-06 00:00:00";
            String endTime = "2019-07-08 00:00:00";
            String event_code = EventCode.ST_DAY.getValue();

            // 1、创建search请求
            String indexs = RouteUtils.queryRouteIndexNames(restHighLevelClient, AppCode.REDBONUS.getValue(), event_code, beginTime, endTime);
            SearchRequest searchRequest = new SearchRequest(indexs);

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
            sourceBuilder.size(0);

            //字段值项分组聚合
            TermsAggregationBuilder aggregation = AggregationBuilders.terms("agg").field("app_code");
            TermsAggregationBuilder aggregation2 = AggregationBuilders.terms("agg2").field("owner_code");
            SumAggregationBuilder aggregation3 = AggregationBuilders.sum("agg3").field("regi_user_count");

            aggregation2.subAggregation(aggregation3);
            aggregation.subAggregation(aggregation2);

            sourceBuilder.aggregation(aggregation);
            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Terms agg = searchResponse.getAggregations().get("agg");

            for (Terms.Bucket entry : agg.getBuckets()) {
                Terms agg2 = entry.getAggregations().get("agg2");

                for (Terms.Bucket entry2 : agg2.getBuckets()) {
                    Sum agg3 = entry2.getAggregations().get("agg3");
                    double value = agg3.getValue();
                    System.out.println(entry.getKey() + " " + entry2.getKey() + "===========" + value);
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @RequestMapping("/top")
    public String test3() {
        try {
            //  分组排序 取  最大的前几条数据
            String beginTime = "2019-06-01 00:00:00";
            String endTime = "2019-07-09 00:00:00";
            String event_code = EventCode.ST_DAY.getValue();

            // 1、创建search请求
            String indexs = RouteUtils.queryRouteIndexNames(restHighLevelClient, AppCode.REDBONUS.getValue(), event_code, beginTime, endTime);
            SearchRequest searchRequest = new SearchRequest(indexs);

            // 2、用SearchSourceBuilder来构造查询请求体 ,请仔细查看它的方法，构造各种查询的方法都在这。
            SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();

            //字段值项分组聚合
            AggregationBuilder aggregation = AggregationBuilders.terms("agg").field("owner_code");
            AggregationBuilder aggregation2 = AggregationBuilders.topHits("top");
            aggregation.subAggregation(aggregation2);
            sourceBuilder.aggregation(aggregation);
            searchRequest.source(sourceBuilder);

            //3、发送请求
            SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
            Terms agg = searchResponse.getAggregations().get("agg");

            // For each entry
            for (Terms.Bucket entry : agg.getBuckets()) {
                TopHits topHits = entry.getAggregations().get("top");
                for (SearchHit hit : topHits.getHits().getHits()) {
                    logger.info(" -> id [{}], _source [{}]", hit.getId(), hit.getSourceAsString());
                }
            }
            return "";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
