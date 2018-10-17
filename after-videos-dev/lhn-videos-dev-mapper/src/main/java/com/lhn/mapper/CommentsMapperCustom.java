package com.lhn.mapper;

import java.util.List;

import com.lhn.pojo.Comments;
import com.lhn.pojo.vo.CommentsVO;
import com.lhn.utils.MyMapper;

public interface CommentsMapperCustom extends MyMapper<Comments> {
	
	public List<CommentsVO> queryComments(String videoId);
}