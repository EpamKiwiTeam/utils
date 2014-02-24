package com.epam.controller;

import com.epam.model.report.HtmlReport;
import com.epam.model.report.MappingResultRecord;
import com.epam.model.report.PdfResultRecord;
import com.epam.model.report.TestResult;
import com.epam.util.html.HtmlReader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.epam.util.PropertiesBundle.getReportValue;

public class PdfReportParser {

    public final static String COLUMN_TAG = "td";

    public static void main(String...args) {
        System.out.print(parseAxaReport(new File("C:\\report2\\TestCase1_AL.html")));
    }

    public static HtmlReport parseAxaReport(File htmlReport) {
        HtmlReport report = new HtmlReport(htmlReport);
        List<String> result = new ArrayList<String>();
        Document doc = HtmlReader.getHtmlDocument(htmlReport);
        Element table = doc.getElementById(getReportValue("TABLE"));
        for(TestResult resultValue: TestResult.values()){
            Elements elementsForResult = table.getElementsByAttributeValue("id", getReportValue(resultValue.toString()));
            for(Element element: elementsForResult) {
                report.addResult(getRecord(resultValue, element));
            }
        }
        return report;
    }

    private static MappingResultRecord getRecord(TestResult result, Element recordElement){
        MappingResultRecord record = new PdfResultRecord();
        ((PdfResultRecord)record).setTestResult(result);
        Elements columnsList =recordElement.getElementsByTag(COLUMN_TAG);
        ((PdfResultRecord)record).setActual(columnsList.get(Integer.parseInt(getReportValue("actual.index"))).text());
        ((PdfResultRecord)record).setExpected(columnsList.get(Integer.parseInt(getReportValue("expected.index"))).text());
        ((PdfResultRecord)record).setPdfId(columnsList.get(Integer.parseInt(getReportValue("pdf.index"))).text());
        ((PdfResultRecord)record).setUiId(columnsList.get(Integer.parseInt(getReportValue("ui.index"))).text());
        return record;
    }

}
