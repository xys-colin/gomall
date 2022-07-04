package cn.qmulin.gomall.ware.service.impl;

import cn.qmulin.common.utils.R;
import cn.qmulin.gomall.ware.feign.MemberFeignService;
import cn.qmulin.gomall.ware.vo.FareVo;
import cn.qmulin.gomall.ware.vo.MemberAddressVo;
import com.alibaba.fastjson.TypeReference;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Map;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.common.utils.Query;

import cn.qmulin.gomall.ware.dao.WareInfoDao;
import cn.qmulin.gomall.ware.entity.WareInfoEntity;
import cn.qmulin.gomall.ware.service.WareInfoService;


@Service("wareInfoService")
public class WareInfoServiceImpl extends ServiceImpl<WareInfoDao, WareInfoEntity> implements WareInfoService {
    @Autowired
    private MemberFeignService memberFeignService;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        String key = (String) params.get("key");
        QueryWrapper<WareInfoEntity> wrapper = new QueryWrapper<>();
        if (StringUtils.isNotEmpty(key)){
            wrapper.eq("id",key).or().like("name",key)
                    .or().like("address",key)
                    .or().like("areacode",key);
        }
        IPage<WareInfoEntity> page = this.page(
                new Query<WareInfoEntity>().getPage(params),wrapper
        );

        return new PageUtils(page);
    }

    @Override
    public FareVo getFare(Long addrId) {
        FareVo fareVo = new FareVo();
        R r = memberFeignService.addressInfo(addrId);
        MemberAddressVo data = r.getData("memberReceiveAddress",new TypeReference<MemberAddressVo>() {
        });
        if (data != null) {
            //模拟运费
            String phone = data.getPhone();
            String substring = phone.substring(phone.length() - 1);
            BigDecimal bigDecimal = new BigDecimal(substring);
            fareVo.setAddressVo(data);
            fareVo.setFare(bigDecimal);
            return fareVo;
        }
        return null;
    }

}