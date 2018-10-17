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

import com.lhn.mapper.UsersMapper;
import com.lhn.pojo.Users;
import com.lhn.service.UserService;
import com.lhn.utils.MD5Utils;
import idworker.Sid;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;


/**
 * 〈一句话功能简述〉<br> 
 * 〈这是用户服务类的实现类〉
 *
 * @author lhn
 * @create 2018/8/6
 * @since 1.0.0
 */

@Service("userSerice")
public class UserServiceImpl  implements UserService{

    @Autowired
    private UsersMapper usersMapper;

    @Autowired
    private Sid sid;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public boolean queryUsernameIsExist(String userName) {
        Users users = new Users();
        users.setUsername(userName);
        Users selectOne = usersMapper.selectOne(users);
        return selectOne == null?true : false;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public void saveUser(Users users) {
        String userId = sid.nextShort();
        users.setId(userId);
        usersMapper.insert(users);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    @Override
    public Users queryUsersIsExist(Users users){
        Users users1 = new Users();
        users1.setUsername(users.getUsername());
        try {
            users1.setPassword(MD5Utils.getMD5Str(users.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return usersMapper.selectOne(users1);
    }

    @Override
    public void updateUsetInfo(Users users) {
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id", users.getId());
        usersMapper.updateByExampleSelective(users,userExample);
    }

    @Override
    public Users queryUserInfo(String userId){
        Example userExample = new Example(Users.class);
        Example.Criteria criteria = userExample.createCriteria();
        criteria.andEqualTo("id",userId);
        Users users = usersMapper.selectOneByExample(userExample);
        return users;
    }
}