package com.epam.model.report;

public class PdfResultRecord implements MappingResultRecord {

    private TestResult testResult;

    private String expected;
    private String actual;

    private String uiId;
    private String pdfId;

    public PdfResultRecord(){}

    public void setTestResult(TestResult testResult) {
        this.testResult = testResult;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public void setActual(String actual) {
        this.actual = actual;
    }

    public void setUiId(String uiId) {
        this.uiId = uiId;
    }

    public void setPdfId(String pdfId) {
        this.pdfId = pdfId;
    }

    @Override
    public TestResult getTestResult() {
        return testResult;
    }

    @Override
    public Object getExpectedValue() {
        return expected;
    }

    @Override
    public Object getActualValue() {
        return actual;
    }

    @Override
    public Object getExpectedValueSource() {
        return pdfId;
    }

    @Override
    public Object getActualValueSource() {
        return uiId;
    }

    @Override
    public String toString() {
        return "PdfResultRecord{" +
                "testResult=" + testResult +
                ", expected='" + expected + '\'' +
                ", actual='" + actual + '\'' +
                ", uiId='" + uiId + '\'' +
                ", pdfId='" + pdfId + '\'' +
                '}';
    }
}
