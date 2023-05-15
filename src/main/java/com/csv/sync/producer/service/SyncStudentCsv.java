package com.csv.sync.producer.service;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.csv.sync.producer.factory.CSVReaderFactory;
import com.csv.sync.producer.model.Student;
import com.csv.sync.producer.utils.CsvMappingStrategy;
import com.csv.sync.producer.utils.CsvMappingStrategyImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class SyncStudentCsv {

	private static final Logger LOGGER = LoggerFactory.getLogger(SyncStudentCsv.class);

	@Autowired
	private final CSVReaderFactory csvReaderFactory;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public SyncStudentCsv(CSVReaderFactory csvReaderFactory) {
		this.csvReaderFactory = csvReaderFactory;
	}

	public void readStudentCsv() throws IOException {

		Field[] studentAttributes = Student.class.getDeclaredFields();
		List<String> fieldNames = Arrays.stream(studentAttributes).map(Field::getName).collect(Collectors.toList());

		CsvMappingStrategy<Student> csvMappingStrategy = new CsvMappingStrategyImpl<>(Student.class,
				fieldNames.toArray(new String[0]));

		CSVReader csvReader = csvReaderFactory.loadCsvFilePath("Student.csv");

		// Skip the first row
		csvReader.skip(1);
		CsvToBean<Student> csvToBean = new CsvToBeanBuilder<Student>(csvReader).withMappingStrategy(csvMappingStrategy)
				.build();
		List<Student> students = csvToBean.parse();

		for (Student student : students) {
			LOGGER.info("mapping the object in json");
			String studentJson = new ObjectMapper().writeValueAsString(student);
			LOGGER.info("publishing the employeeJson ID {} to payload", student.getStudentId());
			rabbitTemplate.convertAndSend("student.queue", studentJson);
			LOGGER.info("student ID {} published to payload", student.getStudentId());
		}
	}
}
