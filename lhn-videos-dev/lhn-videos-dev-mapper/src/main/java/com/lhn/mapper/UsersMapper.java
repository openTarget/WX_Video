package com.lhn.mapper;

import com.lhn.common.persistence.CrudDao;
import com.lhn.common.persistence.annotation.MyBatisDao;
import com.lhn.pojo.Users;
import com.lhn.utils.MyMapper;

@MyBatisDao
public interface UsersMapper extends MyMapper<Users> {
	
	/**
	 * @Description: 用户受喜欢数累加
	 */
	public void addReceiveLikeCount(String userId);
	
	/**
	 * @Description: 用户受喜欢数累减
	 */
	public void reduceReceiveLikeCount(String userId);
	
	/**
	 * @Description: 增加粉丝数
	 */
	public void addFansCount(String userId);
	
	/**
	 * @Description: 增加关注数
	 */
	public void addFollersCount(String userId);
	
	/**
	 * @Description: 减少粉丝数
	 */
	public void reduceFansCount(String userId);
	
	/**
	 * @Description: 减少关注数
	 */
	public void reduceFollersCount(String userId);
}