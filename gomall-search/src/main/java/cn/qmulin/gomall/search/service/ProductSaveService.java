package cn.qmulin.gomall.search.service;

import cn.qmulin.common.to.es.SkuEsModel;

import java.io.IOException;
import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/24 16:11
 */
public interface ProductSaveService {
    boolean productStatusUp(List<SkuEsModel> skuEsModels) throws IOException;
}
