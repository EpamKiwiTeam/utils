package com.epam.controller;


import com.epam.model.handler.CommandHandler;

public class CommandExecutionFacade {

    public void process(String path, String fileName, String beanName) {
        CommandHandler handler = CommandExecutor.executeCommandFromParameters(path,fileName,beanName);
        handler.handle();
        handler.outputResult();
    }
}
