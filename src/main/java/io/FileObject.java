package io;

import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Data
public class FileObject implements Message {
    private long len;
    private byte[] data;
    private String name;

    public FileObject(Path path) throws IOException {
        len = Files.size(path);
        name = path.getFileName().toString();
        data = Files.readAllBytes(path);

    }

    @Override
    public MessageType getType() {
        return MessageType.FILE;
    }
}
