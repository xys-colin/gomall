package cn.qmulin.gomall.product.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import cn.qmulin.gomall.product.entity.CategoryEntity;
import cn.qmulin.gomall.product.service.CategoryService;
import cn.qmulin.common.utils.PageUtils;
import cn.qmulin.common.utils.R;



/**
 * 商品三级分类
 *
 * @author xys
 * @email 2583247805@qq.com
 * @date 2022-06-17 01:29:03
 */
@RestController
@RequestMapping("product/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    /**
     * 查出所有分类一级子分类,以树形结构组装起来
     */
    @RequestMapping(path = "/list/tree",method = RequestMethod.GET)
    public R list(){
        List<CategoryEntity> page = categoryService.listWithTree();

        return R.ok().put("data", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{catId}")
    //@RequiresPermissions("product:category:info")
    public R info(@PathVariable("catId") Long catId){
		CategoryEntity category = categoryService.getById(catId);

        return R.ok().put("data", category);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    //@RequiresPermissions("product:category:save")
    public R save(@RequestBody CategoryEntity category){
		categoryService.save(category);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    //@RequiresPermissions("product:category:update")
    public R update(@RequestBody CategoryEntity category){
		categoryService.updateById(category);

        return R.ok();
    }

    @RequestMapping(value = "/update/sort",method = RequestMethod.POST)
    //@RequiresPermissions("product:category:update")
    public R updateSort(@RequestBody List<CategoryEntity> categorys){
        categoryService.updateBatchById(categorys);

        return R.ok();
    }
    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] catIds){
        categoryService.removeMenuByIds(Arrays.asList(catIds));
        return R.ok();
    }

}
