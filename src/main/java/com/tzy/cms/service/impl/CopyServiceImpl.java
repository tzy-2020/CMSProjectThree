package com.tzy.cms.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tzy.cms.dao.CopyMapper;
import com.tzy.cms.domain.Copy;
import com.tzy.cms.service.CopyService;
@Service
public class CopyServiceImpl implements CopyService {
@Autowired
private CopyMapper copyMapper;

@Override
public List<Copy> list(String copytext) {
	// TODO Auto-generated method stub
	return copyMapper.list(copytext);
}

@Override
public void del(Integer id) {
	// TODO Auto-generated method stub
	copyMapper.del(id);
}
	
}
