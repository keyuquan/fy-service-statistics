package com.fy.service.statistics.utils;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;

import com.fy.service.statistics.constant.Constant;
import com.fy.service.statistics.model.es.EdaIndexRoute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class RouteUtils {



    public static String queryRouteIndexNames(RestHighLevelClient restHighLevelClient,String appCode, String eventCode, String beginTime, String endTime) throws IOException {
        List<EdaIndexRoute> routes = queryRouteIndex(restHighLevelClient,appCode, eventCode, beginTime, endTime);
        List<String> indexs = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(routes)) {
            for (EdaIndexRoute route : routes) {
                indexs.add(route.getIndexName());
            }
        }
        String joinNames = StringUtils.join(indexs, ",");
        return joinNames;
    }


    /**
     * 根据appCode、eventCode、时间起\止 查询可用的索引
     *
     * @param appCode
     * @param eventCode
     * @param beginTime
     * @param endTime
     * @return
     * @throws IOException
     */
    public static List<EdaIndexRoute> queryRouteIndex(RestHighLevelClient restHighLevelClient,String appCode, String eventCode, String beginTime, String endTime) throws IOException {
        SearchResponse searchResponse = queryIndexRoutesByTime(restHighLevelClient,appCode, eventCode, beginTime, endTime);
        SearchHits hits = searchResponse.getHits();
        long totalHits = hits.getTotalHits().value;
        if (totalHits == 0l) {
            return null;
        } else {
            SearchHit[] hitArrays = hits.getHits();
            List<EdaIndexRoute> routes = new ArrayList();
            for (SearchHit hit : hitArrays) {
                Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                String indexName = (String) sourceAsMap.get(EdaIndexRoute.INDEX_NAME_FIELD);
                String begin = (String) sourceAsMap.get(EdaIndexRoute.START_TIME_FIELD);
                String end = (String) sourceAsMap.getOrDefault(EdaIndexRoute.END_TIME_FIELD, "");
                long size = Long.valueOf(sourceAsMap.getOrDefault(EdaIndexRoute.SIZE_FIELD, 0l).toString());
                EdaIndexRoute route = new EdaIndexRoute();
                route.setId(hit.getId());
                route.setAppCode(appCode);
                route.setEventCode(eventCode);
                route.setIndexName(indexName);
                route.setStartTime(begin);
                route.setEndTime(end);
                route.setSize(size);
                routes.add(route);
            }
            return routes;
        }
    }

    /**
     * 根据appCode,事件code,开始时间,结束时间,查找数据
     *
     * @param appCode
     * @param eventCode
     * @param beginTime
     * @param endTime
     * @return
     */
    private static SearchResponse queryIndexRoutesByTime( RestHighLevelClient restHighLevelClient,String appCode, String eventCode, String beginTime, String endTime) throws IOException {
        //构建 searchRequest
        SearchRequest searchRequest = new SearchRequest(Constant.ROUTE_INDEX_NAME);
        //构建searchSourceBulider
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //通过bool来进行查询条件组件
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        //构建 appCode,事件code和时间范围的查询条件
        TermQueryBuilder appCodeQuery = QueryBuilders.termQuery(EdaIndexRoute.APP_CODE_FIELD, appCode);
        TermQueryBuilder eventCodeQuery = QueryBuilders.termQuery(EdaIndexRoute.EVENT_CODE_FIELD, eventCode);
        boolQueryBuilder.must(appCodeQuery).must(eventCodeQuery);
        //开始时间,结束时间不为空 则需要查询为: 查询的起始时间在数据的开始时间与结束时间之间
        updateQuery(beginTime, endTime, boolQueryBuilder);
        //按照时间排序
        searchSourceBuilder.sort(EdaIndexRoute.START_TIME_FIELD, SortOrder.DESC).size(1000);
        searchSourceBuilder.query(boolQueryBuilder);
        //完成最终查询条件组件
        searchRequest.source(searchSourceBuilder);
        SearchResponse search = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        return search;
    }

    private static void updateQuery(String beginTime, String endTime, BoolQueryBuilder boolQueryBuilder) {
        if (StringUtils.isNotBlank(beginTime) && StringUtils.isNotBlank(endTime)) {
            BoolQueryBuilder condition = constructCondition(beginTime, endTime);
            boolQueryBuilder.must(condition);
        } else if (StringUtils.isNotBlank(beginTime) && StringUtils.isBlank(endTime)) {
            //开始时间不为空,结束时间为空
            BoolQueryBuilder condition = constructBeginTimeCondition(beginTime);
            boolQueryBuilder.must(condition);
        } else if (StringUtils.isBlank(beginTime) && StringUtils.isNotBlank(endTime)) {
            //开始时间为空,结束时间不为空, 数据开始时间小于查询结束时间
            RangeQueryBuilder startTimeRange = QueryBuilders.rangeQuery(EdaIndexRoute.START_TIME_FIELD);
            startTimeRange.to(endTime).format(DateUtils.DATE_FULL_FORMAT);
            boolQueryBuilder.must(startTimeRange);
        }
    }

    private static BoolQueryBuilder constructCondition(String beginTime, String endTime) {
        BoolQueryBuilder conditionBoolQueryBuilder = QueryBuilders.boolQuery();
        // 1,数据的开始时间小于查询的结束时间
        RangeQueryBuilder startTimeQuery = QueryBuilders.rangeQuery(EdaIndexRoute.START_TIME_FIELD);
        startTimeQuery.to(endTime).format(DateUtils.DATE_FULL_FORMAT);

        // 2, 如果数据没有结束时间时 数据的结束时间大于查询的开始时间
        BoolQueryBuilder noExistsEndTimeBoolQueryBuilder = QueryBuilders.boolQuery();
        // 3, 如果数据有结束时间时 数据的结束时间大于查询的开始时间
        BoolQueryBuilder existsEndTimeBoolQueryBuilder = QueryBuilders.boolQuery();

        RangeQueryBuilder endTimeQuery = QueryBuilders.rangeQuery(EdaIndexRoute.END_TIME_FIELD);
        endTimeQuery.from(beginTime).format(DateUtils.DATE_FULL_FORMAT);
        ExistsQueryBuilder existsEndTimeQuery = QueryBuilders.existsQuery(EdaIndexRoute.END_TIME_FIELD);

        existsEndTimeBoolQueryBuilder.must(existsEndTimeQuery).must(startTimeQuery).must(endTimeQuery);

        noExistsEndTimeBoolQueryBuilder.mustNot(existsEndTimeQuery).must(startTimeQuery);

        conditionBoolQueryBuilder.should(noExistsEndTimeBoolQueryBuilder).should(existsEndTimeBoolQueryBuilder);
        return conditionBoolQueryBuilder;
    }

    /**
     * 根据开始时间,组装路由查询条件
     *
     * @param beginTime
     * @return
     */
    private static BoolQueryBuilder constructBeginTimeCondition(String beginTime) {
        BoolQueryBuilder conditionBoolQueryBuilder = QueryBuilders.boolQuery();

        // 1,数据的结束时间大于查询的开始时间
        RangeQueryBuilder endTimeQuery = QueryBuilders.rangeQuery(EdaIndexRoute.END_TIME_FIELD);
        endTimeQuery.from(beginTime).format(DateUtils.DATE_FULL_FORMAT);

        // 2, 如果数据的结束时间为空时,需要的条件时,数据的开始时间大于小于查询开始时间
        BoolQueryBuilder noExistsEndTimeBoolQueryBuilder = QueryBuilders.boolQuery();
        ExistsQueryBuilder existsEndTimeQuery = QueryBuilders.existsQuery(EdaIndexRoute.END_TIME_FIELD);
        noExistsEndTimeBoolQueryBuilder.mustNot(existsEndTimeQuery);

        conditionBoolQueryBuilder.should(endTimeQuery).should(noExistsEndTimeBoolQueryBuilder);
        return conditionBoolQueryBuilder;
    }

}
