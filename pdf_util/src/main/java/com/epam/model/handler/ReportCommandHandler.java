package com.epam.model.handler;

import com.epam.model.CommandArgument;
import com.epam.model.report.HtmlReport;
import com.epam.model.report.MappingResultRecord;
import com.epam.model.report.TestResult;
import com.epam.util.PropertiesBundle;
import com.epam.util.file_system.ResourceReader;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ReportCommandHandler extends CommandHandler {

    private static final Logger log = Logger.getLogger(ReportCommandHandler.class);

    private List<HtmlReport> reports = Collections.synchronizedList(new ArrayList<HtmlReport>());
    private TestResult testResult;

    public ReportCommandHandler(CommandArgument commandData) {
        super(commandData);
    }

    @Override
    public void handle() {
        List<File> files = new ArrayList<File>();
        File dir = new File(getCommandData().getPdfPath());
        if (dir.isDirectory())
            files.addAll(ResourceReader.readDirectory(Paths.get(getCommandData().getPathForReports()), PropertiesBundle.getValue("htmlreport.template")));
        else {
            files.add(dir);
        }
        ExecutorService service = Executors.newFixedThreadPool(Integer.parseInt(PropertiesBundle.getValue("processing.threads.count")));
        for(File report: files) {
            service.execute(new ReportExtractor(report));
        }
        try {
            service.shutdown();
            service.awaitTermination(Integer.parseInt(PropertiesBundle.getValue("processing.timeout")), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
           log.error("Exception during report repository parsing: " + e);
        }
    }

    @Override
    public String getResult() {
        StringBuilder result = new StringBuilder();
        boolean isTypeDefined = testResult != null;
        HashMap<String, Set<File>> idMap = new HashMap<String, Set<File>>();
        for (HtmlReport report : reports) {
            List<MappingResultRecord> recordsInReport = isTypeDefined ? report.getDefinedResults(testResult)
                    : report.getAllResults();
            for (MappingResultRecord record : recordsInReport) {
                addIdInMap(report, record, idMap);
            }
        }
        result.append(convertMapToString(idMap));
        return result.toString();
    }

    private void addIdInMap(HtmlReport report, MappingResultRecord record, HashMap<String, Set<File>> idMap) {
        if (!isValuesSpecified()) {
            if (idMap.containsKey(record.getExpectedValueSource())) {
                idMap.get(record.getExpectedValueSource()).add(report.getFile());
            } else {
                idMap.put((String) record.getExpectedValueSource(), new TreeSet<File>());
            }
        } else {
            List<String> definedIds = getValues();
            if (idMap.containsKey(record.getExpectedValueSource())) {
                if (definedIds.contains(record.getExpectedValueSource()))
                    idMap.get(record.getExpectedValueSource()).add(report.getFile());
            } else {
                if (definedIds.contains(record.getExpectedValueSource())) {
                    idMap.put((String) record.getExpectedValueSource(), new TreeSet<File>());
                    idMap.get(record.getExpectedValueSource()).add(report.getFile());
                }

            }
        }
    }

    public void setResult(TestResult result) {
        this.testResult = result;
    }

    private class ReportExtractor  implements Runnable {

        private File reportFile;

        public ReportExtractor(File reportFile) {
            this.reportFile = reportFile;
        }

        @Override
        public void run() {
            reports.add(PdfReportParser.parseAxaReport(reportFile));
        }
    }

}
