package com.csv.sync.producer.utils;

import com.opencsv.bean.MappingStrategy;

public interface CsvMappingStrategy<T> extends MappingStrategy<T> {
	
	void setType(Class<? extends T> type);

	void setColumnMapping(String[] columnMapping);
}
