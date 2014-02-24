package com.epam.model.handler;

import com.epam.model.CommandArgument;
import com.epam.model.reporter.ConsoleReporter;
import com.epam.model.reporter.Resultant;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public abstract class CommandHandler {

    private Resultant output = new ConsoleReporter();

    private CommandArgument commandData;

    public CommandHandler(CommandArgument commandData) {
        this.commandData = commandData;
    }

    /**
     * Method to start commandData processing.
     */
    public abstract void handle();

    /**
     * Must return {@link java.lang.String string} representation of handling results.
     *
     * @return result
     */
    abstract String getResult();

    public void outputResult() {
        output.output(getResult());
    }

    public String convertMapToString(Map<String, Set<File>> fileList) {
        StringBuilder result = new StringBuilder();
        result.append("\n------------------------------------------------------------------------------------------\n");
        for (String key : fileList.keySet()) {
            result.append(key);
            if (fileList.get(key).size() != 0)
                result.append(": ").append("\n");
            for (File report : fileList.get(key)) {
                result.append("\t\t").append(getShortFileName(report)).append("\n");
            }
            result.append("\n------------------------------------------------------------------------------------------\n");
        }
        return result.toString();
    }

    public String getShortFileName(File file) {
        return file.getName().replaceAll("\\..*", "");
    }

    public boolean isValuesSpecified() {
        return getCommandData().getValues() != null;
    }

    public List<String> getValues() {
        String values = commandData.getValues();
        if (values == null) {
            return new ArrayList<String>();
        }
        return Arrays.asList(values.replaceAll("\\s", "").split(","));
    }

    public CommandArgument getCommandData() {
        return commandData;
    }

    public void setCommandData(CommandArgument commandData) {
        this.commandData = commandData;
    }

    public Resultant getOutput() {
        return output;
    }

    public void setOutput(Resultant output) {
        this.output = output;
    }

    @Override
    public String toString() {
        return "CommandHandler{" +
                "commandData=" + commandData +
                ", reporter=" + output +
                '}';
    }
}
