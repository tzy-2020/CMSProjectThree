package com.tzy.cms.dao;

import java.util.List;

import com.tzy.cms.domain.Category;

public interface CategoryMapper {
	
	// 根据栏目查询分类
	List<Category> selectsByChannelId(Integer channerId);
	
    int deleteByPrimaryKey(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    Category selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Category record);

    int updateByPrimaryKey(Category record);
}