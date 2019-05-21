package com.airmonitor.service.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.airmonitor.mapper.TbDataMapper;
import com.airmonitor.pojo.TbData;
import com.airmonitor.pojo.TbDataExample;
import com.airmonitor.pojo.TbDataExample.Criteria;
import com.airmonitor.service.DataService;

import entity.PageResult;
import entity.Result;

/**
 * 服务实现层
 * @author Administrator
 *
 */
@Service
public class DataServiceImpl implements DataService {

	@Autowired
	private TbDataMapper dataMapper;
	
	/**
	 * 查询全部
	 */
	@Override
	public List<TbData> findAll() {
		return dataMapper.selectByExample(null);
	}

	/**
	 * 按分页查询
	 */
	@Override
	public PageResult findPage(int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);		
		Page<TbData> page=   (Page<TbData>) dataMapper.selectByExample(null);
		return new PageResult(page.getTotal(), page.getResult());
	}

	/**
	 * 增加
	 */
	@Override
	public void add(TbData data) {
		dataMapper.insert(data);		
	}

	
	/**
	 * 修改
	 */
	@Override
	public void update(TbData data){
		dataMapper.updateByPrimaryKey(data);
	}	
	
	/**
	 * 根据ID获取实体
	 * @param id
	 * @return
	 */
	@Override
	public TbData findOne(Integer id){
		return dataMapper.selectByPrimaryKey(id);
	}

	/**
	 * 批量删除
	 */
	@Override
	public void delete(Integer[] ids) {
		for(Integer id:ids){
			dataMapper.deleteByPrimaryKey(id);
		}		
	}
	
	
		@Override
	public PageResult findPage(TbData data, int pageNum, int pageSize) {
		PageHelper.startPage(pageNum, pageSize);
		
		TbDataExample example=new TbDataExample();
		Criteria criteria = example.createCriteria();
		
		if(data!=null){			
				
		}
		
		Page<TbData> page= (Page<TbData>)dataMapper.selectByExample(example);		
		return new PageResult(page.getTotal(), page.getResult());
	}

		@Override
		public Result findData(TbData data) {
			TbDataExample example=new TbDataExample();
			Criteria criteria = example.createCriteria();
			
			if(data!=null){			
				if(data.getCid()!=null) {
					criteria.andCidEqualTo(data.getCid());
				}	
			}
			return new Result(true, dataMapper.selectByExample(example));
		}

		@Override
		public List<TbData> findAllByCid(Integer cid,String dates) {
			return dataMapper.findAllByCid(cid,dates);
		}
	
}
