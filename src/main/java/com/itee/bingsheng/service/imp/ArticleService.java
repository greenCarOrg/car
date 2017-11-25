package com.itee.bingsheng.service.imp;

import com.itee.bingsheng.entity.Article;
import com.itee.bingsheng.mybatis.dao.ArticleDao;
import com.itee.bingsheng.service.IArticleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ArticleService implements IArticleService {

    @Resource
    ArticleDao dao;
    @Override
    public int saveArticle (Map<String,Object> map)throws Exception{
        return dao.saveArticle(map);
    }

    @Override
    public List<Map<String, Object>> queryAllArticle(Map<String,Object> map)throws Exception{
        return dao.queryAllArticle(map);
    }

    @Override
    public int updateArticle(Map<String,Object> map)throws Exception{
        return dao.updateArticle(map);
    }

    @Override
    public int getAllCount(Map<String,Object> map)throws Exception{
        return dao.getAllCount1(map);
    }

    @Override
    public Article getArticle(int id)throws Exception{
        return  dao.getArticle(id);
    }

    @Override
    public int deleteArtic(int id)throws Exception{
        return dao.deleteArtic(id);
    }
}