package com.example.demo.quartz;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

public class FirstJob extends QuartzJobBean{

	private final Logger LOG = LoggerFactory.getLogger(FirstJob.class);
	
	private String name;
	
	public void setName(String name) {
	    this.name = name;
	}
	   
	@Override
	protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		LOG.info("hi {}", this.name);
	}
	
}
