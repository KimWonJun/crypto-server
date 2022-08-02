package com.my.cryptoserver.test.service;

import com.my.cryptoserver.test.vo.TestVO;
import com.my.cryptoserver.test.mapper.TestMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TestServiceImpl implements TestService
{
    @Autowired
    private TestMapper testMapper;

    @Override
    public List<TestVO> selectUserList() throws Exception {
        return testMapper.selectUserList();
    }
}
