package com.epam.model.handler;

import com.epam.model.Command;
import com.epam.model.report.HtmlReport;
import com.epam.util.PropertiesBundle;
import com.epam.util.file_system.ResourceReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReportCommandHandler extends CommandHandler {

    private static final Logger log = Logger.getLogger(ReportCommandHandler.class);

    private List<HtmlReport> reports = new ArrayList<HtmlReport>();

    public ReportCommandHandler(Command command) {
        super(command);
    }

    @Override
    public void handle() {
        List<File> files = ResourceReader.readDirectory(Paths.get(getCommand().getPdfPath()), PropertiesBundle.getValue("pdffile.template"));
        ExecutorService service = Executors.newFixedThreadPool(Integer.parseInt(PropertiesBundle.getValue("processing.threads.count")));
        long start = System.currentTimeMillis();
        for(File report: files) {
            service.execute(new ReportExtractor(report));
        }
        try {
            service.shutdown();
            service.awaitTermination(Integer.parseInt(PropertiesBundle.getValue("processing.timeout")), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getResult() {
        return null;
    }

    public static void main(String...args) {}

    private class ReportExtractor  implements Runnable {

        private File reportFile;

        public ReportExtractor(File reportFile) {
            this.reportFile = reportFile;
        }

        @Override
        public void run() {
            HtmlReport report = new HtmlReport(reportFile);
        }
    }

}
