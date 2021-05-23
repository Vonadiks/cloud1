package io;

import java.io.Serializable;

public class FilePart implements Serializable {
    private long len;
    private byte[] data;
    private long part;
    private boolean finish;
}
