package com.tzy.cms.kafka;

import javax.annotation.Resource;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.MessageListener;

import com.alibaba.fastjson.JSON;
import com.tzy.cms.domain.ArticleWithBLOBs;
import com.tzy.cms.service.ArticleService;

public class MsgListener implements MessageListener<String, String>{

	@Resource
	private ArticleService articleService;
	
	@Override
	public void onMessage(ConsumerRecord<String, String> data) {
		String value = data.value();
		//把消息转成json
		System.err.println("接收消息:-------");
		//如果是以这个开头，说明是走流量肖锋的业务
		if (value.startsWith("user_view")) {
			String[] split = value.split("==");
			String id=split[1];
		    //1.根据ID查询文章执行浏览量+1
			ArticleWithBLOBs bs = articleService.selectByPrimaryKey(Integer.parseInt(id));
		   //执行+1操作
			bs.setHits(bs.getHits()+1);
			//执行后更新到数据库
			articleService.updateByPrimaryKeySelective(bs);
			System.err.println("浏览量+1成功");
			
		} else {
			//读取爬虫信息保存到MySQL数据库
			ArticleWithBLOBs po = JSON.parseObject(value,ArticleWithBLOBs.class);
			//保存到MySQL
			articleService.insertSelective(po);
		}
		
		
	}

}
