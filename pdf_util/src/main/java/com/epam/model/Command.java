package com.epam.model;

import com.epam.controller.CommandType;

public class Command implements java.io.Serializable {

    private CommandType type;

    private String reportsPath;
    private String pdfPath;
    private TestResult result;

    private ResultOutput output = ResultOutput.CONSOLE;
    /**
     * Set if report redirects to file.
     */
    private String pathToGeneratedReport;

    private boolean isUnique;
    private String values;

    public Command() {
    }

    public CommandType getType() {
        return type;
    }

    public void setType(CommandType type) {
        this.type = type;
    }

    public String getPathForReports() {
        return reportsPath;
    }

    public void setPathForReports(String reportsPath) {
        this.reportsPath = reportsPath;
    }

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public TestResult getResult() {
        return result;
    }

    public void setResult(TestResult result) {
        this.result = result;
    }

    public boolean isUnique() {
        return isUnique;
    }

    public void setUnique(boolean isUnique) {
        this.isUnique = isUnique;
    }

    public ResultOutput getOutput() {
        return output;
    }

    public void setOutput(ResultOutput output) {
        this.output = output;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }


    public String getPathToGeneratedReport() {
        return pathToGeneratedReport;
    }

    public void setPathToGeneratedReport(String pathToGeneratedReport) {
        this.pathToGeneratedReport = pathToGeneratedReport;
    }

    @Override
    public String toString() {
        return "Command{" +
                "type=" + type +
                ", reportsPath='" + reportsPath + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                ", result=" + result +
                ", isUnique=" + isUnique +
                '}';
    }
}
