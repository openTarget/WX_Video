package com.lhn.mapper;


import com.lhn.pojo.Bgm;
import com.lhn.utils.MyMapper;

public interface BgmMapper extends MyMapper<Bgm>{

    public Bgm queryBgmId(String bgmId);

}