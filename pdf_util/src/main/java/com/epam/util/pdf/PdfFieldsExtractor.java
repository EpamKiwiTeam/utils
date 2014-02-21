package com.epam.util.pdf;

import org.apache.pdfbox.exceptions.CryptographyException;
import org.apache.pdfbox.exceptions.InvalidPasswordException;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.common.COSObjectable;
import org.apache.pdfbox.pdmodel.interactive.form.PDAcroForm;
import org.apache.pdfbox.pdmodel.interactive.form.PDField;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Extractor of PDF fields. Uses <a href="http://pdfbox.apache.org/">PDFBox</a>
 * as PDF API implementation
 */
public class PdfFieldsExtractor {


    private PdfFieldsExtractor() {
    }


    /**
     * Extracts fields with their values from specified file
     *
     * @param filename
     * @return
     * @throws java.io.IOException
     * @throws org.apache.pdfbox.exceptions.InvalidPasswordException
     * @throws org.apache.pdfbox.exceptions.CryptographyException
     */
    public static Map<String, String> extractFields(String filename) throws IOException, CryptographyException, InvalidPasswordException {
        return extractFields(PDDocument.load(filename), null);
    }


    /**
     * Extracts fields with their values from specified {@link org.apache.pdfbox.pdmodel.PDDocument}
     *
     * @return
     * @throws java.io.IOException
     * @throws org.apache.pdfbox.exceptions.InvalidPasswordException
     * @throws org.apache.pdfbox.exceptions.CryptographyException
     */
    @SuppressWarnings("unchecked")
    public static Map<String, String> extractFields(PDDocument pdfDocument, String password) throws IOException, CryptographyException,
            InvalidPasswordException {
        try {
            decrypt(pdfDocument, password);

            List<PDField> fields = getAcroForm(pdfDocument).getFields();

            Iterator<PDField> fieldsIter = fields.iterator();

            Map<String, String> mapFields = new HashMap<String, String>();
            while (fieldsIter.hasNext()) {
                PDField field = fieldsIter.next();
                processField(field, mapFields);
            }
            return mapFields;
        } catch (Exception e) {
            return new HashMap<String, String>();
        } finally {
            if (pdfDocument != null) {
                pdfDocument.close();
            }
        }
    }

    private static PDAcroForm getAcroForm(PDDocument pdfDocument) {
        PDAcroForm acroForm = pdfDocument.getDocumentCatalog().getAcroForm();
        return acroForm;
    }


    /**
     * Recursive method, retrieves field name/value pair from fields tree
     *
     * @param field
     * @param mapFields
     * @throws java.io.IOException
     */
    public static void processField(PDField field, Map<String, String> mapFields) throws IOException {
        List<COSObjectable> kids = field.getKids();
        if (kids != null) {
            Iterator<COSObjectable> kidsIter = kids.iterator();
            while (kidsIter.hasNext()) {
                Object pdfObj = kidsIter.next();
                if (pdfObj instanceof PDField) {
                    PDField kid = (PDField) pdfObj;
                    processField(kid, mapFields);
                }
            }
        } else {
            mapFields.put(field.getFullyQualifiedName(), field.getValue());
        }
    }

    public static void decrypt(PDDocument document, String password) throws CryptographyException, IOException, InvalidPasswordException {
        if (document.isEncrypted() && password != null) {
            document.decrypt(password);
        }
    }
}
