package com.my.cryptoserver.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;

import java.lang.reflect.Method;

public class AsyncExceptionHandler implements AsyncUncaughtExceptionHandler
{
    private static final Logger log = LogManager.getLogger(AsyncExceptionHandler.class);
    @Override
    public void handleUncaughtException(Throwable throwable, Method method, Object... obj)
    {
        log.debug("<================== Thread ERROR Start ==================>");
        log.debug("Exception Message : {}", throwable.getMessage());
        log.debug("Method name : {}", method.getName());
        for(Object param : obj)
        {
            log.debug("Parameter Values : {}", param);
        }
        log.debug("<================== Thread ERROR End ==================>");
    }
}
