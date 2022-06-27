package cn.qmulin.gomall.search.service;

import cn.qmulin.gomall.search.vo.SearchParam;
import cn.qmulin.gomall.search.vo.SearchResult;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/27 15:43
 */
public interface MallSearchService {
    SearchResult search(SearchParam param);
}

