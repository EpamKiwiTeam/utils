package com.epam.view;

import com.epam.controller.CommandExecutionFacade;

public class Runner {

    public static void main(String... args) {
        if (args.length == 0) {
            throw new IllegalArgumentException("Please, specify path to command file.");
        }
        CommandExecutionFacade processor = new CommandExecutionFacade();
        processor.process(args);
    }

}
