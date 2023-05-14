package com.csv.sync.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.csv.sync.producer.service.SyncEmployeeCsv;

@SpringBootApplication
public class CsvSyncProducerApplication implements CommandLineRunner {

	@Autowired
	private SyncEmployeeCsv syncEmployeeCsv;
	
	public static void main(String[] args) {
		SpringApplication.run(CsvSyncProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		syncEmployeeCsv.readEmployeeCsv();
		
	}

}
