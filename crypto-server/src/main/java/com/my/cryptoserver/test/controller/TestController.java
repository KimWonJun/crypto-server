package com.my.cryptoserver.test.controller;

import com.my.cryptoserver.test.dto.TestDTO;
import com.my.cryptoserver.test.service.TestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@Controller
@RequestMapping(value="/test")
public class TestController
{
    @Autowired
    private TestService testService;

    private static final Logger log = LoggerFactory.getLogger(TestController.class);

    @RequestMapping(value="/user", method= RequestMethod.GET)
    public ResponseEntity<?> selectUserList() throws Exception
    {
        List<TestDTO> testList = testService.selectUserList();
        log.debug("testList : {}", testList);

        return new ResponseEntity<>(testList, HttpStatus.OK);
    }
}
