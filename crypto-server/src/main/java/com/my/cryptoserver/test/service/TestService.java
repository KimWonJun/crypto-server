package com.my.cryptoserver.test.service;

import com.my.cryptoserver.test.dto.TestDTO;

import java.util.List;

public interface TestService
{
    public List<TestDTO> selectUserList() throws Exception;
}
