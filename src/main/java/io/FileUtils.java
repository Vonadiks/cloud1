package io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class FileUtils {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        try (
                InputStream is = new FileInputStream("img.png");
                OutputStream os = new FileOutputStream("img_copy.png")
        ) {
            int b = 0;
            byte[] buffer = new byte[8192];
            while ((b = is.read(buffer)) != -1) {
                os.write(buffer, 0, b);
            }
        }

        Scanner scanner = new Scanner(new File("123.txt"));
        BufferedReader br = new BufferedReader(
                new InputStreamReader(new FileInputStream("123.txt")));

        PrintWriter pr;
        BufferedWriter bw;

        RandomAccessFile raf = new RandomAccessFile("123.txt", "rw");

        byte[] len = new byte[4];
        DataInputStream is;

        ObjectInputStream ois = null;
        ObjectOutputStream oos;
//        Object o = ois.readObject();
//
//        if (o instanceof FileObject) {
//
//        }
//
//        if (o instanceof String) {
//
//        }


        // FileObject o = (FileObject) ois.readObject();
        // oos.writeObject(new FileObject());
    }
}
