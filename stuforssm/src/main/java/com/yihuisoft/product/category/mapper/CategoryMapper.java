package com.yihuisoft.product.category.mapper;

import com.yihuisoft.product.category.entity.CategoryBig;
import com.yihuisoft.product.category.entity.CategorySmall;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 *  @Description：产品分类Mapper
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/1/29-10:37
 *  @version    ：V_1.0.0
 */
public interface CategoryMapper {
    /**
     * 创建产品大类
     * @param map
     */
    void createNewCategoryBig(@Param("map")Map map);

    /**
     * 创建产品小类
     * @param map
     */
    void createNewCategorySmall(@Param("map")Map map);

    /**
     * 修改产品大类
     * @param map
     */
    void changeCategoryBig(@Param("map")Map map);

    /**
     * 修改产品小类
     * @param map
     */
    void changeCategorySmall(@Param("map")Map map);

    /**
     * 删除产品大类
     * @param map
     */
    void delteCategoryBig(@Param("map")Map map);

    /**
     * 删除产品小类
     * @param map
     */
    void delteCategorySmall(@Param("map")Map map);

    /**
     * 查询产品大类
     * @return
     */
    List<CategoryBig> selectAllCategoryBig();

    /**
     * 查询产品小类
     * @param map
     * @return
     */
    List<CategorySmall> selectAllCategorySmall(@Param("map")Map map);
}
