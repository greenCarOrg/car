package com.itee.bingsheng.mybatis.dao;

import java.util.List;
import java.util.Map;


public interface CommentDao {

	/**
	 * 保存商品评论
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int saveComment(Map<String, Object> map)throws Exception;

	/**
	 * 查询所有评论信息
	 * @param map
	 * @return
	 * @throws Exception
	 */
	List<Map<String, Object>> queryAllComment(Map<String, Object> map)throws Exception;

	/**
	 * 更新评论状态
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int updateComment(Map<String, Object> map)throws Exception;

	/**
	 * 获取评论总数
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int getAllCount(Map<String, Object> map)throws Exception;

	/**
	 * 批量审核
	 * @param map
	 * @return
	 * @throws Exception
	 */
	int bathUpdateComment(Map<String, Object> map)throws Exception;

	/**
	 * 通过评论id获取当条评论
	 * @param id
	 * @return
	 * @throws Exception
	 */
	Map<String, Object> getCommentById(int id)throws Exception;
}
