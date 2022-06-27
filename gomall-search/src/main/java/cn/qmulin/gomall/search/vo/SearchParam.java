package cn.qmulin.gomall.search.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/27 15:41
 */
@Data
public class SearchParam {
    //全文匹配关键字
    private String keyword;
    //三级分类ID
    private Long catalog3Id;
    //排序规则
    private String sort;
    //是否有货
    private Integer hasStock;
    /*** 价格区间 */
    private String skuPrice;

    /*** 品牌id 可以多选 */
    private List<Long> brandId;

    /*** 按照属性进行筛选 */
    private List<String> attrs;

    /*** 页码*/
    private Integer pageNum = 1;

    /*** 原生所有查询属性*/
    private String _queryString;
}
