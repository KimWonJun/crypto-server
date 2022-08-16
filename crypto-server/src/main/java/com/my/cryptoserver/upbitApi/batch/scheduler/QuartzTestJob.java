package com.my.cryptoserver.upbitApi.batch.scheduler;

import com.my.cryptoserver.upbitApi.service.UpbitApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.time.LocalDateTime;

public class QuartzTestJob extends QuartzJobBean implements InterruptableJob
{
    private static final Logger log = LogManager.getLogger(QuartzTestJob.class);

    /**
     * methodName : executeInternal
     * author : KimWonJun
     * description :
     * 작성일 : 2022-08-15
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        log.info("Quartz Test Job Executed : {}", LocalDateTime.now().toString());
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
