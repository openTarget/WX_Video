package com.lhn.mapper;


import com.lhn.pojo.Videos;
import com.lhn.utils.MyMapper;

public interface VideosMapper extends MyMapper<Videos> {

    void updateVideoKeySelective(Videos videos);

}