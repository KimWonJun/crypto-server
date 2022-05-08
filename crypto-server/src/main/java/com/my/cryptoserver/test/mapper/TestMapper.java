package com.my.cryptoserver.test.mapper;

import com.my.cryptoserver.test.dto.TestDTO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface TestMapper
{
    List<TestDTO> selectUserList();
}
