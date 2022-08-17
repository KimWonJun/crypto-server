package com.my.cryptoserver.test.service;

import com.my.cryptoserver.test.vo.TestVO;

import java.util.List;

public interface TestService
{
    public List<TestVO> selectUserList() throws Exception;
}
