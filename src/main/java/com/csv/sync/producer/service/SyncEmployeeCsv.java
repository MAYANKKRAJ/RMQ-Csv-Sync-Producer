package com.csv.sync.producer.service;

import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.csv.sync.producer.factory.CSVReaderFactory;
import com.csv.sync.producer.model.Employee;
import com.csv.sync.producer.utils.EmployeeMappingStrategy;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.exceptions.CsvException;

@Service
public class SyncEmployeeCsv {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CSVReaderFactory.class);
	
	@Autowired
	private final CSVReaderFactory csvReaderFactory;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private EmployeeMappingStrategy mappingStrategy;
	
    public SyncEmployeeCsv(CSVReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
    }
	
	public void readEmployeeCsv() throws IOException {
	    CSVReader csvReader = csvReaderFactory.loadCsvFilePath("Employee.csv");

	    CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
	            .withMappingStrategy(mappingStrategy)
	            .build();

	    List<Employee> employees = csvToBean.parse();

	    for (Employee employee : employees) {
	    	LOGGER.info("mapping the object in json");
	    	String employeeJson = new ObjectMapper().writeValueAsString(employee);
	    	LOGGER.info("publishing the employeeJson ID {} to payload", employee.getEmployeeId());
	    	rabbitTemplate.convertAndSend("employee.queue",employeeJson);
	    	LOGGER.info("published the employeeJson ID {} to payload", employee.getEmployeeId());
	    }
	}
}
