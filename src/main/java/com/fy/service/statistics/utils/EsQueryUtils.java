package com.fy.service.statistics.utils;

import com.fy.framework.common.exception.PigException;
import com.fy.framework.component.config.TenantContextHolder;

import com.fy.service.statistics.constant.Constant;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.aggregations.AggregationBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

/**
 * (\__/)
 * ( ^-^)
 * /つ @author asheng
 *
 * @date 2019/7/6 19:08
 */
@Component
public class EsQueryUtils {
    @Autowired
    RestHighLevelClient restHighLevelClient;

    /**
     * 默认的查询方式
     *
     * @param appCode
     * @param event_code
     * @param userFlag
     * @param beginTime
     * @param endTime
     * @param aggs
     * @return
     */
    public SearchResponse getSearchResponse(String appCode, String event_code, int userFlag, String beginTime, String endTime, List<AggregationBuilder> aggs, String[] field) {
        try {
            BaseSearcher baseSearcher = new BaseSearcher(appCode, event_code, userFlag, beginTime, endTime, field).invoke();
            SearchRequest searchRequest = baseSearcher.getSearchRequest();
            SearchSourceBuilder searchSourceBuilder = baseSearcher.getSearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = baseSearcher.getBoolQueryBuilder();

            // 3 使用 构建 bool 查询组件
            searchSourceBuilder.sort("create_date", SortOrder.DESC).size(1000000);
            searchSourceBuilder.query(boolQueryBuilder);
            if (null != aggs) {
                for (AggregationBuilder agg : aggs) {
                    searchSourceBuilder.aggregation(agg);
                }
            }
            //2 .使用 searchSourceBuilder
            searchRequest.source(searchSourceBuilder);
            // 1 .使用 searchRequest
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new PigException(e);
        }
    }


    /**
     * 指定排序的查询方式
     *
     * @param appCode
     * @param event_code
     * @param userFlag
     * @param beginTime
     * @param endTime
     * @return
     */
    public SearchResponse getSearchResponse(String appCode, String event_code, int userFlag, String beginTime, String endTime, List<AggregationBuilder> aggs, String[] field, String sortField, SortOrder order) {
        try {
            BaseSearcher baseSearcher = new BaseSearcher(appCode, event_code, userFlag, beginTime, endTime, field).invoke();
            SearchRequest searchRequest = baseSearcher.getSearchRequest();
            SearchSourceBuilder searchSourceBuilder = baseSearcher.getSearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = baseSearcher.getBoolQueryBuilder();

            // 3 使用 构建 bool 查询组件
            searchSourceBuilder.sort(sortField, order).size(1000000);
            searchSourceBuilder.query(boolQueryBuilder);
            if (null != aggs) {
                for (AggregationBuilder agg : aggs) {
                    searchSourceBuilder.aggregation(agg);
                }
            }

            //2 .使用 searchSourceBuilder
            searchRequest.source(searchSourceBuilder);
            // 1 .使用 searchRequest
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new PigException(e);
        }
    }


    /**
     * 查明细的查询方式
     *
     * @param appCode
     * @param event_code
     * @param userFlag
     * @param beginTime
     * @param endTime
     * @return
     */
    public SearchResponse getDetailsSearchResponse(String appCode, String event_code, int userFlag, String beginTime, String endTime, List<AggregationBuilder> aggs, List<TermQueryBuilder> terms, List<RangeQueryBuilder> ranges, String[] field, Integer from, Integer size) {

        try {

            BaseSearcher baseSearcher = new BaseSearcher(appCode, event_code, userFlag, beginTime, endTime, field).invoke();
            SearchRequest searchRequest = baseSearcher.getSearchRequest();
            SearchSourceBuilder searchSourceBuilder = baseSearcher.getSearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = baseSearcher.getBoolQueryBuilder();
            if (null != terms) {
                for (TermQueryBuilder term : terms) {
                    boolQueryBuilder.must(term);
                }
            }
            if (null != ranges) {
                for (RangeQueryBuilder range : ranges) {
                    boolQueryBuilder.must(range);
                }
            }

            // 3 使用 构建 bool 查询组件
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.sort("user_id", SortOrder.DESC);
            searchSourceBuilder.from(from);
            searchSourceBuilder.size(size);

            if (null != aggs) {
                for (AggregationBuilder agg : aggs) {
                    searchSourceBuilder.aggregation(agg);
                }
            }

            //2 .使用 searchSourceBuilder
            searchRequest.source(searchSourceBuilder);
            // 1 .使用 searchRequest
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new PigException(e);
        }
    }



