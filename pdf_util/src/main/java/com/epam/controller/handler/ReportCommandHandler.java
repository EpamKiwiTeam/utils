package com.epam.controller.handler;

import com.epam.model.Command;

public class ReportCommandHandler extends CommandHandler {

    public ReportCommandHandler(Command command) {
        super(command);
    }

    @Override
    public void handle() {
        System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

    @Override
    public String getResult() {
        return null;
    }

}
