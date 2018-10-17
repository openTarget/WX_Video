package com.lhn.controller;

import com.lhn.utils.RedisOperator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BasicController {

	@Autowired
	public RedisOperator redis;

	public static final String USER_REDIS_SESSION = "user-redis-session";

	// 文件夹命名空间
	public static final String FILE_SPACE = "D:/imooc_voides_images";

	// ffmpeg所在地址
	public static final String FFMPEG_EXE = "D:\\ruanjianzhongxin\\ffmpeg\\bin\\ffmpeg.exe";

	// ffmpeg所在地址
	public static final Integer PAGE_SIZE = 5;
}
