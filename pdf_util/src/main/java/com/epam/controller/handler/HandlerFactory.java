package com.epam.controller.handler;

import com.epam.model.Command;

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
