package com.fy.service.statistics.utils;


import com.fy.service.statistics.model.es.Pagination;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.aggregations.Aggregations;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.aggregations.metrics.ParsedValueCount;


public class PageUtils {

    public static Pagination Pagination(Integer page, Integer size, SearchResponse Search,String terms_name,String count_name) {
        Pagination pag =new Pagination();

        Long totalCount = 0L;
        Terms agg = Search.getAggregations().get(terms_name);

        if (agg.getBuckets().size() > 0) {
            Aggregations list = agg.getBuckets().get(0).getAggregations();
            totalCount = ((ParsedValueCount) list.get(count_name)).getValue();
        }

        //  根据数据条数 和 参数  求取分页的   from 和 size
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
        pag.setFrom(from);
        pag.setSizeOne(sizeOne);
        pag.setTotalCount(totalCount);
        return pag;
    }








}
