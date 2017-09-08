package com.wx.cloudprint.util;

import java.io.*;
import java.util.List;

public class FileUtils {

    public static String getFileExt(String fileName) {
        int pos = fileName.lastIndexOf(".");
        if (pos > -1) {
            return fileName.substring(pos + 1).toLowerCase();
        }
        return "";
    }

    public static boolean writeByte(String fileName, byte[] b) {
        BufferedOutputStream fos=null;
        try {
            fos = new BufferedOutputStream(new FileOutputStream(fileName));
            fos.write(b);
            fos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fos!=null){
                try {
                    fos.close();
                    fos=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static boolean writeFile(String fileName, InputStream fin) {
        FileOutputStream fout = null;
        byte[] buffer = new byte[1024];
        try {
            fout = new FileOutputStream(fileName);
            for (int i = fin.read(buffer); i > 0; i = fin.read(buffer)) {
                fout.write(buffer, 0, i);
            }
            fout.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fout!=null){
                try {
                    fout.close();
                    fout=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    public static void writeFile(String fileName, OutputStream fout) {
        FileInputStream fin = null;
        byte[] buffer = new byte[1024];
        try {
            fin = new FileInputStream(fileName);
            for (int i = fin.read(buffer); i > 0; i = fin.read(buffer)) {
                fout.write(buffer, 0, i);
            }
        }catch(FileNotFoundException e){
            try {
                fout.write(new String("文件没有找到！").getBytes());
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } catch (Exception e) {
        }finally{
            if(fin!=null){
                try {
                    fin.close();
                    fin=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static byte[] readByte(String fileName) {
        FileInputStream fis=null;
        try {
            fis = new FileInputStream(fileName);
            byte[] r = new byte[fis.available()];
            fis.read(r);
            return r;
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            if(fis!=null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    public static void createFolder(String path, boolean isFile) {
        if (isFile) {
            path = path.substring(0, path.lastIndexOf(File.separator));
        }
        File file = new File(path);
        if (!file.exists())
            file.mkdirs();
    }


    public static String inputStream2String(InputStream input, String charset) throws IOException {
        BufferedReader in = new BufferedReader(new InputStreamReader(input, charset));

        StringBuffer buffer = new StringBuffer();
        String line = "";
        while ((line = in.readLine()) != null) {
            buffer.append(line + "\n");
        }
        return buffer.toString();
    }

    public static void writeFileContent(String filePath, List<String> content) throws IOException {
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        FileOutputStream fos = null;
        PrintWriter pw = null;
        try {
            File file = new File(filePath);// 文件路径(包括文件名称)
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();
            for (String filein : content) {
                buffer.append(filein);
                buffer.append(System.getProperty("line.separator"));
            }

            fos = new FileOutputStream(file);
            pw = new PrintWriter(fos);
            pw.write(buffer.toString().toCharArray());
            pw.flush();
        } catch (Exception e) {
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fos != null) {
                fos.close();
            }
            if (br != null) {
                br.close();
            }
            if (isr != null) {
                isr.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }


    /**
     * 删除文件夹以及下面的文件
     * @param dir
     */
    public static void delete(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            // 递归删除目录中的子目录下
            for (int i = 0; i < children.length; i++) {
                delete(new File(dir, children[i]));
            }
        }
        // 目录此时为空，可以删除
        if (dir.exists()) {
            dir.delete();
        }
    }

    /**
     * 删除单个文件
     * @param fileName
     * @return
     */

}
