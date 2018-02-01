package com.yihuisoft.product.category.service;

import com.yihuisoft.product.category.entity.CategoryBig;
import com.yihuisoft.product.category.entity.CategorySmall;
import com.yihuisoft.product.category.mapper.CategoryMapper;
import oracle.sql.OracleJdbc2SQLInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.org.mozilla.javascript.internal.EcmaError;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *  @Description：产品分类Serice
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/1/29-9:57
 *  @version    ：V_1.0.0
 */
@Service
public class CategoryService {

    private Logger logger= LoggerFactory.getLogger(CategoryService.class);


    @Autowired
    private CategoryMapper categoryMapper;

    /**
     *  @Description：创建产品大类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-9:58
     */
    public void createNewCategoryBig(String bigCategory){
        Map<String,Object>map = new HashMap();
        map.put("bigCategory",bigCategory);
        try{
            categoryMapper.createNewCategoryBig(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("新增产品大类失败："+e.getMessage());
        }

    }

    /**
     *  @Description：创建产品小类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-9:58
     */
    public void createNewCategorySmall(Integer bigcode, Integer smallcode, String smallCategory){
        Map<String,Object> map = new HashMap();
        map.put("bigcode",bigcode);
        map.put("smallcode",smallcode);
        map.put("smallCategory",smallCategory);

        try{
            categoryMapper.createNewCategorySmall(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("新增产品小类失败" + e.getMessage());
        }
    }

    /**
     *  @Description：修改产品大类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-10:00
     */
    public void changeCategoryBig(Integer bigid, String bigCategory){
        Map<String,Object> map = new HashMap();
        map.put("bigid",bigid);
        map.put("bigCategory",bigCategory);

        try{
            categoryMapper.changeCategoryBig(map);
        }catch(Exception e){
            e.printStackTrace();
            logger.error("修改产品大类失败" + e.getMessage());
        }
    }

    /**
     *  @Description：修改产品小类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-10:02
     */
    public void changeCategorySmall(Integer smallid, String smallCategory){
        Map<String,Object> map = new HashMap();
        map.put("smallid",smallid);
        map.put("smallCategory",smallCategory);

        try{
            categoryMapper.changeCategorySmall(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("修改产品小类失败" + e.getMessage());
        }
    }

    /**
     *  @Description：删除产品大类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-10:04
     */
    public void deleteCategoryBig(Integer id){
        Map<String,Object> map = new HashMap();
        map.put("id",id);

        try{
            categoryMapper.delteCategoryBig(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除产品大类失败"+ e.getMessage());
        }
    }

    /**
     *  @Description：删除产品小类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-10:05
     */
    public void deleteCategorySmall(Integer id){
        Map<String,Object> map = new HashMap();
        map.put("id",id);

        try{
            categoryMapper.delteCategorySmall(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("删除产品大类失败"+ e.getMessage());
        }
    }

    /**
     *  @Description：查询所有产品大类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-10:07
     */
    public List<CategoryBig> selectAllCategoryBig(){
        List<CategoryBig> retlist = new ArrayList<CategoryBig>();
        try{
            retlist = categoryMapper.selectAllCategoryBig();
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询产品大类失败" + e.getMessage());
        }
        return retlist;
    }

    /**
     *  @Description：查询大类对应产品小类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-10:08
     */
    public List<CategorySmall> selectAllCategorySmall(Integer id){
        List<CategorySmall> retlist = new ArrayList<CategorySmall>();
        Map<String,Object>map = new HashMap();
        map.put("id",id);
        try{
            retlist = categoryMapper.selectAllCategorySmall(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询产品小类失败"+e.getMessage());
        }
        return retlist;
    }
}
