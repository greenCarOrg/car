package com.itee.bingsheng.service.imp;


import com.itee.bingsheng.mybatis.dao.FeedbookDao;
import com.itee.bingsheng.service.IFeedbookService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class FeedbookService implements IFeedbookService{
	@Resource
	FeedbookDao dao;
	/**
	 * 前端微信用户反馈
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int submitFeedbook(Map<String,Object> map)throws Exception{
		return dao.submitFeedbook(map);
	}



	/**
	 * 获取用户反馈列表
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<Map<String,Object>> getFeedbookList(Map<String,Object> map)throws Exception{
		return dao.getFeedbookList(map);
	}


	/**
	 * 获取用户反馈数量
	 * @return
	 * @throws Exception
	 */
	@Override
	public int getFeedbookCount(Map<String, Object> map)throws Exception{
		return dao.getFeedbookCount(map);
	}

	/**
	 * 更新用户反馈
	 * @param map
	 * @return
	 * @throws Exception
	 */
	@Override
	public int updateFeedbook(Map<String, Object> map)throws Exception{
		return dao.updateFeedbook(map);
	}


}
