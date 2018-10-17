package com.lhn.service;

import com.lhn.pojo.Bgm;

import java.util.List;

public interface BgmService {

    public List<Bgm> queryBgmList();

    /**
    *  通过id查询指定信息
    */
    public Bgm  queryBgmId(String bgmId);
}
