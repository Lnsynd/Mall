package com.lqs.mall.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import co.elastic.clients.elasticsearch._types.aggregations.AggregateBuilders;
import com.lqs.mall.dao.EsProductDao;
import com.lqs.mall.domain.EsProduct;
import com.lqs.mall.domain.EsProductRelatedInfo;
import com.lqs.mall.repository.EsProductRepository;
import com.lqs.mall.service.EsProductService;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.aggregations.*;
import org.elasticsearch.search.aggregations.bucket.filter.ParsedFilter;
import org.elasticsearch.search.aggregations.bucket.nested.NestedAggregationBuilder;
import org.elasticsearch.search.aggregations.bucket.nested.ParsedNested;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedLongTerms;
import org.elasticsearch.search.aggregations.bucket.terms.ParsedStringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.elasticsearch.xcontent.ParseField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * ES搜索商品管理实现类
 * Created by 刘千山 on 2023/7/5/005-15:07
 */
@Service
public class EsProductServiceImpl implements EsProductService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EsProductServiceImpl.class);

    @Autowired
    private EsProductDao productDao;
    @Autowired
    private EsProductRepository esProductRepository;

    @Autowired
    private ElasticsearchRestTemplate elasticsearchRestTemplate;

    @Override
    public int importAll() {
        List<EsProduct> allEsProductList = productDao.getAllEsProductList(null);
        Iterable<EsProduct> esProducts = esProductRepository.saveAll(allEsProductList);
        Iterator<EsProduct> iterator = esProducts.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            result++;
            iterator.next();
        }
        return result;
    }

    @Override
    public void delete(Long id) {
        esProductRepository.deleteById(id);
    }

    @Override
    public void delete(List<Long> ids) {
        if (CollUtil.isNotEmpty(ids)) {
            List<EsProduct> deleteProductList = new ArrayList<>();
            for (Long id : ids) {
                EsProduct esProduct = new EsProduct();
                esProduct.setId(id);
                deleteProductList.add(esProduct);
            }
            esProductRepository.deleteAll(deleteProductList);
        }
    }

    @Override
    public EsProduct create(Long id) {
        EsProduct result = null;
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            result = esProductRepository.save(esProduct);
        }
        return result;
    }

    @Override
    public Page<EsProduct> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return esProductRepository.findByNameOrSubTitleOrKeywords(keyword, keyword, keyword, pageable);
    }

    @Override
    public Page<EsProduct> search(String keyword, Long brandId, Long productCategoryId, Integer pageNum, Integer pageSize, Integer sort) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // 分页
        nativeSearchQueryBuilder.withPageable(pageable);
        // 过滤
        if (brandId != null || productCategoryId != null) {
            BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
            if (brandId != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("brandId", brandId));
            }
            if (productCategoryId != null) {
                boolQueryBuilder.must(QueryBuilders.termQuery("productCategoryId", productCategoryId));
            }
            nativeSearchQueryBuilder.withFilter(boolQueryBuilder);
        }
        // 搜索
        if (StrUtil.isEmpty(keyword)) {
            // 查询全部
            nativeSearchQueryBuilder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword), ScoreFunctionBuilders.weightFactorFunction(10)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword), ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", keyword), ScoreFunctionBuilders.weightFactorFunction(2)));
            // 转换成FunctionScoreQueryBuilder
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders).scoreMode(FunctionScoreQuery.ScoreMode.SUM).setMinScore(2);
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        // 排序
        if (sort == 1) {
            // 从新到旧
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("id").order(SortOrder.DESC));
        } else if (sort == 2) {
            //按销量从高到低
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("sale").order(SortOrder.DESC));
        } else if (sort == 3) {
            //按价格从低到高
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.ASC));
        } else if (sort == 4) {
            //按价格从高到低
            nativeSearchQueryBuilder.withSorts(SortBuilders.fieldSort("price").order(SortOrder.DESC));
        } else {
            //按相关度
            nativeSearchQueryBuilder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
        }
        nativeSearchQueryBuilder.withSorts(SortBuilders.scoreSort().order(SortOrder.DESC));
        NativeSearchQuery searchQuery = nativeSearchQueryBuilder.build();
        LOGGER.info("DSL:{}", searchQuery.getQuery().toString());
        SearchHits<EsProduct> search = elasticsearchRestTemplate.search(searchQuery, EsProduct.class);
        if (search.getTotalHits() <= 0) {
            return new PageImpl<>(ListUtil.empty(), pageable, 0);
        }
        // 获取搜索的内容
        List<EsProduct> esProductList = search.stream().map(SearchHit::getContent).collect(Collectors.toList());
        return new PageImpl<>(esProductList, pageable, search.getTotalHits());
    }

    @Override
    public Page<EsProduct> recommend(Long id, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<EsProduct> esProductList = productDao.getAllEsProductList(id);
        if (esProductList.size() > 0) {
            EsProduct esProduct = esProductList.get(0);
            String keyword = esProduct.getName();
            Long brandId = esProduct.getBrandId();
            Long productCategoryId = esProduct.getProductCategoryId();
            //根据商品标题、品牌、分类进行搜索
            List<FunctionScoreQueryBuilder.FilterFunctionBuilder> filterFunctionBuilders = new ArrayList<>();
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("name", keyword), ScoreFunctionBuilders.weightFactorFunction(8)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("subTitle", keyword), ScoreFunctionBuilders.weightFactorFunction(2)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("keywords", keyword), ScoreFunctionBuilders.weightFactorFunction(2)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("brandId", brandId), ScoreFunctionBuilders.weightFactorFunction(5)));
            filterFunctionBuilders.add(new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchQuery("productCategoryId", productCategoryId), ScoreFunctionBuilders.weightFactorFunction(3)));
            // 将filterFunctionBuilders 转换为 数组
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] builders = new FunctionScoreQueryBuilder.FilterFunctionBuilder[filterFunctionBuilders.size()];
            filterFunctionBuilders.toArray(builders);
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(builders).scoreMode(FunctionScoreQuery.ScoreMode.SUM).setMinScore(2);
            //用于过滤掉相同的商品
            BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
            boolQueryBuilder.mustNot(QueryBuilders.termQuery("id", id));
            //构建查询条件
            NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
            builder.withQuery(functionScoreQueryBuilder);
            builder.withFilter(boolQueryBuilder);
            builder.withPageable(pageable);
            NativeSearchQuery searchQuery = builder.build();
            LOGGER.info("DSL:{}", searchQuery.getQuery().toString());
            SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(searchQuery, EsProduct.class);
            if (searchHits.getTotalHits() <= 0) {
                return new PageImpl<>(ListUtil.empty(), pageable, 0);
            }
            List<EsProduct> searchProductList = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
            return new PageImpl<>(searchProductList, pageable, searchHits.getTotalHits());
        }
        return new PageImpl<>(ListUtil.empty());
    }

    @Override
    public EsProductRelatedInfo searchRelatedInfo(String keyword) {
        NativeSearchQueryBuilder builder = new NativeSearchQueryBuilder();
        //搜索条件
        if (StrUtil.isEmpty(keyword)) {
            builder.withQuery(QueryBuilders.matchAllQuery());
        } else {
            builder.withQuery(QueryBuilders.multiMatchQuery(keyword, "name", "subTitle", "keywords"));
        }
        // 聚合搜索 品牌信息
        builder.withAggregations(AggregationBuilders.terms("brandNames").field("brandName"));
        // 聚合搜索 分类信息
        builder.withAggregations(AggregationBuilders.terms("productCategoryNames").field("productCategoryName"));
        // 聚合搜索商品属性，去除type = 0的属性
        AbstractAggregationBuilder aggregationBuilder = AggregationBuilders.nested("allAttrValues", "attrValueList")
                .subAggregation(
                        AggregationBuilders
                                .filter("productAttrs", QueryBuilders.termQuery("attrValueList.type", 1))
                                .subAggregation(
                                        AggregationBuilders.terms("attrIds").field("attrValueList.productAttributeId")
                                                .subAggregation(AggregationBuilders.terms("attrValues").field("attrValueList.value"))
                                                .subAggregation(AggregationBuilders.terms("attrNames").field("attrValueList.name"))));
        builder.withAggregations(aggregationBuilder);
        NativeSearchQuery searchQuery = builder.build();
        SearchHits<EsProduct> searchHits = elasticsearchRestTemplate.search(searchQuery, EsProduct.class);
        return convertProductRelatedInfo(searchHits);
    }

    /**
     * 将返回结果转换为对象
     */
    private EsProductRelatedInfo convertProductRelatedInfo(SearchHits<EsProduct> searchHits) {
        EsProductRelatedInfo productRelatedInfo = new EsProductRelatedInfo();
        Aggregations aggregations = (Aggregations) searchHits.getAggregations().aggregations();
        Map<String, Aggregation> aggregationsMap = aggregations.asMap();
        // 设置品牌
        Aggregation brandNames = aggregationsMap.get("brandNames");
        List<String> brandNameList = new ArrayList<>();
        for (int i = 0; i < ((Terms) brandNames).getBuckets().size(); i++) {
            brandNameList.add(((Terms) brandNames).getBuckets().get(i).getKeyAsString());
        }
        productRelatedInfo.setBrandNames(brandNameList);
        // 设置分类
        Aggregation productCategoryNames = aggregationsMap.get("productCategoryNames");
        List<String> productCategoryNameList = new ArrayList<>();
        for (int i = 0; i < ((Terms) productCategoryNames).getBuckets().size(); i++) {
            productCategoryNameList.add(((Terms) productCategoryNames).getBuckets().get(i).getKeyAsString());
        }
        productRelatedInfo.setProductCategoryNames(productCategoryNameList);
        // 设置参数
        Aggregation allAttrValues = aggregationsMap.get("allAttrValues");
        ParsedFilter productAttrs = ((ParsedNested) allAttrValues).getAggregations().get("productAttrs");
        ParsedLongTerms attrIdsTerms = (ParsedLongTerms) productAttrs.getAggregations().get("attrIds");
        List<? extends Terms.Bucket> attrIds = attrIdsTerms.getBuckets();

        List<EsProductRelatedInfo.ProductAttr> attrList = new ArrayList<>();
        for (Terms.Bucket attrId : attrIds) {
            EsProductRelatedInfo.ProductAttr productAttr = new EsProductRelatedInfo.ProductAttr();
            productAttr.setAttrId((Long) attrId.getKey());
            List<String> attrValueList = new ArrayList<>();
            List<? extends Terms.Bucket> attrValues = ((ParsedStringTerms) attrId.getAggregations().get("attrValues")).getBuckets();
            List<? extends Terms.Bucket> attrNames = ((ParsedStringTerms) attrId.getAggregations().get("attrNames")).getBuckets();
            for (Terms.Bucket attrValue : attrValues) {
                attrValueList.add(attrValue.getKeyAsString());
            }
            productAttr.setAttrValues(attrValueList);
            if (!CollectionUtils.isEmpty(attrNames)) {
                String attrName = attrNames.get(0).getKeyAsString();
                productAttr.setAttrName(attrName);
            }
            attrList.add(productAttr);
        }
        productRelatedInfo.setProductAttrs(attrList);
        return productRelatedInfo;
    }

}
