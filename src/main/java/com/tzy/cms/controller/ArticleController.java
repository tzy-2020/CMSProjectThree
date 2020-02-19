package com.tzy.cms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.github.pagehelper.PageInfo;
import com.tzy.cms.dao.ArticleRes;
import com.tzy.cms.domain.Article;
import com.tzy.util.HLUtils;

@RequestMapping("article")
@Controller
public class ArticleController {
	@Autowired
	ArticleRes articleRes;
	@Autowired
	ElasticsearchTemplate elasticsearchTemplate;
	@SuppressWarnings("unchecked")
	@RequestMapping("search")
	public String search(String key,Model model,@RequestParam(defaultValue = "1")Integer page,@RequestParam(defaultValue = "2")Integer pageSize) {
		 //List<Article> list = articleRes.findByTitle(key);
		//定义一个开始的时间
		 long start = System.currentTimeMillis();
		PageInfo<Article> info = (PageInfo<Article>) HLUtils.findByHighLight(elasticsearchTemplate, Article.class, 1, 2, new String[] {"title"}, "id", key);
		//定义一个结束的时间
		 long end = System.currentTimeMillis();
		 System.err.println("es查询一共花费了"+(end-start)+"毫秒");
		 
		 model.addAttribute("key", key);
		 model.addAttribute("info", info);
		 return "index/index";
	}
}
