package io;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChatController implements Initializable {

    public TextField input;
    public ListView<String> listView;

    private DataInputStream is;
    private DataOutputStream os;

    public void send(ActionEvent actionEvent) throws IOException {
        os.writeUTF(input.getText());
        os.flush();
        input.clear();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            Socket socket = new Socket("localhost", 8189);
            is = new DataInputStream(socket.getInputStream());
            os = new DataOutputStream(socket.getOutputStream());

            Thread readThread = new Thread(() -> {
                try {
                    while (true) {
                        String msg = is.readUTF();
                        Platform.runLater(() -> listView.getItems().add(msg));
                    }
                } catch (Exception e) {
                    log.error("e=", e);
                }
            });
            readThread.setDaemon(true);
            readThread.start();
        } catch (Exception e) {
            log.error("e=", e);
        }
    }
}
