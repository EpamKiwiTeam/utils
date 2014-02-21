package com.epam.controller.reporter;

public class ReporterFactory {

    public static Resultant getReporter(Class<? extends Resultant> resultReporterKlazz) {
        Resultant resultReporter = null;
        try {
            resultReporter = resultReporterKlazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultReporter;
    }
}
