package com.epam.view;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.epam.controller.CommandExecutionFacade;

import java.io.UnsupportedEncodingException;

public class Runner {

    @Parameter(names = "-dir", description = "directory to bean file.", required = false)
    private String dir;

    @Parameter(names = "-beanFile", description = "simple file name with beans, for example abc.xml", required = false)
    private String nameOfFileWithBean;

    @Parameter(names = "-beanName", description = "name of command handler bean to execute.", required = false)
    private String beanName;

    public static void main(String... args) throws UnsupportedEncodingException {

        Runner runner = new Runner();
        new JCommander(runner, args);


       CommandExecutionFacade processor = new CommandExecutionFacade();
       processor.process(runner.dir, runner.nameOfFileWithBean, runner.beanName);


    }

}
