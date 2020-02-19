package com.tzy.cms.dao;

import java.util.List;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.tzy.cms.domain.Article;

// 此时自动具备增删改查功能
public interface ArticleRes extends ElasticsearchRepository<Article, Integer>{
     //根据标题查询
	List<Article> findByTitle(String key);
} 
