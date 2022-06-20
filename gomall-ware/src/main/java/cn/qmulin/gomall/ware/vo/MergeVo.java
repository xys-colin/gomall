package cn.qmulin.gomall.ware.vo;

import lombok.Data;

import java.util.List;

/**
 * @description:
 * @author: xys
 * @date: 2022/6/20 17:52
 */
@Data
public class MergeVo {
    private Long purchaseId;

    private List<Long> items;
}
