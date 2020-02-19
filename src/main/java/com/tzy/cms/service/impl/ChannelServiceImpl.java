package com.tzy.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzy.cms.dao.CategoryMapper;
import com.tzy.cms.dao.ChannelMapper;
import com.tzy.cms.domain.Category;
import com.tzy.cms.domain.Channel;
import com.tzy.cms.service.ChannelService;

@Service
public class ChannelServiceImpl implements ChannelService {

	@Resource
	private ChannelMapper channelMapper;
	@Resource
	private CategoryMapper categoryMapper;
	
	@Override
	public List<Channel> selects() {
		return channelMapper.selects();
	}

	@Override
	public List<Category> selectsByChannelId(Integer channerId) {
		return categoryMapper.selectsByChannelId(channerId);
	}

}
