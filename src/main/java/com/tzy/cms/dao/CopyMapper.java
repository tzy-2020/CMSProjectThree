package com.tzy.cms.dao;
//收藏夹

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.tzy.cms.domain.Copy;

public interface CopyMapper {
	//收藏夹
    List<Copy> list(String copytext);
    //删除记录
	void del(@Param("id") Integer id);
	
}
