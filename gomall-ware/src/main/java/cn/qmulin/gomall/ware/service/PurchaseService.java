package cn.qmulin.gomall.ware.service;

import cn.qmulin.gomall.ware.vo.MergeVo;
import cn.qmulin.gomall.ware.vo.PurchaseDoneVo;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.gomall.ware.entity.PurchaseEntity;

import java.util.List;
import java.util.Map;

/**
 * 采购信息
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 11:24:52
 */
public interface PurchaseService extends IService<PurchaseEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void mergePurchase(MergeVo mergeVo);
    PageUtils queryPageUnreceive(Map<String, Object> params);

    void received(List<Long> ids);

    void done(PurchaseDoneVo doneVo);
}

