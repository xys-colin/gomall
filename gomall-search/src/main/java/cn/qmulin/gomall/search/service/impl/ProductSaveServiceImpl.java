package cn.qmulin.gomall.search.service.impl;

import cn.qmulin.common.to.es.SkuEsModel;
import cn.qmulin.gomall.search.config.GomallElasticsearchConfig;
import cn.qmulin.gomall.search.constant.EsConstant;
import cn.qmulin.gomall.search.service.ProductSaveService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.bulk.BulkItemResponse;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/24 16:13
 */
@Service
@Slf4j
public class ProductSaveServiceImpl implements ProductSaveService {
    @Autowired
    RestHighLevelClient restHighLevelClient;
    @Override
    public boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException {
        //1.在es中建立索引,建立好映射关系
        //2. 给es中保存这些数据
        BulkRequest request = new BulkRequest();
        for (SkuEsModel model : skuEsModels) {
            IndexRequest indexRequest = new IndexRequest(EsConstant.PRODUCT_INDEX);
            indexRequest.id(String.valueOf(model.getSkuId()));
            String s = JSON.toJSONString(model);
            indexRequest.source(s, XContentType.JSON);
            request.add(indexRequest);
        }
        BulkResponse bulk = restHighLevelClient.bulk(request, GomallElasticsearchConfig.COMMON_OPTIONS);
        // TODO: 如果批量错误,依次处理
        List<String> collect = Arrays.stream(bulk.getItems()).map(BulkItemResponse::getId).collect(Collectors.toList());
        log.error("商品上架完成:{}",collect);
        return bulk.hasFailures();
    }

}
