package com.epam.model;

public class CommandArgument implements java.io.Serializable {

    private String reportsPath;
    private String pdfPath;

    private String values;

    public CommandArgument() {
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


    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    @Override
    public String toString() {
        return "CommandArgument{" +
                "reportsPath='" + reportsPath + '\'' +
                ", pdfPath='" + pdfPath + '\'' +
                "}";
    }
}
