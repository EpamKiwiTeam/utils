package com.epam.controller.reporter;

import com.epam.model.Command;

public class ConsoleReporter implements Resultant {


    @Override
    public void output(String result, Command command) {
        System.out.print(result);
    }
}
