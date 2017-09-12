package com.wx.cloudprint.motan;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.apache.pdfbox.*;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.rendering.ImageType;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import util.FileUtils;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

@MotanService
public class WEP2PDFImpl implements WEP2PDF {



    private static final int WDFO_RMATPDF = 17;
    private static final int XLTYPE_PDF = 0;
    private static final int PPT_SAVEAS_PDF = 32;
    public static final int WORD_HTML = 8;
    public static final int WORD_TXT = 7;
    public static final int EXCEL_HTML = 44;
    public static final int PPT_SAVEAS_JPG = 17;

    @Value("${file.tempPath}")
    private   String tempFilePath;

    public static void main(String[] s) {

        StringBuilder supers= new StringBuilder();
        File file=new File("C:\\Users\\wx\\Desktop\\cloudprintparent\\covert_server\\lib");
        File[]files=file.listFiles();
        for(File f:files){
            supers.append(String.format("  <zipfileset src=\"%s\"/>\n  ", f.getName()));
        }
        System.out.println(supers.toString());
//        String source = "C:\\Users\\wx\\Documents\\工作簿1.xlsx";
//        String target="C:\\Users\\wx\\Downloads\\toimage";
//        String prefix=source.split("\\.")[1];
//        try {
//            int i=1;
//            byte[][]datas=new WEP2PDFImpl().offceBytes2imgsBytes(FileUtils.readByte(source),prefix);
//            for(byte[] data:datas){
//                FileUtils.writeByte(new File(target,(i++)+".jpg").getPath(),data);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

    }

