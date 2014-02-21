package com.epam.controller;


import com.epam.controller.handler.CommandHandler;
import com.epam.controller.handler.HandlerFactory;
import com.epam.controller.reporter.ReporterFactory;
import com.epam.controller.reporter.Resultant;
import com.epam.model.Command;

public class CommandExecutionFacade {

    public void process(String[] args) {
        Command command = CommandExecutor.executeCommandFromParameters(args);

        CommandHandler handler = getHandler(command);
        handler.handle();

        Resultant resultant = getReporter(command);
        handler.outputResult(resultant);
    }

    private CommandHandler getHandler(Command command) {
        if (command.getType() == null) {
            throw new IllegalArgumentException("Operation " + command.getType() + "is not defined or not supported!");
        }
        return HandlerFactory.createHandler(command);
    }

    private Resultant getReporter(Command command) {
        if (command.getOutput() == null) {
            throw new IllegalArgumentException("Can't define output type" + command.getOutput());
        }
        return ReporterFactory.getReporter(command.getOutput().getReporter());
    }


}
