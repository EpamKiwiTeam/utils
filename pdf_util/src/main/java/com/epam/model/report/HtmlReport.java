package com.epam.model.report;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class HtmlReport {

    private File reportFile;
    private List<MappingResultRecord> results = new ArrayList<MappingResultRecord>();

    public HtmlReport(File file) {
        this.reportFile = file;
    }


    public void setReportFile(File reportFile) {
        if(reportFile == null){
            throw new IllegalArgumentException("File can't be null");
        }
        this.reportFile = reportFile;
    }

    public File getFile(){
        return reportFile;
    }

    public void addResult(MappingResultRecord resultRecord){
        results.add(resultRecord);
    }

    public List<MappingResultRecord> getDefinedResults(TestResult resultType) {
        List<MappingResultRecord> searchResults = new ArrayList<MappingResultRecord>();
        for (MappingResultRecord resultRecord : results) {
            if (resultRecord.getTestResult().equals(resultType)) {
                searchResults.add(resultRecord);
            }
        }
        return searchResults;
    }

    public List<MappingResultRecord> getAllResults(){
       return results;
    }

    @Override
    public String toString() {
        return "HtmlReport{" +
                "reportFile=" + reportFile +
                ", results=" + results +
                '}';
    }
}
