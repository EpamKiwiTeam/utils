package com.epam.model.reporter;

import com.epam.util.PropertiesBundle;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileReporter implements Resultant {

    private static final Logger log = Logger.getLogger(FileReporter.class);

    private final static String DATE_FORMAT = "dd.MM.yyyyHH.mm.ss";
    private String pathToReport;


    @Override
    public void output(String result) {
        File report = getReportFile();
        Writer stream = null;
        try {
            stream = new FileWriter(report);
            stream.append(result);
            stream.flush();
            stream.close();
            log.info("Report " + report.getAbsolutePath() + " was created");
        } catch (Exception e) {
            log.warn("Can't redirect results into file: " + report.getAbsolutePath(), e);
        }
    }



    private File getReportFile(){
        File report;
        if(pathToReport == null) {
            report = getDefaultReportFile();
        } else {
            report = new File(pathToReport);
            if(report.isDirectory()){
                report = new File(pathToReport + "\\" + getDefaultFileName());
            }
        }
        return report;
    }

    private File getDefaultReportFile(){
        return new File(PropertiesBundle.getValue("generated.report.default.path") + getDefaultFileName());
    }


    private String getDefaultFileName(){
        StringBuilder name = new StringBuilder();
        name.append("pdf_report-")
                .append(new SimpleDateFormat(DATE_FORMAT).format(new Date()))
                .append(".txt");
        return name.toString();
    }

    public String getPathToReport() {
        return pathToReport;
    }

    public void setPathToReport(String pathToReport) {
        this.pathToReport = pathToReport;
    }

    @Override
    public String toString() {
        return "FileReporter{" +
                "pathToReport='" + pathToReport + '\'' +
                '}';
    }
}
