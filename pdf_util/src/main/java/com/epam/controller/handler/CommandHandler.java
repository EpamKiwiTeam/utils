package com.epam.controller.handler;

import com.epam.controller.reporter.Resultant;
import com.epam.model.Command;

public abstract class CommandHandler {

    private Command command;

    public CommandHandler(Command command) {
        this.command = command;
    }

    /**
     * Method to start command processing.
     */
    public abstract void handle();

    /**
     * Must return {@link java.lang.String string} representation of handling results.
     *
     * @return result
     */
    abstract String getResult();

    public void outputResult(Resultant resultOutput) {
        resultOutput.output(getResult(), command);
    }

    public Command getCommand() {
        return command;
    }

    public void setCommand(Command command) {
        this.command = command;
    }
}
