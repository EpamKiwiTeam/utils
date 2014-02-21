package com.epam.controller.handler;

import com.epam.model.Command;

public class PdfCommandHandler extends CommandHandler {

    public PdfCommandHandler(Command command) {
        super(command);
    }

    @Override
    public void handle() {

    }

    @Override
    public String getResult() {
        return null;
    }
}
