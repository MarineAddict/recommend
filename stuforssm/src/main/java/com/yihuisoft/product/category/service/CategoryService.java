package com.yihuisoft.product.category.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yihuisoft.product.category.entity.Category;
import com.yihuisoft.product.category.entity.CategoryBig;
import com.yihuisoft.product.category.entity.CategoryData;
import com.yihuisoft.product.category.entity.CategorySmall;
import com.yihuisoft.product.category.entity.dto.ProductAssetInfo;
import com.yihuisoft.product.category.mapper.CategoryMapper;
import com.yihuisoft.product.page.Page;

/**
 *  @Description：产品分类Serice
 *  @author     ：Chengxin.Ma
 *  @Data       ：2018/1/29-9:57
 *  @version    ：V_1.0.0
 */
@Service
public class CategoryService {

    private Logger logger= LoggerFactory.getLogger(CategoryService.class);
    public static final int 三个月=3;
    public static final int 六个月=6;
    public static final int 九个月=9;
    public static final int 一年=12;
    public static final int 近三年=3*一年;
    public static final int 近五年=5*一年;
    
    

    @Autowired
    private CategoryMapper categoryMapper;

    /**
     *  @Description：创建产品大类
     *  @author     ：Chengxin.Ma
     *  @Data       ：2018/1/29-9:58
     */
    public void createNewCategoryBig(String bigCategory){
        Map<String,Object>map = new HashMap<String, Object>();
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
        Map<String,Object> map = new HashMap<String, Object>();
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
        Map<String,Object> map = new HashMap<String, Object>();
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
        Map<String,Object> map = new HashMap<String, Object>();
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
        Map<String,Object> map = new HashMap<String, Object>();
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
        Map<String,Object> map = new HashMap<String, Object>();
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
        Map<String,Object>map = new HashMap<String, Object>();
        map.put("id",id);
        try{
            retlist = categoryMapper.selectAllCategorySmall(map);
        }catch (Exception e){
            e.printStackTrace();
            logger.error("查询产品小类失败"+e.getMessage());
        }
        return retlist;
    }
    
    
    /**
     * 查询产品类别
     * @author zhaodc 2018-02-02
     * @return
     */
    public List<Category> qryCategoryList(){
    	Map map = new HashMap();//可传参数做分页处理
    	return categoryMapper.getCategoryList(map);
    	
    }
    
    /**
     * @author zhaodc 
     * @return
     */
    public List<CategoryData> qryCategoryDataList(Integer cycletype,String startTime,String endTime){
    	//根据循环周期计算日期
    	
    	List<CategoryData> dataList=null;
    	Map data=countDateFromNow(cycletype,startTime,endTime);
    	try{
    	dataList= categoryMapper.getCategoryDataList(data);
    	}catch(Exception e){
    		e.printStackTrace();
    		logger.error("获取CATEGORY_DATA数据异常");
    	}
    	return dataList;
    }
   
    /**
     * 根据周期（例如：3个月，5个月，9个月计算当时的日期）
     * @param cycle
     * @return
     */
    private Map<String,String> countDateFromNow(Integer cycle,String startTime,String endTime){
    	Map map=new HashMap<>();
    	
    	if (cycle==null&&startTime==null&&endTime==null){
    		//默认3个月,为了响应速度
    		cycle=三个月;
    		map=getStartEnd(cycle);
    	} else if (cycle!=null){
    		//计算起始结束时间
    		map=getStartEnd(cycle);
    	}else if(cycle==null&&startTime!=null&&endTime!=null){
    	  //有开始结束时间，直接装进map返回
    		map.put("startTime", startTime);
    		map.put("endTime", endTime);
    	}
    	return map;
    }
/**
 * 选定当天未起始日期，计算周期
 * @param cycle
 * @return
 */
       private Map getStartEnd(Integer cycle){
    	   Map map=new HashMap<>();
    	 SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
       	Date today=new Date();
       	Calendar c=Calendar.getInstance();
       	c.setTime(today);
       	c.add(Calendar.MONTH,-cycle);
       	Date dbefore=c.getTime();
       	map.put("startTime", sdf.format(dbefore));
       	
       	map.put("endTime", sdf.format(today));
        return map;
       }
    
    
    
    
    /**
     * @author zhaodc 
     * @return
     */
    @SuppressWarnings("finally")
	public Map qryOneBidDataDetail(int code){
    	Map map = new HashMap();
		String dmax =null;
		String dmin =null;
		try {
			map = categoryMapper.getOneBidDetail(code);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			dmax = sdf.format(map.get("DMAX"));
			dmin = sdf.format(map.get("DMIN"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询标的详情失败"+e.getMessage());
		}finally{
			map.put("dmax", dmax);
	    	map.put("dmin", dmin);
	    	return map;
		}
    }

    /**
     * @author zhaodc
     * @param code
     * @return
     */
	public List<CategoryData> qryOneBidData(int code,String starTime,String endTime) {
		Map<String, Comparable> map =new HashMap<String, Comparable>();
		map.put("code", code);
		map.put("starTime", starTime);
		map.put("endTime", endTime);
		List<CategoryData> li = null;
		try {
			li = categoryMapper.getOneBidData(map);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("查询标的信息失败"+ e.getMessage());
		}
		return li;
	}
	
	/**
	 * 获取全部的资产信息
	 * @param p
	 * @param map
	 * @return
	 */
	public List<ProductAssetInfo> getProductAssetInfo(Page p,Map map){
		List<ProductAssetInfo> returnedList=null;
		try{
			returnedList=categoryMapper.getProductAssetInfo(p,map);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("查询资产信息失败"+ e.getMessage());
		}
		return returnedList;
	}
	
	public Integer getTotalProductAssetInfo(Map map){
		Integer total=null;
		try{
			total=categoryMapper.getTotalProductAssetInfo(map);
		}catch(Exception e){
			e.printStackTrace();
			logger.error("获取资产信息总数失败"+ e.getMessage());
		}
		return total;
	}
	
}
