package com.my.cryptoserver.test.controller;

import com.my.cryptoserver.test.dto.TestDTO;
import com.my.cryptoserver.test.service.TestService;
import com.my.cryptoserver.webinf.service.WebInfService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value="/test")
public class TestController
{
    private static final Logger log = LogManager.getLogger(TestController.class);

    @Autowired
    private TestService testService;

    @Autowired
    private WebInfService webInfService;

    @RequestMapping(value="/user", method= RequestMethod.GET)
    public ResponseEntity<?> selectUserList() throws Exception
    {
        List<TestDTO> testList = testService.selectUserList();
        log.info("info log : {}", testList);
        log.debug("debug log");
        log.error("error log");

        return new ResponseEntity<>(testList, HttpStatus.OK);
    }

    @RequestMapping(value="/getAllAccounts", method=RequestMethod.GET)
    public ResponseEntity<?> getAllAccounts() throws Exception
    {
        String result = webInfService.getAllAccounts();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value="/orderChance", method=RequestMethod.GET)
    public ResponseEntity<?> orderChance() throws Exception
    {
        String result = webInfService.orderChance();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @RequestMapping(value="/orderSeperate", method=RequestMethod.GET)
    public ResponseEntity<?> orderSeperate() throws Exception
    {
        String result = webInfService.orderSeperate();

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

}
