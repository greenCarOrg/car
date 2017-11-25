package com.itee.bingsheng.mybatis.dao;

import com.itee.bingsheng.entity.Article;

import java.util.List;
import java.util.Map;


public interface ArticleDao {

    int saveArticle(Map<String, Object> map)throws Exception;

    List<Map<String, Object>> queryAllArticle(Map<String, Object> map)throws Exception;

    int updateArticle(Map<String, Object> map)throws Exception;

    int getAllCount1(Map<String, Object> map)throws Exception;

    Article getArticle(int id)throws Exception;

    int deleteArtic(int id)throws Exception;
}
