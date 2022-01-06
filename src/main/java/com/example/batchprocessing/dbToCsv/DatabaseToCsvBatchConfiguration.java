package com.example.batchprocessing.dbToCsv;

import com.example.batchprocessing.csvToDb.PersonItemProcessor;
import com.example.batchprocessing.entity.Person;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableBatchProcessing
public class DatabaseToCsvBatchConfiguration {

	@Autowired
	public JobBuilderFactory jobBuilderFactory;

	@Autowired
	public StepBuilderFactory stepBuilderFactory;

	@Autowired
	@Qualifier("personDbReader")
	ItemReader<Person> personDbReader;

	@Autowired
	@Qualifier("personFileWriter")
	ItemWriter<? super Person> personFileWriter;

	@Bean
	public Job exportUserJob( Step dbToFileStep) {
		return jobBuilderFactory.get("exportUserJob")
				.incrementer(new RunIdIncrementer())
				.flow(dbToFileStep)
				.end()
				.build();
	}

	@Bean
	public Step dbToFileStep(JdbcBatchItemWriter<Person> writer) {
		return stepBuilderFactory.get("dbToFileStep")
				.<Person, Person> chunk(10)
				.reader(personDbReader)
				.processor(PersonProcessor())
				.writer(personFileWriter)
				.build();
	}

	@Bean
	public PersonItemProcessor PersonProcessor() {
		return new PersonItemProcessor();
	}

}
