package com.epam.model.handler;

import com.epam.model.CommandArgument;

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
        return super.getResult() + "\n" + pdfHandler.getResult();
    }
}
