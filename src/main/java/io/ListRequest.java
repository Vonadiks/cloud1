package io;

public class ListRequest implements Message{
    @Override
    public MessageType getType() {
        return MessageType.LIST_REQUEST;
    }
}
