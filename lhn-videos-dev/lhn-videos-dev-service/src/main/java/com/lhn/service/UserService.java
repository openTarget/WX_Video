package com.lhn.service;

import com.lhn.pojo.Users;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 功能描述: <br>
 * 〈用户注册的服务层〉
 * @return:
 * @since: 1.0.0
 * @Author:lhn
 * @Date: 2018/8/5 18:18
 */
@Service
@Transactional(readOnly = true)
public interface UserService{
    /**
     * 功能描述: <br>
     * 〈判断用户是否存在〉
     *
     * @param userName:用户名
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/6 15:06
     */
    public boolean queryUsernameIsExist(String userName);

    /**
     * 功能描述: <br>
     * 〈保存用户信息〉
     *
     * @param users：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/6 15:09
     */
    public void saveUser(Users users);

    /**
     * 功能描述: <br>
     * 〈用户登陆的方法〉
     *
     * @param users：用户对象
     * @return:
     * @since: 1.0.0
     * @Author:lhn
     * @Date: 2018/8/7 21:34
     */
    public Users queryUsersIsExist(Users users) throws Exception;

    /**
    *  用户上传图片的方法d
    */
    public void updateUsetInfo(Users users);

    /**
    *  用户登录成功后查询用户id
    */
    public Users queryUserInfo(String userId);
}

