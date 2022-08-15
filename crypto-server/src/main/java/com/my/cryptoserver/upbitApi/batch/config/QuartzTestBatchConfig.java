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

    @PostConstruct
    public void init()
    {
        try
        {
            quartzService.addCronJob(QuartzTestJob.class, "testJob", "test", null, "0/10 * * ? * * *");
        }
        catch (SchedulerException e)
        {
            throw new RuntimeException(e);
        }
    }

//    public Trigger buildJobTrigger(String scheduleExp)
//    {
//        return TriggerBuilder.newTrigger()
//                .withSchedule(CronScheduleBuilder.cronSchedule(scheduleExp))
//                .build();
//    }
//
//    public JobDetail buildJobDetail(Class job, String name, String group, Map params)
//    {
//        JobDataMap jobDataMap = new JobDataMap();
//        jobDataMap.putAll(params);
//
//        return newJob(job).withIdentity(name, group)
//                .usingJobData(jobDataMap)
//                .build();
//    }

}
