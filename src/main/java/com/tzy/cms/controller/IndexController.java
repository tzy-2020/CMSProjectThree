package com.tzy.cms.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.swing.Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.github.pagehelper.PageInfo;
import com.tzy.cms.domain.Article;
import com.tzy.cms.domain.ArticleWithBLOBs;
import com.tzy.cms.domain.Category;
import com.tzy.cms.domain.Channel;
import com.tzy.cms.domain.Comment;
import com.tzy.cms.domain.Complain;
import com.tzy.cms.domain.Slide;
import com.tzy.cms.domain.User;
import com.tzy.cms.service.ArticleService;
import com.tzy.cms.service.ChannelService;
import com.tzy.cms.service.CommentService;
import com.tzy.cms.service.ComplainService;
import com.tzy.cms.service.SlideService;
import com.tzy.cms.util.CMSException;

@Controller
public class IndexController {

	@Resource
	private ChannelService channelService;

	@Resource
	private ArticleService articleService;

	@Resource
	private SlideService slideService;

	@Resource
	private CommentService commentService;
	
	@Resource
	private ComplainService complainService;

	@SuppressWarnings("rawtypes")
	@Autowired
	RedisTemplate redisTemplate; 
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value = { "", "/", "index" })
	public String index(Model model, Article article, @RequestParam(defaultValue = "1") Integer page,
			@RequestParam(defaultValue = "10") Integer pageSize) {
		// 0封装查询条件
		article.setStatus(1);
		model.addAttribute("article", article);
		// 1.查询出所有的栏目
		List<Channel> channels = channelService.selects();
		model.addAttribute("channels", channels);

		// 如果没有选择栏目,则默认选择推荐的文章
		Article last = new Article();
		if (article.getChannelId() == null) {
			// 1.查询广告
			List<Slide> slides = slideService.selects();
			model.addAttribute("slides", slides);

			Article a2 = last;
			a2.setHot(1); // 推荐文章的标志
			a2.setStatus(1); // 审核过的文章
			// 2.查询栏目下所有的文章
			
			List<Article> reidsArticles = (List<Article>) redisTemplate.opsForValue().get("hot_articles");
			// 2. 判断redis中的最新文章有没有
			// 3. 如果为空
			if(reidsArticles==null || reidsArticles.size() == 0) {
				// 4. 就从mysql中查询,并且存入redis,返回个前台
				PageInfo<Article> info = articleService.selects(a2, 1, 5);
				System.err.println("从mysql中查询的最新文章......");
				// 4.1 放入redis
				redisTemplate.opsForValue().set("hot_articles", info.getList());
				System.err.println(info.getList().toArray().length);
				model.addAttribute("info", info);
			}else {
				// 5. 如果非空
				// 6. 直接把redis的数据返回给前台
				System.err.println("从redis中查询的最新文章......");
				int size = reidsArticles.size();
				System.err.println(size);
				 PageInfo<Article> info = articleService.selects(a2, page, pageSize);
				 model.addAttribute("info", info);
			}
			
			
			/*
			 * PageInfo<Article> info = articleService.selects(a2, page, pageSize);
			 * model.addAttribute("info", info);
			 */
			 

		}

		// 如果栏目不为空,则查询栏目下的所有分类
		if (article.getChannelId() != null) {
			List<Category> categorys = channelService.selectsByChannelId(article.getChannelId());

			// 查询栏目下所有的文章
			PageInfo<Article> info = articleService.selects(article, page, pageSize);
			model.addAttribute("info", info);
			model.addAttribute("categorys", categorys);

			// 如果分类不为空,则查询分类下 文章
			if (article.getCategoryId() != null) {
				PageInfo<Article> info2 = articleService.selects(article, page, pageSize);
				model.addAttribute("info", info2);
			}
		}
		// 页面右侧显示最近发布的10篇文章
		Article last1 = new Article();
		last.setStatus(1);
		// 这里需要用redis作为缓存优化的最新文章
		// 1. 从redis中查询最新文章
		List<Article> reidsArticles = (List<Article>) redisTemplate.opsForValue().get("new_articles");
		// 2. 判断redis中的最新文章有没有
		// 3. 如果为空
		if(reidsArticles==null || reidsArticles.size() == 0) {
			// 4. 就从mysql中查询,并且存入redis,返回个前台
			PageInfo<Article> lastInfo = articleService.selects(last1, 1, 5);
			System.err.println("从mysql中查询的最新文章......");
			// 4.1 放入redis
			redisTemplate.opsForValue().set("new_articles", lastInfo.getList());
			System.err.println(lastInfo.getList().toArray().length);
			model.addAttribute("lastInfo", lastInfo);
		}else {
			// 5. 如果非空
			// 6. 直接把redis的数据返回给前台
			System.err.println("从redis中查询的最新文章......");
			int size = reidsArticles.size();
			System.err.println(size);
			PageInfo<Article> lastInfo = new PageInfo<>(reidsArticles);
			model.addAttribute("lastInfo", lastInfo);
		}
		return "index/index";
	}
	/*
	 * //注入kafka的模板
	 * 
	 * @Autowired KafkaTemplate<String, String> kafkaTemplate;
	 */

	@Autowired
	ThreadPoolTaskExecutor executor;
	// 查询单个文章
	@GetMapping("article")
	public String article(Integer id, Model model,HttpServletRequest request) {
		ArticleWithBLOBs article = articleService.selectByPrimaryKey(id);
		model.addAttribute("article", article);
	   //	获取用户ID
		//当亲拥护用户浏览文章时在kafkafa发送文章ID
		//kafkaTemplate.send("1708d", "user_view=="+id+"");
		
		/*
		 * 现在请你利用Redis提高性能，当用户浏览文章时， 将“Hits_${文章ID}_${用户IP地址}”为key，
		 * 查询Redis里有没有该key，如果有key，则不做任何操作。 如果没有，则使用Spring线程池异步执行数据库加1操作，
		 * 并往Redis保存key为Hits_${文章ID}_${用户IP地址}， value为空值的记录，而且有效时长为5分钟。
		 */
		//获取用户Ip的方法
		String user_ip =request.getRemoteAddr();
		String key="Hits"+id+user_ip; 
	    String redisKey = (String) redisTemplate.opsForValue().get(key);
		if (redisKey==null) {
			executor.execute(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					//在这里就可以写具体的逻辑了
					//数据可+1操作（根据id从数据库中查询文章对象）
				//	ArticleWithBLOBs  bs = articleService.selectByPrimaryKey(id);
					//设置+1操作
					article.setHits(article.getHits()+1);
					  //	更新到数据库
					articleService.updateByPrimaryKeySelective(article);
					//并往Redis保存key为Hits_${文章ID}_${用户IP地址}，
					//value为空值的记录，而且有效时长为5分钟。
					
					redisTemplate.opsForValue().set(key, "", 5, TimeUnit.MINUTES);
				}
			});
			
		} else {

		}
		
		

		// 查询出评论
		Comment comment = new Comment();
		comment.setArticleId(article.getId());
		PageInfo<Comment> info = commentService.selects(comment, 1, 100);
		model.addAttribute("info", info);
		return "/index/article";
	}

	/**
	 * 评论
	 * 
	 * @Title: addComment
	 * @Description: TODO
	 * @param comment
	 * @param request
	 * @return
	 * @return: boolean
	 */
	@ResponseBody
	@PostMapping("addComment")
	public boolean addComment(Comment comment, HttpServletRequest request) {
		HttpSession session = request.getSession();
		// 获取session中的用户对象
		User user = (User) session.getAttribute("user");
		if (null == user)
			return false;// 没有登录，不能评论
		comment.setUserId(user.getId());
		comment.setCreated(new Date());
		return commentService.insert(comment) > 0;
	}

	// 去举报
	@GetMapping("complain")
	public String complain(Model model, Article article, HttpSession session) {
		User user = (User) session.getAttribute("user");
		if (null != user) {// 如果有户登录
			article.setUser(user);// 封装举报人和举报的文章
			model.addAttribute("article", article);
			return "index/complain";// 转发到举报页面
		}

		return "redirect:/passport/login";// 没有登录，先去登录
	}

	// 执行举报
	@ResponseBody
	@PostMapping("complain")
	public boolean complain(Model model,MultipartFile file, Complain complain) {
		if (null != file && !file.isEmpty()) {
			String path = "d:/pic/";
			String filename = file.getOriginalFilename();
			String newFileName = UUID.randomUUID() + filename.substring(filename.lastIndexOf("."));
			File f = new File(path, newFileName);
			try {
				file.transferTo(f);
				complain.setPicurl(newFileName);

			} catch (IllegalStateException | IOException e) {
				e.printStackTrace();
			}
		}
		
		try {
			complainService.insert(complain);
			return true;
		} catch (CMSException e) {
			e.printStackTrace();
			model.addAttribute("error",e.getMessage());
		}catch (Exception e) {
			e.printStackTrace();
			model.addAttribute("error","系统错误,联系管理员");
		}
		return false;
	}
}
