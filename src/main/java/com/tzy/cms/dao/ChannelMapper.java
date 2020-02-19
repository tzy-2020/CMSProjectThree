package com.tzy.cms.dao;

import java.util.List;

import com.tzy.cms.domain.Channel;

public interface ChannelMapper {
	
	// 所有栏目
	List<Channel> selects();
	
    int deleteByPrimaryKey(Integer id);

    int insert(Channel record);

    int insertSelective(Channel record);

    Channel selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Channel record);

    int updateByPrimaryKey(Channel record);
}