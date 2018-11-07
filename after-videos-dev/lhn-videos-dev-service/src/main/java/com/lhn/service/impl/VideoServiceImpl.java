/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: UserServiceImpl
 * Author:   lhn
 * Date:     2018/8/6 15:10
 * Description: 这是用户服务类的实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lhn.service.impl;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lhn.mapper.SearchRecordsMapper;
import com.lhn.mapper.VideosMapper;
import com.lhn.mapper.VideosMapperCustom;
import com.lhn.pojo.Bgm;
import com.lhn.pojo.SearchRecords;
import com.lhn.pojo.Videos;
import com.lhn.pojo.vo.VideosVO;
import com.lhn.service.VideoService;
import com.lhn.utils.PagedResult;
import idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 〈一句话功能简述〉<br> 
 * 〈视频的实现类〉
 *
 * @author lhn
 * @create 2018/8/6
 * @since 1.0.0
 */

@Service("videoService")
public class VideoServiceImpl implements VideoService {

    @Autowired
    private Sid sid;

    @Autowired
    private VideosMapper videosMapper;

    @Autowired
    private VideosMapperCustom videosMapperCustom;

    @Autowired
    private SearchRecordsMapper searchRecordsMapper;

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public String saveVideo(Videos videos) {
        String id = sid.nextShort();
        videos.setId(id);
        videosMapper.insertSelective(videos);
        return id;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void updataVideo(String videoId, String coverPath) {
        Videos videos = new Videos();
        videos.setId(videoId);
        videos.setCoverPath(coverPath);
        videosMapper.updateVideoKeySelective(videos);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public PagedResult getAllVideos(Videos video, Integer isSaveRecord, Integer page, Integer pageSize) {
        String desc = video.getVideoDesc();
        if (isSaveRecord != null && isSaveRecord == 1) {
            SearchRecords  record = new SearchRecords();
            String recordId = sid.nextShort();
            record.setId(recordId);
            record.setContent(desc);
            searchRecordsMapper.insert(record);
        PageHelper.startPage(page, pageSize);
        }
        List<VideosVO> list = videosMapperCustom.queryAllVideos(desc);
        PageInfo<VideosVO> pagelist = new PageInfo<>(list);
        PagedResult pagedResult = new PagedResult();
        pagedResult.setPage(page);
        pagedResult.setTotal(pagelist.getPages());
        pagedResult.setRows(list);
        pagedResult.setRecords(pagelist.getTotal());
        return pagedResult;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<String> getHotWords() {
        List<String> hotwords = searchRecordsMapper.getHotwords();
        return hotwords;
    }

}