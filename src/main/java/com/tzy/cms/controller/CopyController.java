package com.tzy.cms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.tzy.cms.domain.Copy;
import com.tzy.cms.service.CopyService;
//收藏夹
@Controller
public class CopyController {
@Autowired
private CopyService copyService; 
@RequestMapping("ToCopy")
public String ToCopy(Model model,@RequestParam(defaultValue = "1")Integer pageNum,String copytext) {	
	PageHelper.startPage(pageNum, 4);
	List<Copy>list=copyService.list(copytext);
	PageInfo<Copy> page = new PageInfo<>(list);
    model.addAttribute("page", page);
	return "list";
		
}

//删除记录
@RequestMapping("del")
public String del(Integer id) {
	copyService.del(id); 
	return "redirect:list";
	
}
	
}
