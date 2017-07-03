/**
 * 
 */
package com.opencodez.quartz.jobs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobDetail;
import org.quartz.JobExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.JobDetailFactoryBean;
import org.springframework.scheduling.quartz.SimpleTriggerFactoryBean;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.opencodez.configuration.ConfigureQuartz;
import com.opencodez.util.AppLogger;

/**
 * @author pavan.solapure
 *
 */
@Component
@DisallowConcurrentExecution
public class JobWithSimpleTrigger implements Job {

	private final static AppLogger logger = AppLogger.getInstance();
	
	@Value("${cron.frequency.jobwithsimpletrigger}")
    private long frequency;

	@Override
	public void execute(JobExecutionContext jobExecutionContext) {
		logger.info("Running JobWithSimpleTrigger | frequency {}", frequency);
		
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:9000/quartz/{name}/{date}";
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("name", "ks");
        map.put("date", new Date().getTime());
//        Address address = new Address("Dhananjaypur", "Varanasi","UP");
//        restTemplate.put(url, address, map);
        restTemplate.put(url, null, map);
	}
	
	@Bean(name = "jobWithSimpleTriggerBean")
    public JobDetailFactoryBean sampleJob() {
        return ConfigureQuartz.createJobDetail(this.getClass());
    }

    @Bean(name = "jobWithSimpleTriggerBeanTrigger")
    public SimpleTriggerFactoryBean sampleJobTrigger(@Qualifier("jobWithSimpleTriggerBean") JobDetail jobDetail) {
    	return ConfigureQuartz.createTrigger(jobDetail,frequency);
    }
}
