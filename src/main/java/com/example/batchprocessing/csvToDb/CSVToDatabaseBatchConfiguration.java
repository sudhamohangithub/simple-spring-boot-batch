package com.example.batchprocessing.csvToDb;

import com.example.batchprocessing.entity.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
@Slf4j
public class CSVToDatabaseBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("personFileReader")
	ItemReader<Person> personFileReader;

	@Autowired
	@Qualifier("personDBWriter")
	ItemWriter<? super Person> personDBWriter;

	@Bean
	public Job importUserJob(JobCompletionNotificationListener listener, Step csvToDb) {
		return jobBuilderFactory.get("importUserJob")
				.incrementer(new RunIdIncrementer())
				.listener(listener)
				.flow(csvToDb)
				.end()
				.build();
	}

	@Bean
	public Step csvToDb() {
		return stepBuilderFactory.get("csvToDb")
				.<Person, Person> chunk(10)
				.reader(personFileReader)
				.processor(personProcessor())
				.writer(personDBWriter)
				.build();
	}

	@Bean
	public PersonItemProcessor personProcessor() {
		return new PersonItemProcessor();
	}

}
