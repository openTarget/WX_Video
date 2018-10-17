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

import static com.sun.tools.internal.xjc.reader.Ring.add;

/**
 * 〈一句话功能简述〉<br> 
 * 〈视频转码专用〉
 *
 * @author lhn
 * @create 2018/9/26
 * @since 1.0.0
 */
public class FFMpegTest {

    private String ffmpegEXE;

    public FFMpegTest(String ffmpegEXE) {
        this.ffmpegEXE = ffmpegEXE;
    }

    public void convertor(String videoInputPath,String videoOutputPath) throws IOException {
//        ffmpeg -i input.mp4 output.avi
        List<String> command = new ArrayList<>();

        command.add(ffmpegEXE);
        command.add("-i");
        command.add(videoInputPath);
        command.add(videoOutputPath);

        for (String comm:command) {
            System.out.println(comm);
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
        FFMpegTest ffMpeg = new FFMpegTest("D:\\ruanjianzhongxin\\ffmpeg\\bin\\ffmpeg.exe");
        try {
            ffMpeg.convertor("D:\\wenjian\\ceshi.mp4","D:\\wenjian\\中国.avi");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}