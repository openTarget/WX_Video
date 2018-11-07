package com.lhn.service;

import com.lhn.pojo.Bgm;
import com.lhn.pojo.Videos;
import com.lhn.utils.PagedResult;

import java.util.List;

public interface VideoService {

    /**
    *  保存视频
    */
    public String saveVideo(Videos videos);

    /**
    *  保存视频路径
    */
    public void updataVideo(String videoId, String coverPath);

    /**
    *  分页查询视频列表
    */
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize);

    /**
    *  获取热搜关键字
    */
    public List<String> getHotWords();
}
