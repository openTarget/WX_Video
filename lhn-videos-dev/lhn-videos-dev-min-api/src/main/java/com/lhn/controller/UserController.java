package com.lhn.controller;

import com.fasterxml.jackson.databind.util.BeanUtil;
import com.lhn.pojo.Users;
import com.lhn.pojo.vo.UsersVO;
import com.lhn.service.UserService;
import com.lhn.utils.IMoocJSONResult;
import com.lhn.utils.MD5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
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
@Api(value = "用户相关业务的接口", tags = {"用户相关业务的controller"})
@RequestMapping("/user")
public class UserController extends BasicController{

	@Autowired
	private UserService userService;

    /**
     * 功能描述: <br>
     * 〈用户注销的方法〉
     *
     * @param userId：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/7 21:32
     */
    @ApiOperation(value = "用户注销", notes = "用户注销的方法")
    @ApiImplicitParam(name = "userId" , value = "用户id", required = true, dataType = "String", paramType = "query")
    @RequestMapping("/uplodeFace")
    public IMoocJSONResult uplodeFace(String userId , @RequestParam("file") MultipartFile[] files) throws IOException {

        if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorException("用户id不能为空····");
        }

        // 文件夹命名空间
        String fileSpace = "D:/imooc_voides_images";
        // 保存到数据中的路径
        String uploadPathDB = "/"+ userId + "/face";

        FileOutputStream fileOutputStream = null;
        InputStream inputStream = null;

        if (files !=null && files.length > 0) {
            // 获得上传文件的名字
            String fileName = files[0].getOriginalFilename();
            if (StringUtils.isNotBlank(fileName)){
                // 文件最终保存的路径
                String finalFacePath = fileSpace + uploadPathDB + "/" + fileName;
                // 设置数据库保存的路径
                uploadPathDB += ("/"+fileName);
                File outFile = new File(finalFacePath);
                if (outFile.getParentFile() != null || !outFile.getParentFile().isDirectory()){
                    // 创建夫文件夹
                    outFile.getParentFile().mkdirs();
                }
                try {
                    fileOutputStream = new FileOutputStream(outFile);
                    inputStream = files[0].getInputStream();
                    IOUtils.copy(inputStream,fileOutputStream);
                } catch (Exception e) {
                    e.printStackTrace();
                    return IMoocJSONResult.errorMsg("上传出错···");
                } finally {
                    if (fileOutputStream != null){
                        fileOutputStream.flush();
                        fileOutputStream.close();
                    }
                }
            } else {
              return IMoocJSONResult.errorMsg("上传出错···");
            }
        }
        Users users = new Users();
        users.setId(userId);
        users.setFaceImage(uploadPathDB);
        userService.updateUsetInfo(users);
        return IMoocJSONResult.ok(users);
    }

    @ApiOperation(value = "查询用户信息", notes = "查询用户信息")
    @ApiImplicitParam(name = "userId" , value = "用户id", required = true, dataType = "String", paramType = "query")
    @PostMapping("/query")
    public IMoocJSONResult query(String userId){

        if (StringUtils.isBlank(userId)){
            return IMoocJSONResult.errorMsg("用户id不能为空···");
        }

        Users usersInfo = userService.queryUserInfo(userId);
        UsersVO usersVO = new UsersVO();
        BeanUtils.copyProperties(usersInfo,usersVO);
        return IMoocJSONResult.ok(usersVO);
    }
}
