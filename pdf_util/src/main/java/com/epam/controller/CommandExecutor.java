package com.epam.controller;

import com.epam.model.Command;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

/**
 * Uses to get {@link com.epam.model.Command command bean}.
 */
public class CommandExecutor {

    /**
     * Command line key, which indicates that bean config file will be searched in classpath.
     * By default search will be organized in file system, so parameter must be set as full path to resource.
     */
    public static final String CLASS_PATH_FLAG = "-classpath";

    /**
     * Processing command line arguments and execute {@link com.epam.model.Command command bean} from context.
     * Bases on spring context library.
     */
    static Command executeCommandFromParameters(String[] args) {
        String path = getCommandBeanPath(args);
        if (path == null) {
            throw new IllegalArgumentException("Can't find path to command file in parameters: " + getLineFromArray(args));
        }
        AbstractApplicationContext context;
        if (isClasspathOptionDefined(args)) {
            context = new ClassPathXmlApplicationContext(path);
        } else {
            context = new FileSystemXmlApplicationContext(path);
        }
        return (Command) context.getBean("Command");
    }

    private static boolean isClasspathOptionDefined(String[] args) {
        for (String arg : args) {
            if (arg.equals(CLASS_PATH_FLAG)) {
                return true;
            }
        }
        return false;
    }

    private static String getCommandBeanPath(String[] args) {
        for (String arg : args) {
            if (new File(arg).exists()) {
                return arg;
            }
        }
        return null;
    }

    private static String getLineFromArray(String[] args) {
        StringBuilder result = new StringBuilder();
        for (String arg : args) {
            result.append(arg);
            result.append(" ");
        }
        return result.toString();
    }

}