    /**
     * top10
     *
     * @param appCode
     * @param event_code
     * @param userFlag
     * @param beginTime
     * @param endTime
     * @return
     */
    public SearchResponse getTop10(String appCode, String event_code, int userFlag, String beginTime, String endTime, List<AggregationBuilder> aggs, List<TermQueryBuilder> terms, List<RangeQueryBuilder> ranges, String[] field, Integer from, Integer size) {

        try {

            BaseSearcher baseSearcher = new BaseSearcher(appCode, event_code, userFlag, beginTime, endTime, field).invoke();
            SearchRequest searchRequest = baseSearcher.getSearchRequest();
            SearchSourceBuilder searchSourceBuilder = baseSearcher.getSearchSourceBuilder();
            BoolQueryBuilder boolQueryBuilder = baseSearcher.getBoolQueryBuilder();
            if (null != terms) {
                for (TermQueryBuilder term : terms) {
                    boolQueryBuilder.must(term);
                }
            }
            if (null != ranges) {
                for (RangeQueryBuilder range : ranges) {
                    boolQueryBuilder.must(range);
                }
            }

            // 3 使用 构建 bool 查询组件
            searchSourceBuilder.query(boolQueryBuilder);
            searchSourceBuilder.sort("money", SortOrder.DESC);
            searchSourceBuilder.from(from);
            searchSourceBuilder.size(size);

            if (null != aggs) {
                for (AggregationBuilder agg : aggs) {
                    searchSourceBuilder.aggregation(agg);
                }
            }

            //2 .使用 searchSourceBuilder
            searchRequest.source(searchSourceBuilder);


            // 1 .使用 searchRequest
            return restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        } catch (Exception e) {
            throw new PigException(e);
        }
    }




    private class BaseSearcher {
        private String appCode;
        private String event_code;
        private int userFlag;
        private String beginTime;
        private String endTime;
        private String[] field;
        private SearchRequest searchRequest;
        private SearchSourceBuilder searchSourceBuilder;
        private BoolQueryBuilder boolQueryBuilder;

        public BaseSearcher(String appCode, String event_code, int userFlag, String beginTime, String endTime, String... field) {
            this.appCode = appCode;
            this.event_code = event_code;
            this.userFlag = userFlag;
            this.beginTime = beginTime;
            this.endTime = endTime;
            this.field = field;
        }

        public SearchRequest getSearchRequest() {
            return searchRequest;
        }

        public SearchSourceBuilder getSearchSourceBuilder() {
            return searchSourceBuilder;
        }

        public BoolQueryBuilder getBoolQueryBuilder() {
            return boolQueryBuilder;
        }

        public BaseSearcher invoke() throws IOException {
            String indexs = RouteUtils.queryRouteIndexNames(restHighLevelClient, appCode, event_code, beginTime, endTime);
            //1.构建 searchRequest
            searchRequest = new SearchRequest(indexs);
            //2.构建 searchSourceBuilder
            searchSourceBuilder = new SearchSourceBuilder().fetchSource(field, null);
            //3.构建 bool 查询组件
            boolQueryBuilder = QueryBuilders.boolQuery();
            //4.构建 owner_code,create_date 时间范围的查询条件
            System.out.println("当前商户：" + TenantContextHolder.getTenant());
            TermQueryBuilder ownerCodeQuery = QueryBuilders.termQuery("owner_code", TenantContextHolder.getTenant());
            TermQueryBuilder userFlageQuery = QueryBuilders.termQuery("user_flag", userFlag);
            RangeQueryBuilder timeRangeQuery = QueryBuilders.rangeQuery("create_date");
            timeRangeQuery.from(beginTime).format(DateUtils.DATE_FULL_FORMAT);
            timeRangeQuery.to(endTime).format(DateUtils.DATE_FULL_FORMAT);

            // 4.使用  owner_code,create_date 时间范围的查询条件
            boolQueryBuilder.must(ownerCodeQuery);
            if (userFlag != Constant.USER_FLAG_NONE) {
                boolQueryBuilder.must(userFlageQuery);
            }
            boolQueryBuilder.must(timeRangeQuery);
            return this;
        }
    }


}
