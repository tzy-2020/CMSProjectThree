package com.tzy.cms.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.tzy.cms.dao.SlideMapper;
import com.tzy.cms.domain.Slide;
import com.tzy.cms.service.SlideService;

@Service
public class SlideServiceImpl implements SlideService {

	@Resource
	private SlideMapper slideMapper;
	
	@Override
	public List<Slide> selects() {
		return slideMapper.selects();
	}

}
