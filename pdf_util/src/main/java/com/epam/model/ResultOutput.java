package com.epam.model;

import com.epam.controller.reporter.ConsoleReporter;
import com.epam.controller.reporter.FileReporter;
import com.epam.controller.reporter.Resultant;

public enum ResultOutput {

    CONSOLE(ConsoleReporter.class),
    FILE(FileReporter.class);

    private Class<? extends Resultant> result;

    private ResultOutput(Class<? extends Resultant> resultantKlazz) {
        result = resultantKlazz;
    }

    public Class<? extends Resultant> getReporter() {
        return result;
    }
}
