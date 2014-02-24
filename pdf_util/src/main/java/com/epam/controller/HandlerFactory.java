package com.epam.controller;

import com.epam.model.Command;
import com.epam.model.handler.CommandHandler;

public class HandlerFactory {

    public static CommandHandler createHandler(Command command) {
        CommandHandler handler = null;
        try {
            handler = command.getType().getHandler().getConstructor(Command.class).newInstance(command);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return handler;
    }
}
