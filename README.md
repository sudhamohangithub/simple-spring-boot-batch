# simple-spring-boot-batch
This is a example project for Spring boot batch. It has two batch jobs importUserJob and exportUserJob

How to run?
1. clone
2. mvn clean install
3. mvn spring-boot:run -Dspring-boot.run.arguments="importUserJob"
4. mvn spring-boot:run -Dspring-boot.run.arguments="exportUserJob" -- to run this need a existing db having Person table and data.

Following table will automatically created:
BATCH_JOB_INSTANCE, BATCH_JOB_EXECUTION, BATCH_JOB_EXECUTION_PARAMS, and BATCH_STEP_EXECUTION ...


Reference and good link

I used this example as base and created my project: https://spring.io/guides/gs/batch-processing/

https://spring.io/projects/spring-batch home
https://docs.spring.io/spring-batch/docs/current/reference/html/index.html
https://docs.spring.io/spring-batch/docs/current/reference/html/spring-batch-intro.html#spring-batch-intro
https://docs.spring.io/spring-batch/docs/current/reference/html/schema-appendix.html tables reference
https://docs.spring.io/spring-batch/docs/current/reference/html/step.html#configureStep 
https://docs.spring.io/spring-batch/docs/current/reference/html/job.html#configureJob

https://docs.spring.io/spring-batch/docs/current/reference/html/readersAndWriters.html 

https://www.baeldung.com/introduction-to-spring-batch
