package com.lhn.controller;

import com.lhn.pojo.Users;
import com.lhn.pojo.vo.UsersVO;
import com.lhn.service.UserService;
import com.lhn.utils.IMoocJSONResult;
import com.lhn.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@Api(value = "用户登陆和注册的接口", tags = {"注册和登陆的controller"})
public class RegistLoginController extends BasicController{

	@Autowired
	private UserService userService;

    @ApiOperation(value = "用户组成", notes = "用户组成的接口")
    @PostMapping("/regist")
    /** @requestBody 这样就不会再被解析为跳转路径，而是直接将user对象写入 HTTP 响应正文中 */
    public IMoocJSONResult regist(@RequestBody Users users) throws Exception {
        // 1、判断用户名和密码不为空
        if (StringUtils.isBlank(users.getUsername()) && StringUtils.isBlank(users.getPassword())){
            return IMoocJSONResult.errorMsg("用户名或密码不能为空！");
        }
        //2、判断用户名是否已经存在
        if (!userService.queryUsernameIsExist(users.getUsername())){
            return IMoocJSONResult.errorMsg("用户名已经被注册！<br/>请使用其他用户注册。");
        }else {
            users.setUsername(users.getUsername());
            users.setPassword(MD5Utils.getMD5Str(users.getPassword()));
            users.setFansCounts(0);
            users.setReceiveLikeCounts(0);
            users.setFollowCounts(0);
            users.setNickname(users.getNickname());
            //3、保存用户信息注册成功
            userService.saveUser(users);
            UsersVO usersVO = setUserRedisSessionToken(users);
            return IMoocJSONResult.ok(usersVO);
        }
    }

    public UsersVO setUserRedisSessionToken(Users users){
        String uniqueToken = UUID.randomUUID().toString();
        redis.set(USER_REDIS_SESSION + ":"+ users.getId(),uniqueToken , 1000 * 60 * 30);

        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(users, usersVO);
        usersVO.setUserToken(uniqueToken);
        return usersVO;
    }

    /**
     * 功能描述: <br>
     * 〈用户登陆的方法〉
     *
     * @param users：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/7 21:32
     */
    @ApiOperation(value = "用户登陆", notes = "用户登录的方法")
    @PostMapping("/login")
    public IMoocJSONResult login(@RequestBody Users users) throws Exception {
        Users users1 = userService.queryUsersIsExist(users);
        Thread.sleep(1000);
        if (users1 == null){
            return IMoocJSONResult.errorMsg("没有找到用户信息，用户名或密码输入有误！");
        }
        UsersVO usersVO = setUserRedisSessionToken(users1);
        usersVO.setPassword("");
        return IMoocJSONResult.ok(usersVO);
    }

    /**
     * 功能描述: <br>
     * 〈用户登陆的方法〉
     *
     * @param users：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/7 21:32
     */
    @ApiOperation(value = "用户注销", notes = "用户注销的方法")
    @ApiImplicitParam(name = "userId" , value = "用户id", required = true, dataType = "String", paramType = "query")
    @GetMapping("/logout")
    public IMoocJSONResult logout(String userId){
        redis.del( USER_REDIS_SESSION+ ":" + userId);
        return IMoocJSONResult.ok();
    }
}
