package com.epam.model.handler;

import com.epam.model.CommandArgument;
import com.epam.util.PropertiesBundle;
import com.epam.util.file_system.ResourceReader;
import com.epam.util.pdf.PdfFieldsExtractor;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PdfCommandHandler extends CommandHandler{

    private static final Logger log = Logger.getLogger(PdfCommandHandler.class);

    private Map<File,Map<String,String>> pdfFiles = Collections.synchronizedMap(new HashMap<File, Map<String, String>>());


    public PdfCommandHandler(CommandArgument commandData) {
        super(commandData);
    }

    @Override
    public void handle() {
        List<File> files = new ArrayList<File>();
        File dir = new File(getCommandData().getPdfPath());
        if (dir.isDirectory())
            files.addAll(ResourceReader.readDirectory(Paths.get(getCommandData().getPdfPath()), PropertiesBundle.getValue("pdffile.template")));
        else {
            files.add(dir);
        }
        ExecutorService service = Executors.newFixedThreadPool(Integer.parseInt(PropertiesBundle.getValue("processing.threads.count")));
        for (File pdf : files) {
            service.execute(new PdfExtractor(pdf));
        }
        try {
            service.shutdown();
            service.awaitTermination(Integer.parseInt(PropertiesBundle.getValue("processing.timeout")), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            log.error("Exception during pdf folder handling: " + e);
        }
    }

    @Override
    public String getResult() {
        StringBuilder result = new StringBuilder();
        if (isValuesSpecified()) {
            List<String> valuesList = getValues();
            Map<String, Set<File>> resultMap = new HashMap<String, Set<File>>();
            for (File pdf : pdfFiles.keySet()) {
                for (String value : valuesList) {
                    addIdToMap(resultMap, pdf, value);
                }
            }
            result.append(convertMapToString(resultMap));
        } else {
            for (File pdf : pdfFiles.keySet()) {
                result.append(pdf).append("\n");
                for (String field : pdfFiles.get(pdf).keySet()) {
                    result.append("   ").append(field).append("=").append(pdfFiles.get(pdf).get(field)).append("\n");
                }
            }
        }
        return result.toString();
    }

    private void addIdToMap(Map<String, Set<File>> fileMap, File pdfFile, String id) {
        if (pdfFiles.get(pdfFile).keySet().contains(id)) {
            if (fileMap.containsKey(id)) {
                fileMap.get(id).add(pdfFile);
            } else {
                fileMap.put(id, new TreeSet<File>());
                fileMap.get(id).add(pdfFile);
            }
        }
    }

    private class PdfExtractor implements Runnable{

        private File pdf;

        public PdfExtractor(File pdfFile){
            this.pdf = pdfFile;
        }


        @Override
        public void run() {
            Map<String, String> result = new HashMap<String, String>();
            try {
                result.putAll(PdfFieldsExtractor.extractFields(pdf.getAbsolutePath()));
            } catch (Exception e) {
                log.warn("Can't process pdf " + pdf.getAbsolutePath(), e);
            }
            pdfFiles.put(pdf, result);
        }
    }
}
