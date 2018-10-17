package com.lhn.controller;

import com.lhn.service.BgmService;
import com.lhn.utils.IMoocJSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import javafx.scene.shape.VLineTo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 功能描述: <br>
 * 〈用户注册和登陆的控制器〉
 * @return:
 * @since: 1.0.0
 * @Author:lhn
 * @Date: 2018/8/5 17:21
 */
@RestController
@Api(value = "背景音乐业务的接口", tags = {"背景音乐业务的controller"})
@RequestMapping("/bgm")
public class BgmController extends BasicController{

	@Autowired
	private BgmService bgmService;

    @ApiOperation(value = "bgm列表查找", notes = "查找bgm列表")
    @PostMapping("/list")
    public IMoocJSONResult uplodeFace(){
        return IMoocJSONResult.ok(bgmService.queryBgmList());
    }
}
