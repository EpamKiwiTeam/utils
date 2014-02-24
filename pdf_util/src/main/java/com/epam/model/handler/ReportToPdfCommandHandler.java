package com.epam.model.handler;

import com.epam.model.CommandArgument;

import java.util.List;

public class ReportToPdfCommandHandler extends ReportCommandHandler {

    private PdfCommandHandler pdfHandler;

    public ReportToPdfCommandHandler(CommandArgument commandData, PdfCommandHandler pdfFormHandler) {
        super(commandData);
        setPdfHandler(pdfFormHandler);
    }

    public ReportToPdfCommandHandler(CommandArgument commandData) {
        this(commandData, new PdfCommandHandler(commandData));
    }

    public PdfCommandHandler getPdfHandler() {
        return pdfHandler;
    }

    public void setPdfHandler(PdfCommandHandler pdfHandler) {
        if(pdfHandler == null){
            throw new IllegalArgumentException("Argument can't be null!");
        }
        this.pdfHandler = pdfHandler;
    }

    @Override
    public void handle() {
        super.handle();
        pdfHandler.handle();
    }

    @Override
    public String getResult() {
        StringBuilder result = new StringBuilder();
        if(isValuesSpecified()){
            List<String> valuesList = getValues();

        } else {

        }
        return result.toString();
    }
}
