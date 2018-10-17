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

import com.lhn.mapper.BgmMapper;
import com.lhn.pojo.Bgm;
import com.lhn.service.BgmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 〈一句话功能简述〉<br> 
 * 〈这是用户服务类的实现类〉
 *
 * @author lhn
 * @create 2018/8/6
 * @since 1.0.0
 */

@Service("bgmservice")
public class BgmServiceImpl implements BgmService{

    @Autowired
    private BgmMapper bgmMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Bgm> queryBgmList() {
        List<Bgm> bgms = bgmMapper.selectAll();
        return bgms;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Bgm queryBgmId(String bgmId) {
        return bgmMapper.queryBgmId(bgmId);
    }
}