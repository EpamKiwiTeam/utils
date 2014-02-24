package com.epam.model.reporter;

import com.epam.model.Command;
import com.epam.util.PropertiesBundle;
import org.apache.log4j.Logger;

import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FileReporter implements Resultant {

    private static final Logger log = Logger.getLogger(FileReporter.class);

    private final static String DATE_FORMAT = "dd/MM/yyyy HH:mm:ss";


    @Override
    public void output(String result, Command command) {
        File report = getReportFile(command);
        Writer stream = null;
        try {
            stream = new FileWriter(report);
            stream.append(result);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            log.warn("Can't redirect results into file: " + report.getAbsolutePath(), e);
        }
    }

    private File getReportFile(Command command){
        File report;
        if(command.getPathToGeneratedReport() == null) {
            report = getDefaultReportFile();
        } else {
            report = new File(command.getPathToGeneratedReport());
            if(report.isDirectory()){
                report = new File(command.getPathToGeneratedReport() + "\\" + getDefaultFileName());
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
}