    @Override
    public  byte[][]offceBytes2imgsBytes(byte[] offceBytes,String prefix)   {
        if(tempFilePath==null||tempFilePath.equals("")) {
            tempFilePath=new File("").getAbsolutePath();
        }
        if(prefix.equals("ppt")||prefix.equals("pptx")){
            File file=new File(tempFilePath);
            if(!file.exists()) file.mkdir();
            String targetTempPath=null;
            String sourceTempPath= new File(tempFilePath,UUID.randomUUID().toString()+"."+prefix).getPath();
            targetTempPath=new File(tempFilePath,UUID.randomUUID().toString()).getPath();
            FileUtils.writeByte(sourceTempPath,offceBytes);
            officeFileConverterToPdf(sourceTempPath,targetTempPath);
            return readFileImages(targetTempPath);
        }else if(prefix.equals("pdf")){
            try {
                return pdf2Img(offceBytes,216);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else {//word or excel
            try {
                byte[] pdfBytes = officeFile2PdfBytes(offceBytes, prefix);

                return pdf2Img(pdfBytes,216);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
     byte[][]readFileImages(String path){

        File file=new File(path);
          File[] fileList= file.listFiles();
        Arrays.sort(fileList,(o1, o2)->{
            String name1=o1.getName().split("\\.")[0];
            String name2=o2.getName().split("\\.")[0];



            return name1.compareTo(name2);
        });
        byte[][]result=new byte[fileList.length][];
        for(int i=0;i<fileList.length;i++){
            try {
                result[i]=FileUtils.readByte(fileList[i].getPath());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;

    }

//       byte[][] pdfBytes2ImgsBytes(byte[]pdfBytes) throws IOException {
//
//        int dpi=216;
//         Document document = null;
//
//         float scale = dpi / 72f;
//         document = new Document();
//         try {
//
//
//             document.setByteArray(pdfBytes,0,pdfBytes.length,UUID.randomUUID().toString()+".pdf");
//         } catch (Exception e1) {
//             e1.printStackTrace();
//         }
//         // maxPages = document.getPageTree().getNumberOfPages();
//
//
//         int pages = document.getNumberOfPages();
//         byte[][]result=new byte[pages][];
//         for (int i = 0; i < pages; i++) {
//
//             BufferedImage img = (BufferedImage) document.getPageImage(i,
//                     GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, 0,
//                     scale);
//             ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();
//
//             ImageIO.write(img, "jpg", arrayOutputStream);
//             result[i]=arrayOutputStream.toByteArray();
//             arrayOutputStream.close();
//         }
//
//        return result;
//    }
    public static byte[][] pdf2Img(byte []pdfBytes, int dpi) throws IOException {

        PDDocument doc = PDDocument.load(pdfBytes);
        int pageCount = doc.getNumberOfPages();
        byte[][]result=new byte[pageCount][];
        System.out.println(pageCount);
        PDFRenderer renderer= new PDFRenderer(doc);

        for(int i=0;i<pageCount;i++){
            BufferedImage image= renderer.renderImageWithDPI(i,dpi);
                        ByteArrayOutputStream arrayOutputStream=new ByteArrayOutputStream();

            ImageIO.write(image,"jpg",arrayOutputStream);
            result[i]=arrayOutputStream.toByteArray();
            arrayOutputStream.close();

        }
        doc.close();
        System.out.println("over");
        return result;
    }


       byte[]officeFile2PdfBytes(byte[] source,String prefix) throws Exception {

        File file=new File(tempFilePath);
        if(!file.exists()) file.mkdir();

          String targetTempPath=null;
        String sourceTempPath= new File(tempFilePath,UUID.randomUUID().toString()+"."+prefix).getPath();

         targetTempPath=new File(tempFilePath,UUID.randomUUID().toString()+".pdf").getPath();
        FileUtils.writeByte(sourceTempPath,source);
        byte []pdfBytes=null;
        if(officeFileConverterToPdf(sourceTempPath,targetTempPath)){
           pdfBytes= FileUtils.readByte(targetTempPath);
        }
        return pdfBytes;
    }
     static boolean officeFileConverterToPdf(String argInputFilePath, String argPdfPath) {
        if (argInputFilePath.isEmpty() || argPdfPath.isEmpty() || getFileSufix(argInputFilePath).isEmpty()) {
            return false;
        }

        String suffix = getFileSufix(argInputFilePath);

        File file = new File(argInputFilePath);
        if (!file.exists()) {
            return false;
        }

        // PDF如果不存在则创建文件夹
        file = new File(argPdfPath);
        if (!file.exists()) {
            file.mkdir();
        }

        // 如果输入的路径为PDF 则生成失败
        if (suffix.equals("pdf")) {
            System.out.println("PDF not need to convert!");
            return false;
        }

        switch (suffix) {
            case "doc":
            case "docx":
            case "txt":
                return wordToPDF(argInputFilePath, argPdfPath);
            case "xls":
            case "xlsx":
                return excelToPdf(argInputFilePath, argPdfPath);
            case "ppt":
            case "pptx":
                return pptToImg(argInputFilePath, argPdfPath);
        }

        return false;
    }

    /**
     * converter word to pdf
     *
     * @param wordPath
     * @param pdfPath
     * @return
     */
     static boolean wordToPDF(String wordPath, String pdfPath) {
        ActiveXComponent msWordApp = new ActiveXComponent("Word.Application");
        msWordApp.setProperty("Visible", new Variant(false));

        Dispatch docs = Dispatch.get(msWordApp, "Documents").toDispatch();
        // long pdfStart = System.currentTimeMillis();
        Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[]{wordPath, new Variant(false), new Variant(true)}, new int[1]).toDispatch();

        deletePdf(pdfPath);

        Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{pdfPath, new Variant(WDFO_RMATPDF)}, new int[1]);
        // long pdfEnd = System.currentTimeMillis();
         Dispatch.call(doc, "Close", false);
         return true;
    }

    /**
     * excel to pdf
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
     static boolean excelToPdf(String inputFile, String pdfFile) {
        ActiveXComponent activeXComponent = new ActiveXComponent("Excel.Application");
        activeXComponent.setProperty("Visible", false);

        deletePdf(pdfFile);

        Dispatch excels = activeXComponent.getProperty("Workbooks").toDispatch();
        Dispatch excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
        Dispatch.call(excel, "ExportAsFixedFormat", XLTYPE_PDF, pdfFile);
        Dispatch.call(excel, "Close", false);
        activeXComponent.invoke("Quit");
        return true;
    }


    /**
     * ppt to img
     *
     * @param inputFile
     * @param imgFile
     * @return
     */
     static boolean pptToImg(String inputFile, String imgFile) {
        // 打开word应用程序
        ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
        // 设置word不可见，office可能有限制
        // app.setProperty("Visible", false);
        // 获取word中国所打开的文档，返回Documents对象
        Dispatch files = app.getProperty("Presentations").toDispatch();
        // 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
        Dispatch file = Dispatch.call(files, "open", inputFile, true, true, false).toDispatch();
        // 调用Document对象的SaveAs方法，将文档保存为pdf格式
        // Dispatch.call(doc, "ExportAsFixedFormat", outputFile,
        // PPT_TO_PDF);
        Dispatch.call(file, "SaveAs", imgFile, PPT_SAVEAS_JPG);
        // 关闭文档
        // Dispatch.call(file, "Close", false);
        Dispatch.call(file, "Close");
        // 关闭word应用程序
        // app.invoke("Quit", 0);
        app.invoke("Quit");
        return true;
    }

    /**
     * get file extension
     *
     * @param argFilePath
     * @return
     */
    public static String getFileSufix(String argFilePath) {
        int splitIndex = argFilePath.lastIndexOf(".");
        return argFilePath.substring(splitIndex + 1);
    }



    /**
     * 如果PDF存在则删除PDF
     *
     * @param pdfPath
     */
    private static void deletePdf(String pdfPath) {
        File pdfFile = new File(pdfPath);
        if (pdfFile.exists()) {
            pdfFile.delete();
        }
    }
}
