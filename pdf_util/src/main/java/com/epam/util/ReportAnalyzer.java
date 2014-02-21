package com.epam.util;

import com.epam.util.html.HtmlReader;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ReportAnalyzer {

    private static final String SKIPPED_ID = "value_skipped";
    private static final String ABSENT_ID = "value_absent";

    private static final String MAIN_TABLE_ID = "reportTableContainer";

    private static final String COLUMN_TAG = "td";
    private static final int PDF_ID = 4;

    public static List<String> getMissingPdfIdList(File htmlReport) {
        List<String> result = new ArrayList<String>();
        Document doc = HtmlReader.getHtmlDocument(htmlReport);
        Element table = doc.getElementById(MAIN_TABLE_ID);
        Elements skipped = table.getElementsByAttributeValue("id", SKIPPED_ID).addClass("");
        for (Element element : skipped) {
            result.add(getPdfId(element));
        }
        return result;
    }

    public static List<String> getNotMappedPdfFields(File htmlReport) {
        List<String> result = new ArrayList<String>();
        Document doc = HtmlReader.getHtmlDocument(htmlReport);
        Element table = doc.getElementById(MAIN_TABLE_ID);
        Elements skipped = table.getElementsByAttributeValue("id", ABSENT_ID).addClass("");
        for (Element element : skipped) {
            result.add(getPdfId(element));
        }
        return result;
    }

    private static String getPdfId(Element record) {
        Elements columnsList = record.getElementsByTag(COLUMN_TAG);
        return removeControlPrefix(columnsList.get(PDF_ID - 1).text());
    }

    private static String removeControlPrefix(String id) {
        String result = id;
        if (result.contains(":")) {
            result = result.substring(result.indexOf(":") + 1);
        }
        return result;
    }
}
