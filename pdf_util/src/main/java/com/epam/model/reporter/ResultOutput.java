package com.epam.model.reporter;

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
