package com.my.cryptoserver.upbitApi.batch.config;

import com.my.cryptoserver.batch.service.QuartzService;
import com.my.cryptoserver.upbitApi.batch.scheduler.QuartzTestJob;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import lombok.RequiredArgsConstructor;
import org.quartz.*;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.annotation.PostConstruct;

import static org.quartz.JobBuilder.newJob;

@Configuration
@RequiredArgsConstructor
public class QuartzTestBatchConfig
{
    private static final Logger log = LogManager.getLogger(QuartzTestBatchConfig.class);

    private final SchedulerFactoryBean schedulerFactoryBean;

    private final QuartzService quartzService;

    /**
     * methodName : init
     * author : Kim WonJun
     * description : Quartz 테스트 config 클래스 init 메서드
     * 작성일 : 2022.08.15
     */
    @PostConstruct
    public void init()
    {
        try
        {
            quartzService.addCronJob(QuartzTestJob.class, "testJob", "test", null, "0/20 * * ? * * *");
        }
        catch (SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }
}
