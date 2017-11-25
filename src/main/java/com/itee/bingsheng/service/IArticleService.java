package com.itee.bingsheng.service;


import com.itee.bingsheng.entity.Article;

import java.util.List;
import java.util.Map;


public interface IArticleService {

   	int saveArticle(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllArticle(Map<String, Object> map)throws Exception;

	int updateArticle(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;

	Article getArticle(int id)throws Exception;

	int deleteArtic(int id)throws Exception;
}
