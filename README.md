# springboot_schedule_2ways

## 一, spring的定时器
   ### 1.方法上加@Scheduled 注解， 类上加@Component
   ### 2.在当前类或者主启动类上添加 @EnableScheduling
   ### 3.间隔一段时间之后执行的这2个有什么区别呢？
	 @Scheduled(fixedRate=1000*30)，  @Scheduled(fixedDelay=1000*30)
	 同： 两者都会在服务启动后立即执行一次。
         异： fixedRate, 间隔时间是按上次执行的时间开始计
             fixedDelay，间隔时间是按上次执行完成的时间开始计		
   ### 4. fixedRate， 一个问题。
		当任务的执行时长超过设置的间隔时长，则第二个任务就会被阻塞，可能也会阻塞第三个，第四个。
        但是，当当前任务执行完后面的任务立马开始执行的。
    	想一想，如何避免任务被阻塞呢？
		  加上注解@EnableAsync（类上）和@Async（方法上），加了注解以后，就开启了多线程模式，
		    当到了下一次任务执行的时间，上一个任务还没有完，就会在起一个新的线程去执行。 可以创建
			一个线程池，去优化系统资源。
	@EnableAsync
		
	@Bean
        public TaskScheduler taskScheduler() {
            ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
            taskScheduler.setPoolSize(20);
            return taskScheduler;
        }
	    
        @Async("taskScheduler")		

  ###  cron 表达式的含义
      @Scheduled(cron=" */2 * * * * * ")
      6个分格的时间元素分别代表: 
           秒， 分钟，小时， 天， 月， 天of周， 年份(可省略)
	* 表示所有值； 
        ? 表示未说明的值，即不关心它为何值； 
        - 表示一个指定的范围； 
        , 表示附加一个可能值； 
        / 符号前表示开始时间，符号后表示每次递增的值；    
        L 这个月最后一天，或者这个周最后一天
        W 是工作日(周一到周五)
	
## 二，使用quartz实现定时任务 		
 ###   1. 添加quartz的maven依赖
 ###   2. 定义自己的job，实现QuartzJobBean，重写executeInternal方法
 ###   3. 新建jobDetail，绑定前面的job
 ###   4. 定义trigger, 以怎样的SimpleScheduleBuilder来执行上面的jobDetail。
      
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
        		  
