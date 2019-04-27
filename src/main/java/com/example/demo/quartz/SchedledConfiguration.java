package com.example.demo.quartz;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class SchedledConfiguration {
	@Bean
	public JobDetail jobDetail() {
		return JobBuilder.newJob(FirstJob.class) //FirstJob是我实现的job类
				.withIdentity("firstJob")        //可以给该jobDetail起一个id，便于之后的检索，也可以.withidentity("firstjob","group1")
				.usingJobData("name", "myname1") // 以Key-Value形式关联数据
				.storeDurably()                  // 即使没有Trigger关联时，也不需要删除该JobDetail
				.build();
	}
	
	@Bean
	public Trigger trigger() {
		SimpleScheduleBuilder scheduleBuilder = SimpleScheduleBuilder.simpleSchedule().
				                                withIntervalInSeconds(10).repeatForever();
		                                        //每10S 重复一次的。     
		return TriggerBuilder.newTrigger()
				.forJob("firstJob")       // 关联上述的JobDetail
				.withIdentity("trigger1") // 给该Trigger起一个id
				.startNow()               // 立即开启。or startAt(DateBuilder.futureDate(20, IntervalUnit.SECOND)) // 延迟20秒开始 
				.withSchedule(scheduleBuilder).build();
	}
}
