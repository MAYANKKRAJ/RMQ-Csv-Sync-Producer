package com.csv.sync.producer.service;

import java.io.IOException;
import java.util.List;

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
	@Autowired
	private final CSVReaderFactory csvReaderFactory;
	
	@Value(value = "${spring.web.resources.static-locations}")
    private String employeeCsvFile;
	
	@Autowired
	private RabbitTemplate rabbitTemplate;
	
	@Autowired
	private EmployeeMappingStrategy mappingStrategy;
	
    public SyncEmployeeCsv(CSVReaderFactory csvReaderFactory) {
        this.csvReaderFactory = csvReaderFactory;
    }
	
	public void readEmployeeCsv() throws IOException {
	    CSVReader csvReader = csvReaderFactory.getEmployeeCsvReader();

	    CsvToBean<Employee> csvToBean = new CsvToBeanBuilder<Employee>(csvReader)
	            .withMappingStrategy(mappingStrategy)
	            .build();

	    List<Employee> employees = csvToBean.parse();

	    for (Employee employee : employees) {
	    	String employeeJson = new ObjectMapper().writeValueAsString(employee);
	    	rabbitTemplate.convertAndSend("employee.queue",employeeJson);
	    }
	}
}
