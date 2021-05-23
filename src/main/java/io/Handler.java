package io;

import java.io.Closeable;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Handler implements Runnable, Closeable {

    private final Socket socket;

    public Handler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try (DataInputStream is = new DataInputStream(socket.getInputStream());
             DataOutputStream os = new DataOutputStream(socket.getOutputStream())) {
            while (true) {
                String msg = is.readUTF();
                log.debug("received: {}", msg);
                os.writeUTF(msg);
            }
        } catch (Exception e) {
            log.error("e=", e);
        }
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
