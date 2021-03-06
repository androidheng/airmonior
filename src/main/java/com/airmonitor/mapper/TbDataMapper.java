package com.airmonitor.mapper;

import com.airmonitor.pojo.TbData;
import com.airmonitor.pojo.TbDataExample;

import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface TbDataMapper {
    int countByExample(TbDataExample example);

    int deleteByExample(TbDataExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(TbData record);

    int insertSelective(TbData record);

    List<TbData> selectByExample(TbDataExample example);

    TbData selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") TbData record, @Param("example") TbDataExample example);

    int updateByExample(@Param("record") TbData record, @Param("example") TbDataExample example);

    int updateByPrimaryKeySelective(TbData record);

    int updateByPrimaryKey(TbData record);

	List<TbData> findAllByCid(@Param("cid") Integer cid,@Param("dates") String dates);
}