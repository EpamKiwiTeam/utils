package com.epam.model.reporter;

public class ConsoleReporter implements Resultant {

    @Override
    public void output(String result) {
        System.out.print(result);
    }
}
