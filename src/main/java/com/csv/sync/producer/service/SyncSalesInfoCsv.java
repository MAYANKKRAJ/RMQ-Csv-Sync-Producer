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
import com.csv.sync.producer.model.SalesInfo;
import com.csv.sync.producer.utils.CsvMappingStrategy;
import com.csv.sync.producer.utils.CsvMappingStrategyImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

@Service
public class SyncSalesInfoCsv {
	private static final Logger LOGGER = LoggerFactory.getLogger(SyncSalesInfoCsv.class);

	@Autowired
	private final CSVReaderFactory csvReaderFactory;

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public SyncSalesInfoCsv(CSVReaderFactory csvReaderFactory) {
		this.csvReaderFactory = csvReaderFactory;
	}

	public void readSalesInfoCsv() throws IOException {

		Field[] salesAttributes = SalesInfo.class.getDeclaredFields();
		List<String> fieldNames = Arrays.stream(salesAttributes).map(Field::getName).map(str -> str.replaceAll("_", ""))
				.collect(Collectors.toList());

		CsvMappingStrategy<SalesInfo> csvMappingStrategy = new CsvMappingStrategyImpl<>(SalesInfo.class,
				fieldNames.toArray(new String[0]));

		CSVReader csvReader = csvReaderFactory.loadCsvFilePath("SalesInfo.csv");
		
		// Skip the first row
	    csvReader.skip(1);
		CsvToBean<SalesInfo> csvToBean = new CsvToBeanBuilder<SalesInfo>(csvReader)
				.withMappingStrategy(csvMappingStrategy).build();
		
		List<SalesInfo> salesInfos = csvToBean.parse();

		for (SalesInfo salesInfo : salesInfos) {
			LOGGER.info("mapping the object in json");
			String salesInfoJson = new ObjectMapper().writeValueAsString(salesInfo);
			LOGGER.info("publishing the salesInfo to payload");

			rabbitTemplate.convertAndSend("sales-info.queue", salesInfoJson);

			LOGGER.info("product family: {} from device type: {} published to payload", salesInfo.getProductFamily(),
					salesInfo.getDeviceType());
		}
	}

}
