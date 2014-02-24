package com.epam.model.report;

public interface MappingResultRecord {

    public TestResult getTestResult();
    public Object getExpectedValue();
    public Object getActualValue();

    public Object getExpectedValueSource();
    public Object getActualValueSource();

}
