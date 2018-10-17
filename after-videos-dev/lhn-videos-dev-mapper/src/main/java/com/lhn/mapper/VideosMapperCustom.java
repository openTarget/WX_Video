package com.lhn.mapper;


import com.lhn.pojo.Videos;
import com.lhn.pojo.vo.VideosVO;
import com.lhn.utils.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideosMapperCustom extends MyMapper<Videos> {

	/**
	 * @Description: 条件查询所有视频列表
	 */
	public List<VideosVO> queryAllVideos();


}