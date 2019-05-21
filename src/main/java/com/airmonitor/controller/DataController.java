package com.airmonitor.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.json.Json;
import javax.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.airmonitor.pojo.TbData;
import com.airmonitor.pojo.TbUser;
import com.airmonitor.service.DataService;
import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;

import entity.CityData;
import entity.CityData.ResultBean;
import entity.PageResult;
import entity.Result;
/**
 * controller
 * @author Administrator
 *
 */
@RestController
@RequestMapping("/data")
public class DataController {

	@Autowired
	private DataService dataService;
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findAll")
	public List<TbData> findAll(){			
		return dataService.findAll();
	}
	
	
	/**
	 * 返回全部列表
	 * @return
	 */
	@RequestMapping("/findPage")
	public PageResult  findPage(int page,int rows){			
		return dataService.findPage(page, rows);
	}
	
	/**
	 * 增加
	 * @param data
	 * @return
	 */
	@RequestMapping("/add")
	public Result add(@RequestBody TbData data){
		try {
			dataService.add(data);
			return new Result(true, "增加成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "增加失败");
		}
	}
	
	/**
	 * 修改
	 * @param data
	 * @return
	 */
	@RequestMapping("/update")
	public Result update(@RequestBody TbData data){
		try {
			dataService.update(data);
			return new Result(true, "修改成功");
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "修改失败");
		}
	}	
	
	/**
	 * 获取实体
	 * @param id
	 * @return
	 */
	@RequestMapping("/findOne")
	public TbData findOne(Integer id){
		return dataService.findOne(id);		
	}
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	@RequestMapping("/delete")
	public Result delete(Integer [] ids){
		try {
			dataService.delete(ids);
			return new Result(true, "删除成功"); 
		} catch (Exception e) {
			e.printStackTrace();
			return new Result(false, "删除失败");
		}
	}
	
		/**
	 * 查询+分页
	 * @param brand
	 * @param page
	 * @param rows
	 * @return
	 */
	@RequestMapping("/search")
	public PageResult search(@RequestBody TbData data, int page, int rows  ){
		return dataService.findPage(data, page, rows);		
	}

	@RequestMapping(value="/taskSearch",produces = "application/json;charset=UTF-8")
	public Object taskSearch(String city,HttpSession session){
		RestTemplate  restTemplate=new RestTemplate();
		TbUser user=(TbUser) session.getAttribute("user");
		if(user!=null){
			Map<String, Object> map=new HashMap<>();
			/*	map.put("city", data.getCity());
				map.put("key", "cb8ce513dbb53a503455ee7b8eef155c");*/
				String key="cb8ce513dbb53a503455ee7b8eef155c";
				String url="http://web.juhe.cn:8080/environment/air/pm?key="+key+"&city="+city;
				
				String result = restTemplate.postForObject(url,JSON.toJSONString(map) , String.class);
				CityData cityData = new Gson().fromJson(result, CityData.class);
				List<ResultBean> list = cityData.getResult();
				for (ResultBean bean : list) {
					TbData tbData=new TbData();
					tbData.setAqi(bean.getAQI());
					tbData.setCo(bean.getCO());
					tbData.setCity(bean.getCity());
					tbData.setNo2(bean.getNO2());
					tbData.setO3(bean.getO3());
					tbData.setPm10(bean.getPM10());
					tbData.setPm2(bean.getPM2());
					tbData.setSo2(bean.getSO2());
					tbData.setTime(bean.getTime());
					tbData.setQuality(bean.getQuality());
					tbData.setCid(user.getCid());
					dataService.add(tbData);
					
				}
				return result;		
		}
		
		return new Result(false, "未登录");		
	}
	@RequestMapping(value="/searchHistory",produces = "application/json;charset=UTF-8")
	public Object searchHistory(String dates,HttpSession session,String cid){
		TbUser user=(TbUser) session.getAttribute("user");
		String searchId;
		if(user!=null){
			if(user.getType()==0) {
				searchId=user.getCid()+"";
			}else {
				searchId=cid;
			}
				
			List<TbData> datas=dataService.findAllByCid(Integer.parseInt(searchId),dates);
			Map<String, Object> resultMap=new HashMap<String, Object>();
			List<Map<String, Object>> resultList=new ArrayList<Map<String,Object>>();
			List<String> xAxisData=new ArrayList<String>();
			List<String> legendData=new ArrayList<String>();
			Map<String, Object> mp1=new HashMap<String, Object>();
			mp1.put("name", "PM2.5");
			mp1.put("type", "line");
			//mp1.put("stack", "总量");
			List<Integer> pm2List=new ArrayList<Integer>();
			Map<String, Object> mp2=new HashMap<String, Object>();
			mp2.put("name", "PM10");
			mp2.put("type", "line");
			//mp2.put("stack", "总量");
			List<Integer> pm10List=new ArrayList<Integer>();
			Map<String, Object> mp3=new HashMap<String, Object>();
			mp3.put("name", "SO2");
			mp3.put("type", "line");
			//mp3.put("stack", "总量");
			List<Integer> so2List=new ArrayList<Integer>();
			Map<String, Object> mp4=new HashMap<String, Object>();
			mp4.put("name", "CO");
			mp4.put("type", "line");
			//mp4.put("stack", "总量");
			List<Double> coList=new ArrayList<Double>();
			Map<String, Object> mp5=new HashMap<String, Object>();
			mp5.put("name", "NO2");
			mp5.put("type", "line");
			//mp5.put("stack", "总量");
			List<Integer> no2List=new ArrayList<Integer>();
			
			Map<String, Object> mp6=new HashMap<String, Object>();
			mp6.put("name", "O3");
			mp6.put("type", "line");
			//mp6.put("stack", "总量");
			List<Integer> o3List=new ArrayList<Integer>();
			for(TbData d:datas){
				xAxisData.add(d.getTime());
				pm2List.add(Integer.parseInt(d.getPm2()));
				pm10List.add(Integer.parseInt(d.getPm10()));
				so2List.add(Integer.parseInt(d.getSo2()));
				coList.add(Double.parseDouble(d.getCo()));
				no2List.add(Integer.parseInt(d.getNo2()));
				o3List.add(Integer.parseInt(d.getO3()));
			}
			mp1.put("data", pm2List);
			mp2.put("data", pm10List);
			mp3.put("data", so2List);
			mp4.put("data", coList);
			mp5.put("data", no2List);
			mp6.put("data", o3List);
			resultList.add(mp1);
			resultList.add(mp2);
			resultList.add(mp3);
			resultList.add(mp4);
			resultList.add(mp5);
			resultList.add(mp6);
			resultMap.put("data", xAxisData);
			resultMap.put("series", resultList);
			return resultMap;		
		}
		return new Result(false, "请先登录");
	}
}
