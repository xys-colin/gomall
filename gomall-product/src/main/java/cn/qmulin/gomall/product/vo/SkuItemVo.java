package cn.qmulin.gomall.product.vo;

import cn.qmulin.gomall.product.entity.SkuImagesEntity;
import cn.qmulin.gomall.product.entity.SkuInfoEntity;
import cn.qmulin.gomall.product.entity.SpuInfoDescEntity;
import lombok.Data;

import java.util.List;
/**
 * @author xys
 */
@Data
public class SkuItemVo {

    //1、sku基本信息获取 pms_sku_info
    SkuInfoEntity info;

    boolean hasStock = true;//库存
    //2、sku图片信息    pms_sku_images
    List<SkuImagesEntity> images;
    //3、获取spu的销售属性组合
    List<SkuItemSaleAttrVo> saleAttr;
    //4、获取spu的介绍
    SpuInfoDescEntity desp;
    //5、获取spu的规格参数信息
    List<SpuItemAttrGroupVo> groupAttrs;

    //6、秒杀商品的优惠信息
    private SeckillSkuVo seckillSkuVo;


}
