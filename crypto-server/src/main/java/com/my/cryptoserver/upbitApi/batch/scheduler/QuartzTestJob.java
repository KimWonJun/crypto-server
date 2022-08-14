package com.my.cryptoserver.upbitApi.batch.scheduler;

import com.my.cryptoserver.upbitApi.service.UpbitApiService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class QuartzTestJob extends QuartzJobBean implements InterruptableJob
{
    private static final Logger log = LogManager.getLogger(QuartzTestJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        log.info("1");
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
