package com.wx.cloudprint.util;

import java.io.*;

public class FileUtils {

    public static byte [] readFile(String path) throws Exception {
        File file=new File(path);
        return readFile(file);
    }
    public static byte [] readFile(File file) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(file);
        int len = fileInputStream.available();
        byte [] pdfBytes = new byte[(len)];
        fileInputStream.read(pdfBytes);
        fileInputStream.close();
        return pdfBytes;
    }

    public static void writeFile(byte []data,File file) throws IOException {
        FileOutputStream fileOutputStream=new FileOutputStream(file);
        fileOutputStream.write(data);
        fileOutputStream.flush();
        fileOutputStream.close();
    }
    public static void writeFile(byte []data,String path) throws IOException {
        File file =new File(path);
        writeFile(data,file);
    }
    public static void main(String []s){
        System.out.println("1");
    }
}
