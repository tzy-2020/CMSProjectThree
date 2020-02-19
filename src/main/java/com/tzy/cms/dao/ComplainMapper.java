package com.tzy.cms.dao;

import java.util.List;

import com.tzy.cms.domain.Complain;
import com.tzy.cms.vo.ComplainVO;

public interface ComplainMapper {
	
	int insert(Complain complain);
	
	//查询举报
	List<Complain> selects(ComplainVO complainVO);

}
