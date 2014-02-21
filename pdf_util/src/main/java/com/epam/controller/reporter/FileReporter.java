package com.epam.controller.reporter;

import com.epam.model.Command;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FileReporter implements Resultant {

    private final static String DATE_FORMAT = "dd/mm/YYYY";


    @Override
    public void output(String result, Command command) {

    }

    private String getDefaultFileName(){
        StringBuilder name = new StringBuilder();
        name.append("pdf_report-")
                .append(new SimpleDateFormat(DATE_FORMAT).format(new Date()));

    }
}
