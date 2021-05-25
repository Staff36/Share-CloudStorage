package ru.share.Handlers;
import lombok.Getter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Getter
public class FileMessage {
    private String fileName;
    private byte[] data;

    public FileMessage(final Path path) throws IOException {
        fileName = path.getFileName().toString();
        data = Files.readAllBytes(path);

    }
}
