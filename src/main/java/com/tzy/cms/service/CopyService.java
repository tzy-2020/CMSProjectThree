package com.tzy.cms.service;

import java.util.List;

import com.tzy.cms.domain.Copy;

public interface CopyService {

	List<Copy> list(String copytext);
    //删除数仓记录
	void del(Integer id);
	
}
