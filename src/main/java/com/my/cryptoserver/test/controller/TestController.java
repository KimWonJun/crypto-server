package com.my.cryptoserver.test.controller;

import com.my.cryptoserver.test.vo.TestVO;
import com.my.cryptoserver.test.service.TestService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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
    private static final Logger log = LogManager.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @RequestMapping(value="/user", method= RequestMethod.GET)
    public ResponseEntity<?> selectUserList() throws Exception
    {
        List<TestVO> testList = testService.selectUserList();
        log.info("info log : {}", testList);
        log.debug("debug log");
        log.error("error log");

        return new ResponseEntity<>(testList, HttpStatus.OK);
    }

}
