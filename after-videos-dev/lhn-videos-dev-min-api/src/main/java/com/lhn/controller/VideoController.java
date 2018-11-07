package com.lhn.controller;

import com.lhn.common.enums.VideoStatusEnum;
import com.lhn.pojo.Bgm;
import com.lhn.pojo.Videos;
import com.lhn.service.BgmService;
import com.lhn.service.VideoService;
import com.lhn.utils.FetchVideoCover;
import com.lhn.utils.IMoocJSONResult;
import com.lhn.utils.PagedResult;
import io.swagger.annotations.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 功能描述: <br>
 * 〈用户注册和登陆的控制器〉
 * @return:
 * @since: 1.0.0
 * @Author:lhn
 * @Date: 2018/8/5 17:21
 */
@RestController
@Api(value = "视频相关业务的接口", tags = {"视频相关业务的controller"})
@RequestMapping("/video")
public class VideoController extends BasicController{

    @Autowired
    private BgmService bgmService;

    @Autowired
    private VideoService videoService;

    /**
     * 功能描述: <br>
     * 〈用户上传视频的方法〉
     *
     * @param userId：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/7 21:32
     */
    @ApiOperation(value = "上传视频", notes = "上传的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId" , value = "用户id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bmgId" , value = "背景音乐的id", required = false, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoSeconds" , value = "视频的秒数", required = true, dataType = "double", paramType = "form"),
            @ApiImplicitParam(name = "videoWidth" , value = "视频的宽度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "videoHeight" , value = "视频的长度", required = true, dataType = "int", paramType = "form"),
            @ApiImplicitParam(name = "des" , value = "视频描述", required = false, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uplode", headers = "content-type=multipart/form-data")
    public IMoocJSONResult uplode(String userId , String bgmId, double videoSeconds, int videoWidth, int videoHeight,String des,
                                  @ApiParam(value = "短视频", required = true)
                                  MultipartFile file) throws IOException {

        if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorException("用户id不能为空····");
        }

        // 保存到数据中的路径
        String uploadPathDB = "/"+ userId + "/video";
        String coverPathDB = "/"+ userId + "/video";
        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件最终保存的路径
        String finalFacePath = "";
        if (file !=null ) {
            // 获得上传文件的名字
            String fileName = file.getOriginalFilename();
            // 截取上传视频的名字
            String fileNamePrefix = fileName.split("\\.")[0];

            if (StringUtils.isNotBlank(fileName)) {
                finalFacePath = FILE_SPACE + uploadPathDB + "/" + fileName;
                // 设置数据库保存的路径
                uploadPathDB += ("/" + fileName);
                // 视频封面的相对路径
                coverPathDB = coverPathDB + "/"+fileNamePrefix + ".jpg";

                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                    // 创建夫文件夹
                    outFile.getParentFile().mkdirs();
                }
                try {
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    return IMoocJSONResult.errorMsg("上传出错···");
                } finally {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            } else {
                return IMoocJSONResult.errorMsg("上传出错···");
            }
        }
        // 判断bgm是否为空，不为空的话查询bgm的信息，与视频进行合并，生成新的视频进行保存。
        if (StringUtils.isNotBlank(bgmId)){
            Bgm bgm = bgmService.queryBgmId(bgmId);
            String mp3InputPath = FILE_SPACE + bgm.getPath();

            MergeVideoMp3 tool = new MergeVideoMp3(FFMPEG_EXE);
            String videoInputPath = finalFacePath;

            String videoOutputName = UUID.randomUUID().toString() + ".mp4";
            uploadPathDB = "/"+ userId + "/video" + "/" + videoOutputName;
            finalFacePath = FILE_SPACE + uploadPathDB;
            tool.convertor(videoInputPath, mp3InputPath, videoSeconds,finalFacePath);
        }
        System.out.println("up="+uploadPathDB);
        System.out.println("fi="+finalFacePath);

        //对视频进行截图
        FetchVideoCover videoInfo = new FetchVideoCover(FFMPEG_EXE);
        try {
            videoInfo.getCover(finalFacePath,FILE_SPACE+coverPathDB);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // 保存视频到数据库
        Videos videos = new Videos();
        videos.setAudioId(bgmId);
        videos.setUserId(userId);
        videos.setVideoSeconds((float)videoSeconds);
        videos.setVideoHeight(videoHeight);
        videos.setVideoWidth(videoWidth);
        videos.setVideoDesc(des);
        videos.setVideoPath(uploadPathDB);
        videos.setCoverPath(coverPathDB);
        videos.setStatus(VideoStatusEnum.SUCCESS.value);
        videos.setCreateTime(new Date());

        String videoId = videoService.saveVideo(videos);
        return IMoocJSONResult.ok();
    }

    /**
     * 功能描述: <br>
     * 〈用户上传视频的方法〉
     *
     * @param userId：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/7 21:32
     */
    @ApiOperation(value = "上传视频封面", notes = "上传视频封面的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId" , value = "用户id", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "videoId" , value = "视频id", required = true, dataType = "String", paramType = "form")
    })
    @PostMapping(value = "/uplodeCover", headers = "content-type=multipart/form-data")
    public IMoocJSONResult uplodeCover(String userId ,String videoId ,
                                       @ApiParam(value = "封面", required = true) MultipartFile file) throws IOException {

        if (StringUtils.isBlank(videoId) || StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorException("视频主键或用户id不能为空····");
        }

        // 保存到数据中的路径
        String uploadPathDB = "/"+ userId + "/video";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;
        // 文件最终保存的路径
        String finalCoverPath = "";
        if (file !=null ) {
            // 获得上传文件的名字
            String fileName = file.getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)) {
                finalCoverPath = FILE_SPACE + uploadPathDB + "/" + fileName;
                // 设置数据库保存的路径
                uploadPathDB += ("/" + fileName);
                File outFile = new File(finalCoverPath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()) {
                    // 创建夫文件夹
                    outFile.getParentFile().mkdirs();
                }
                try {
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = file.getInputStream();
                    IOUtils.copy(inputStream, fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    return IMoocJSONResult.errorMsg("上传出错···");
                } finally {
                    if (fileOutputStream != null) {
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            } else {
                return IMoocJSONResult.errorMsg("上传出错···");
            }
        }
        videoService.updataVideo(videoId,uploadPathDB);
        return IMoocJSONResult.ok();
    }

    /**
     * 功能描述: <br>
     * 〈分页查询和热搜查询〉
     *
     * @param：isSaveRecord
     *        1：保存
     *        0：不保存
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/11/6 18:06
     */
    @ApiOperation(value = "分页查询和热词搜索分页查询", notes = "分页查询和热词搜索分页查询的方法")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "page" , value = "当前页数", required = true, dataType = "String", paramType = "form"),
            @ApiImplicitParam(name = "bmgId" , value = "每一页的数量", required = true, dataType = "String", paramType = "form"),
    })
    @PostMapping(value = "/showAll")
    public IMoocJSONResult showAll(@RequestBody Videos videos, Integer isSaveRecord, Integer page){
        if (page == null){
            page = 1;
        }
        PagedResult allVideos = videoService.getAllVideos(videos, isSaveRecord, page, PAGE_SIZE);
        return IMoocJSONResult.ok(allVideos);
    }

    @ApiOperation(value = "用户热词", notes = "热词展现的方法")
    @GetMapping(value = "/hot")
    public IMoocJSONResult hot(){
        List<String> hotWords = videoService.getHotWords();
        return IMoocJSONResult.ok(hotWords);
    }
}
