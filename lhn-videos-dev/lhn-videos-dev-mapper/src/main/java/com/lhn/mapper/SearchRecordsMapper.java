package com.lhn.mapper;


import com.lhn.pojo.SearchRecords;
import com.lhn.utils.MyMapper;

import java.util.List;

public interface SearchRecordsMapper extends MyMapper<SearchRecords> {

	public List<String> getHotwords();
}