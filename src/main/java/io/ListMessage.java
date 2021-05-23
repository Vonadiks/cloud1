package io;

import lombok.Data;

import java.util.List;

@Data
public class ListMessage implements Message{
    List<String> files;

    public ListMessage(List<String> files) {
        this.files = files;
    }

    @Override
    public MessageType getType() {
        return MessageType.LIST;
    }
}
