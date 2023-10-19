package org.mk96.common.test.template.handler.initialize;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;
import org.mk96.common.test.template.exception.MkTestTemplateException;
import lombok.experimental.UtilityClass;

@UtilityClass
public class MkTestTemplateFileInitalizeHandler {

    public void createFiles(String basePath, String... fileNames) {
        Stream.of(fileNames)
            .forEach(exampleName -> createFile(basePath, exampleName));
    }

	public void createFile(String basePath, String fileName) {
        Path examplePath = Paths.get(basePath, fileName);

        try {
            Files.createDirectories(examplePath.getParent());
            if (Files.notExists(examplePath)) {
                Files.createFile(examplePath);
            }
        } catch (IOException e) {
            throw new MkTestTemplateException(
                    "Error creating file: " + fileName + " ", e);
        }
	}

}
