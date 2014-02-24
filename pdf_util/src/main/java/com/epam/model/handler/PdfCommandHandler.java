package com.epam.model.handler;

import com.epam.model.Command;
import com.epam.util.PropertiesBundle;
import com.epam.util.file_system.ResourceReader;
import com.epam.util.pdf.PdfFieldsExtractor;
import org.apache.log4j.Logger;

import java.io.File;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class PdfCommandHandler extends CommandHandler{

    private static final Logger log = Logger.getLogger(PdfCommandHandler.class);

    private Map<File,Map<String,String>> pdfFiles = Collections.synchronizedMap(new HashMap<File, Map<String, String>>());

    public PdfCommandHandler(Command command) {
        super(command);
    }

    @Override
    public void handle() {
        List<File> files = ResourceReader.readDirectory(Paths.get(getCommand().getPdfPath()), PropertiesBundle.getValue("pdffile.template"));
        ExecutorService service = Executors.newFixedThreadPool(Integer.parseInt(PropertiesBundle.getValue("processing.threads.count")));
        long start = System.currentTimeMillis();
        for(File pdf: files) {
            service.execute(new PdfExtractor(pdf));
        }
        try {
            service.shutdown();
            service.awaitTermination(Integer.parseInt(PropertiesBundle.getValue("processing.timeout")), TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(service.isTerminated());
        System.out.println(service.isShutdown());


        System.out.println("Processing time is: " + (System.currentTimeMillis() - start) / 1000 + " seconds");
    }

    @Override
    public String getResult() {
        StringBuilder result = new StringBuilder();
        for(File pdf: pdfFiles.keySet()) {
           result.append(pdf.getAbsolutePath()).append(" "+pdfFiles.get(pdf).size()).append("\n");
           /* for(String field: pdfFiles.get(pdf).keySet()) {
                result.append("   ").append(field).append("\n");
            }*/
        }
        return result.toString();
    }

    public static void main(String...args) {
        Command com = new Command();
        com.setPdfPath("C:\\123");
        PdfCommandHandler hand = new PdfCommandHandler(com);
        hand.handle();
       /* hand.outputResult(new Resultant() {
            @Override
            public void output(String result, Command command) {
                System.out.print(result);
            }
        });   */
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
