/**
 * Copyright (C), 2015-2018, XXX有限公司
 * FileName: FFMpegTest
 * Author:   lhn
 * Date:     2018/9/26 12:19
 * Description: 视频转码专用
 * History:
 * <author>          <time>          <version>          <desc>
 * 作者姓名           修改时间           版本号              描述
 */
package com.lhn.controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br> 
 * 〈视频转码专用〉
 *
 * @author lhn
 * @create 2018/9/26
 * @since 1.0.0
 */
public class MergeVideoMp3 {

    private String ffmpegEXE;

    public MergeVideoMp3(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath,String bgmInputPath,double secods,String videoOutputPath) throws IOException {
//        ffmpeg -i input.mp4 output.avi
//        ffmpeg.exe -i 测试视屏.mp4 -i "Avicii,Martin Garrix,Simon Aldred - Waiting For Love.mp3" -t 10 -y 1g.mp4
        List<String> command = new ArrayList<>();

        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add("-i");
        command.add(bgmInputPath);
        command.add("-t");
        command.add(String.valueOf(secods));
        command.add("-y");
        command.add(videoOutputPath);

        for (String comm:command) {
            System.out.print(comm+" ");
        }

        ProcessBuilder builder = new ProcessBuilder(command);
        Process process = builder.start();
        // 获得一个流
        InputStream errorStream = process.getErrorStream();
        InputStreamReader inputStreamReader = new InputStreamReader(errorStream);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String line = "";
        while ( (line = bufferedReader.readLine()) != null ){
        }
        if (bufferedReader != null){
            bufferedReader.close();
        }
        if (inputStreamReader != null){
            inputStreamReader.close();
        }
        if (errorStream != null){
            errorStream.close();
        }
    }

    public static void main(String[] args) {
        MergeVideoMp3 ffMpeg = new MergeVideoMp3("D:\\ruanjianzhongxin\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            ffMpeg.convertor("D:\\wenjian\\测试视屏.mp4","D:\\CloudMusic\\91#NO - 闭眼歌.mp3",10.1,"D:\\wenjian\\中国.avi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}