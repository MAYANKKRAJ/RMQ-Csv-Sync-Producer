package com.csv.sync.producer.utils;

import org.springframework.stereotype.Service;

import com.csv.sync.producer.model.Employee;
import com.opencsv.bean.ColumnPositionMappingStrategy;

@Service
public class EmployeeMappingStrategy extends ColumnPositionMappingStrategy<Employee>{
	private static final String[] COLUMN_NAMES = {"employeeId", "empName", "empDepartment", "empSalary", "empCountry"};

    public EmployeeMappingStrategy() {
        setType(Employee.class);
        setColumnMapping(COLUMN_NAMES);
    }
}
