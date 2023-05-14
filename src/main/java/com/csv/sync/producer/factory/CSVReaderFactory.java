package com.csv.sync.producer.factory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.opencsv.CSVReader;

@Component
public class CSVReaderFactory {

//	CSVReader employeeReader = new CSVReader(new FileReader(employeeCsv));

	private Map<String, CSVReader> csvReaders = new HashMap<>();

    public CSVReader getReaderForCsvFile(String file) throws IOException {
        CSVReader csvReader = csvReaders.get(file);
        if (csvReader == null) {
            Resource resource = new ClassPathResource(file);
            csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()));
            csvReaders.put(file, csvReader);
        }
        return csvReader;
    }

	public CSVReader getEmployeeCsvReader() throws IOException {
		return getReaderForCsvFile("Employee.csv");
	}

	public CSVReader getStudentCsvReader() throws IOException {
		return getReaderForCsvFile("Student.csv");
	}

	public CSVReader getSalesInfoCsvReader() throws IOException {
		return getReaderForCsvFile("SalesInfo.csv");
	}
}
