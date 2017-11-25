package com.itee.bingsheng.service;


import java.util.List;
import java.util.Map;


public interface ICommentService {

   	int saveComment(Map<String, Object> map)throws Exception;

	List<Map<String, Object>> queryAllComment(Map<String, Object> map)throws Exception;

	int updateComment(Map<String, Object> map)throws Exception;

	int getAllCount(Map<String, Object> map)throws Exception;

	int bathUpdateComment(Map<String, Object> map)throws Exception;

	/**
	 * 通过评论id获取当条评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCommentById(int id)throws Exception;
}
