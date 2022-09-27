package com.my.cryptoserver.config;

import com.my.cryptoserver.upbitApi.controller.UpbitApiController;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import java.util.concurrent.Executor;

@Configuration("asyncConfig")
@EnableAsync
public class AsyncConfig implements AsyncConfigurer
{
    private static final Logger log = LogManager.getLogger(AsyncConfig.class);

    private static int TASK_CORE_POOL_SIZE = 5;
    private static int TASK_MAX_POOL_SIZE = 10;
    private static int TASK_QUEUE_CAPACITY = 5;

    public Executor getAsyncExecuter()
    {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(TASK_CORE_POOL_SIZE);
        executor.setMaxPoolSize(TASK_MAX_POOL_SIZE);
        executor.setQueueCapacity(TASK_QUEUE_CAPACITY);
        executor.setBeanName("async_thread_executor");
        executor.initialize();
        return executor;
    }

    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler()
    {
        return new AsyncExceptionHandler();
    }
}
