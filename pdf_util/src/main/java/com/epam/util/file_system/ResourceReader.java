package com.epam.util.file_system;

import org.apache.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ResourceReader {

    private static Logger logger = Logger.getLogger(ResourceReader.class);

    public final static String DEFAULT_PATTERN = ".*";

    public static List<File> readDirectory(Path path) {
        return readDirectory(path, DEFAULT_PATTERN);
    }

    public static List<File> readDirectory(Path path, final String fileNameRegexp) {
        final List<File> result = new ArrayList<File>();
        try {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Matcher matcher = getPattern(fileNameRegexp).matcher(file.toString());
                    if (matcher.matches()) {
                        result.add(new File(file.toString()));
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException e) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            logger.error("Exception during the file search: ", e);
        }
        return result;
    }

    private static Pattern getPattern(String fileNameRegexp) {
        Pattern pattern = null;
        if (fileNameRegexp != null)
            pattern = Pattern.compile(fileNameRegexp);
        else
            pattern = Pattern.compile(DEFAULT_PATTERN);
        return pattern;
    }

}
