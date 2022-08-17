package com.my.cryptoserver.upbitApi.batch.scheduler;

import com.my.cryptoserver.upbitApi.service.UpbitApiService;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.quartz.InterruptableJob;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.UnableToInterruptJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

public class CoinPriceCollectBatchJob extends QuartzJobBean implements InterruptableJob
{
    private static final Logger log = LogManager.getLogger(TestBatchScheduler.class);

    @Autowired
    private UpbitApiService upbitApiService;

    /**
     * methodName : executeInternal
     * author : KimWonJun
     * description : 코인 가격 수집 배치 execute
     * 작성일 : 2022-08-16
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException
    {
        try
        {
            upbitApiService.getCoinPrice();
        }
        catch (NoSuchAlgorithmException e1)
        {
            throw new RuntimeException(e1);
        }
        catch (UnsupportedEncodingException e2)
        {
            throw new RuntimeException(e2);
        }
    }

    @Override
    public void interrupt() throws UnableToInterruptJobException {

    }
}
