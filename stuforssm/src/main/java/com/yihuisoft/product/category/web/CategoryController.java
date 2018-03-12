package com.yihuisoft.product.category.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.yihuisoft.product.category.entity.Category;
import com.yihuisoft.product.category.entity.CategoryBig;
import com.yihuisoft.product.category.entity.CategoryData;
import com.yihuisoft.product.category.entity.CategorySmall;
import com.yihuisoft.product.category.entity.dto.ProductAssetInfo;
import com.yihuisoft.product.category.service.CategoryService;
import com.yihuisoft.product.page.Page;
import com.yihuisoft.product.util.CommonCal;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @RequestMapping(value = "/addbig")
    @ResponseBody
    public void addCategoryBig(String categoryBig){
        service.createNewCategoryBig(categoryBig);
    }

    @RequestMapping(value = "/addsmall")
    @ResponseBody
    public void addCategorySmall(String categorySmall, Integer bigcode, Integer smallcode){
        service.createNewCategorySmall(bigcode, smallcode, categorySmall);
    }

    @RequestMapping(value = "/changebig")
    @ResponseBody
    public void changeCategoreBig(Integer bigcode, String bigCategore){
        service.changeCategoryBig(bigcode,bigCategore);
    }

    @RequestMapping(value = "/changesmall")
    @ResponseBody
    public void changeCategoreSmall(Integer smallcode, String smallCategore){
        service.changeCategorySmall(smallcode,smallCategore);
    }

    @RequestMapping(value = "/deletebig")
    @ResponseBody
    public void deleteCategoreBig(Integer bigId){
        service.deleteCategoryBig(bigId);
    }

    @RequestMapping(value = "/deletesmall")
    @ResponseBody
    public void deleteCategoreSmall(Integer smallId){
        service.deleteCategorySmall(smallId);
    }

    @RequestMapping(value = "/selectAllbig")
    @ResponseBody
    public List<CategoryBig> selectAllBig(){
        List<CategoryBig> categoryBigs = service.selectAllCategoryBig();
        return categoryBigs;
    }

    @RequestMapping(value = "/selectAllsmall")
    @ResponseBody
    public List<CategorySmall> selectAllSmall(Integer id){
        List<CategorySmall> categorySmalls = service.selectAllCategorySmall(id);
        return categorySmalls;
    }
    
    /**
     * @author zhaodc 2018-02-02
     * @return
     */
    @RequestMapping(value = "/qryCategory")
    @ResponseBody
    public List<Category> qryCategory(){
    	List<Category> lst = service.qryCategoryList();
    	return lst;
    }
    
    /**
     * @author zhaodc 2018-02-05
     * @return
     */
    @RequestMapping(value = "/qryCategoryData")
    @ResponseBody
    public List<CategoryData> qryCategoryData(Integer cycleType,String startTime,String EndTime){
    	return service.qryCategoryDataList(cycleType,startTime,EndTime);
    }
    /**
     * @author zhaodc
     * @param code
     * @param starTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/toQryBidData")
    @ResponseBody
    public ModelAndView toQryBidData(int bidCode){
    	ModelAndView mv= new ModelAndView();
    	Map map = service.qryOneBidDataDetail(bidCode);
    	mv.addObject("bid", map);
    	mv.setViewName("assets/bidInfo");
    	return mv;
    	
    }
    
    /**
     * @author zhaodc
     * @param code
     * @param starTime
     * @param endTime
     * @return
     */
    @RequestMapping(value = "/qryBidData")
    @ResponseBody
    public List<CategoryData> qryBidData(int code,String starTime,String endTime){
    	return service.qryOneBidData(code,starTime,endTime);
    }
    
    
    /**
     *@author zhaodc
     *计算标的预期收益率，风险率
     * @param code
     * @param starTime
     * @param endTime
     * @return
     */
    @SuppressWarnings("finally")
	@RequestMapping(value = "/qryBidDetail")
    @ResponseBody
    public Map calculateBid(int code,String starTime,String endTime,String calculateType){
    	List<CategoryData> list= service.qryOneBidData(code,starTime,endTime);
    	Map<String, Double> map = new HashMap<String, Double>();
    	List<Double> doubleList = new ArrayList<Double>();
    	List<Double> yieldList = new ArrayList<Double>();
		Double expectYieldRatio =0.00;
		Double expectYearYieldRatio =0.00;
		Double exceptstandard =0.00;
		Double expectRiskRatio =0.00;
	
		
    	//获取bid收盘价序列
    	for(CategoryData li :list){
    		doubleList.add(li.getData());
    	}
    	try {
			//计算 收益率
			if(doubleList !=null && doubleList.size()>0){
				for(int j=0;j<list.size()-1;j++){
					 double yield=CommonCal.calculateYieldRatio(doubleList.get(j+1),doubleList.get(j));
					 yieldList.add(yield);
				}
			}
			//计算预期收益率
			expectYieldRatio = CommonCal.calculateGeometricMean(yieldList);
			//年化收益率
			expectYearYieldRatio =expectYieldRatio*365;
			
			//计算预期风险率
			if ("standard".equals(calculateType) || "".equals(calculateType) || calculateType == null) { //标准差计算预期风险率
				
			  //  expectRiskRatio = CommonCal.calculateGeometricMean(yieldList)*Math.sqrt(220);
				Double[] objs = yieldList.toArray(new Double[0]);
				exceptstandard=CommonCal.StandardDiviation(objs);
			    expectRiskRatio = exceptstandard*Math.sqrt(365);
			    
			    
			}else if ("semiVariance".equals(calculateType)) { //半方差计算预期风险率
				Double[] objs = yieldList.toArray(new Double[0]);
				exceptstandard=CommonCal.StandardDiviation(objs);
				expectRiskRatio = CommonCal.calculateSemiVariance(yieldList)*Math.sqrt(220);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			map.put("expectYieldRatio",expectYieldRatio);
			map.put("expectYearYieldRatio",expectYearYieldRatio);
			map.put("exceptstandard",exceptstandard);
			map.put("expectRiskRatio",expectRiskRatio);
			return map;
		}
    }
    
    @RequestMapping(value="/getProductAssetInfo")
    @ResponseBody
    public Map getProductAssetInfo(@RequestParam("page")Integer page,@RequestParam("rows")Integer rows,Integer categorybig,
    		Integer categorysmall,Integer assettype){
    	Map map=new HashMap();
    	map.put("categorybig", categorybig);
    	map.put("categorysmall", categorysmall);
    	map.put("assettype", assettype);
    	Page p=new Page(page, rows);
    	Map reMap=new HashMap();
    	List list=service.getProductAssetInfo(p,map);
    	reMap.put("rows",list);
    	reMap.put("total", service.getTotalProductAssetInfo(map));
    	return reMap;
    }

    
    
}
