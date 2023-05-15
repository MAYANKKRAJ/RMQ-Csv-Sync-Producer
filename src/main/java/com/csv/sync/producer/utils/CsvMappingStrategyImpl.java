package com.csv.sync.producer.utils;

import com.opencsv.bean.ColumnPositionMappingStrategy;

public class CsvMappingStrategyImpl<T> extends ColumnPositionMappingStrategy<T> implements CsvMappingStrategy<T> {
    public CsvMappingStrategyImpl(Class<? extends T> type, String... columnMapping) {
        setType(type);
        setColumnMapping(columnMapping);
    }

    @Override
    public void setType(Class<? extends T> type) {
        super.setType(type);
    }

    @Override
    public void setColumnMapping(String[] columnMapping) {
        super.setColumnMapping(columnMapping);
    }
}
