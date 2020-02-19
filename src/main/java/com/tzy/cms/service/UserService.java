package com.tzy.cms.service;


import com.github.pagehelper.PageInfo;
import com.tzy.cms.domain.User;

public interface UserService {

	User login(User user);
	
	// 修改  支持动态sql
    int updateByPrimaryKeySelective(User record);
    
    // 列表
    PageInfo<User> selects(String username,Integer page,Integer pageSize);
    
    // 注册用户
    int insertSelective(User record);
 
}
