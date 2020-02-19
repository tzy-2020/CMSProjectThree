package com.tzy.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageInfo;
import com.tzy.cms.dao.ArticleRes;
import com.tzy.cms.domain.Article;
import com.tzy.cms.service.ArticleService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:spring-beans.xml")
public class ImportMysql2Es {

	@Autowired
	ArticleRes articleRes;
	
	@Autowired
	ArticleService articleService;
	
	@Test
	public void testImportMysql2Es() {
		// 1.从mysql中查询文章信息
		Article article = new Article();
		article.setStatus(1);
		PageInfo<Article> selects = articleService.selects(article,1,10000);
		// 2. 把查询出来的文章批量保存到es索引库
		articleRes.saveAll(selects.getList());
	}
	
	
}
