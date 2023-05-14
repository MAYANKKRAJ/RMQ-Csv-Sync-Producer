package com.csv.sync.producer.factory;

import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import com.csv.sync.producer.properties.CsvProperties;
import com.opencsv.CSVReader;

@Component
public class CSVReaderFactory {

//	CSVReader employeeReader = new CSVReader(new FileReader(employeeCsv));
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CSVReaderFactory.class);
	
	@Autowired
    private CsvProperties csvProperties;

	private Map<String, CSVReader> csvReaders = new HashMap<>();

    public CSVReader getReaderForCsvFile(String file) throws IOException {
        CSVReader csvReader = csvReaders.get(file);
        if (csvReader == null) {
            Resource resource = new ClassPathResource(file);
            csvReader = new CSVReader(new InputStreamReader(resource.getInputStream()));
            csvReaders.put(file, csvReader);
            LOGGER.info("CSVReader instance creatd for file: {}", file);
        }
        return csvReader;
    }
    
    public CSVReader loadCsvFilePath(String fileName) throws IOException {
    	try {
            LOGGER.info("loading the csv file path for: {}", fileName);
            String filePath = csvProperties.getFiles().stream()
                    .filter(file -> file.equalsIgnoreCase(fileName))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid file name: " + fileName));
            return getReaderForCsvFile(filePath);
        } catch (IOException err) {
            LOGGER.error("Error loading CSV file {}: {}", fileName, err.getMessage());
            throw err;
        }
    }
}
