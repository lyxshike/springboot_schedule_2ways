package com.example.demo.schedule;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.stereotype.Component;


@Component
@EnableAsync
//@Configurable
public class ScheduleTask {
	
	@Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        return taskScheduler;
    }
	
	@Scheduled(fixedRate=100*1)
	@Async("taskScheduler")
	public void reportCurrentTime() {
		System.out.println(Thread.currentThread()+"Scheduling Tasks Examples： The time is now "+dateFormat().format(new Date()));
		try {
			Thread.sleep(40000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("**** end!!!");
	}
	
	//@Scheduled(fixedDelay=1000*30)
	public void reportCurrentDelay() {
		System.out.println("Delauy   Scheduling Tasks Examples： The time is now "+dateFormat().format(new Date()));
	}
	
	//每分钟执行一次
	//@Scheduled(cron=" */2 * * * * * ")
	public void reportCurrentByCron() {
		System.out.println("ByCron    Scheduling Tasks Examples： The time is now "+dateFormat().format(new Date()));
	}

	public SimpleDateFormat dateFormat() {
		return new SimpleDateFormat("HH:mm:ss");
	}
}
