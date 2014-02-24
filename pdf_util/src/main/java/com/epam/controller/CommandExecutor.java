package com.epam.controller;

import com.epam.model.handler.CommandHandler;
import com.epam.util.PropertiesBundle;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

import java.io.File;

/**
 * Uses to get {@link com.epam.model.handler.CommandHandler command bean}.
 */
public class CommandExecutor {

    /**
     * Execute {@link com.epam.model.handler.CommandHandler command bean} from context.
     * Bases on spring context library.
     */
    static CommandHandler executeCommandFromParameters(String path, String fileName, String beanName) {
        String finalPath = getCommandBeanPath(path, fileName);
        AbstractApplicationContext context = new FileSystemXmlApplicationContext(finalPath);
        String finalBeanName = beanName == null ? PropertiesBundle.getValue("bean.name") : beanName;
        return (CommandHandler) context.getBean(finalBeanName);
    }


    private static String getCommandBeanPath(String path, String fileName) {
        String finalPath = path == null ? System.getProperty("user.dir") : path;
        if(new File(finalPath).isDirectory()) {
            String finalFileName = fileName == null ? PropertiesBundle.getValue("bean.file") : fileName;
            finalPath = finalPath + finalFileName;
        }
        return finalPath;
    }

}
