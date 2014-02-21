package com.epam.util.html;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;

public class HtmlReader {

    public static Document getHtmlDocument(File documentFile) {
        Document doc = null;
        try {
            doc = Jsoup.parse(readFile(documentFile));
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
        return doc;
    }

    private static String readFile(File htmlReport) throws IOException {
        byte[] fileContent = Files.readAllBytes(FileSystems.getDefault().getPath(htmlReport.getCanonicalPath()));
        return new String(fileContent);
    }

}
