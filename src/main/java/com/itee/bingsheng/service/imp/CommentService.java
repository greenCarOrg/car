package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.mybatis.dao.CommentDao;
import com.itee.bingsheng.service.ICommentService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CommentService implements ICommentService {

	@Resource
	CommentDao dao;
	@Override
	public int saveComment (Map<String,Object> map)throws Exception{
		return dao.saveComment(map);
	}

	@Override
	public List<Map<String, Object>> queryAllComment(Map<String,Object> map)throws Exception{
		return dao.queryAllComment(map);
	}

	@Override
	public int updateComment(Map<String,Object> map)throws Exception{
		return dao.updateComment(map);
	}

	@Override
	public int getAllCount(Map<String,Object> map)throws Exception{
		return dao.getAllCount(map);
	}

	@Override
	public int bathUpdateComment(Map<String, Object> map)throws Exception{
		return dao.bathUpdateComment(map);
	}

	/**
	 * 通过评论id获取当条评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	@Override
	public Map<String, Object> getCommentById(int id)throws Exception{
		return dao.getCommentById(id);
	}
}
