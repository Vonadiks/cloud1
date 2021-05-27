package io;

import java.io.*;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler implements Runnable, Closeable {

    private final Socket socket;
    private String serverDir = "serverDir/";

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream is = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream())) {
            while (true) {
                Message msg = (Message) is.readObject();
                switch (msg.getType()){
                    case FILE:
                        handleFileMessage(msg);
                        break;
                    case LIST_REQUEST:
                        List<String> files = Files.list(Paths.get(serverDir)).
                            map(p -> p.getFileName().toString()).collect(Collectors.toList());
                        os.writeObject(new ListMessage(files));
                        os.flush();
                        break;
                }
            }
        } catch (Exception e) {
            log.error("e=", e);
        }
    }

    private void handleFileMessage(Message msg) throws Exception{
        FileObject file = (FileObject) msg;
        Files.write(Paths.get(serverDir + file.getName()), file.getData());
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
