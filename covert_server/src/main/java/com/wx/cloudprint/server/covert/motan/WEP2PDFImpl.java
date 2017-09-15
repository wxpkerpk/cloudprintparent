package com.wx.cloudprint.server.covert.motan;


import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComFailException;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

import org.apache.pdfbox.pdmodel.PDDocument;

import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.beans.factory.annotation.Value;
import util.FileUtils;


import javax.imageio.ImageIO;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
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
    private String tempFilePath;

    public static void main(String[] s) {


        String source = "C:\\Users\\wx\\Documents\\工作簿1.xlsx";
        String target = "C:\\Users\\wx\\Downloads\\toimage";
        String prefix = source.split("\\.")[1];
        int a[] = new int[1];
        try {
            int i = 1;
            byte datas[][] = new WEP2PDFImpl().offceBytes2imgsBytes(FileUtils.readByte(source), prefix);

            for (byte[] data : datas) {
                FileUtils.writeByte(new File(target, (i++) + ".jpg").getPath(), data);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public byte[][] offceBytes2imgsBytes(byte[] offceBytes, String prefix) {
        if (tempFilePath == null || tempFilePath.equals("")) {
            tempFilePath = new File("").getAbsolutePath();
        }
        if (prefix.equals("ppt") || prefix.equals("pptx")) {
            File file = new File(tempFilePath);
            if (!file.exists()) file.mkdir();
            String targetTempPath = null;
            String sourceTempPath = new File(tempFilePath, UUID.randomUUID().toString() + "." + prefix).getPath();
            targetTempPath = new File(tempFilePath, UUID.randomUUID().toString()).getPath();
            FileUtils.writeByte(sourceTempPath, offceBytes);
            officeFileConverterToPdf(sourceTempPath, targetTempPath);
            return readFileImages(targetTempPath);
        } else if (prefix.equals("pdf")) {
            try {
                return pdf2Img(offceBytes, 200);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {//word or excel
            try {
                byte[] pdfBytes = officeFile2PdfBytes(offceBytes, prefix);

                return pdf2Img(pdfBytes, 200);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    byte[][] readFileImages(String path) {

        File file = new File(path);
        File[] fileList = file.listFiles();
        Arrays.sort(fileList, (o1, o2) -> {
            String name1 = o1.getName().split("\\.")[0];
            String name2 = o2.getName().split("\\.")[0];


            return name1.compareTo(name2);
        });
        byte[][] result = new byte[fileList.length][];
        for (int i = 0; i < fileList.length; i++) {
            try {
                result[i] = FileUtils.readByte(fileList[i].getPath());
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
    public static byte[][] pdf2Img(byte[] pdfBytes, int dpi) throws IOException {

        PDDocument doc = PDDocument.load(pdfBytes);
        int pageCount = doc.getNumberOfPages();
        byte[][] result = new byte[pageCount][];
        System.out.println(pageCount);
        PDFRenderer renderer = new PDFRenderer(doc);

        for (int i = 0; i < pageCount; i++) {
            BufferedImage image = renderer.renderImageWithDPI(i, dpi);
            ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();

            ImageIO.write(image, "jpg", arrayOutputStream);
            result[i] = arrayOutputStream.toByteArray();
            arrayOutputStream.close();

        }
        doc.close();
        System.out.println("over");
        return result;
    }


    byte[] officeFile2PdfBytes(byte[] source, String prefix) throws Exception {

        File file = new File(tempFilePath);
        if (!file.exists()) file.mkdir();

        String targetTempPath = null;
        String sourceTempPath = new File(tempFilePath, UUID.randomUUID().toString() + "." + prefix).getPath();

        targetTempPath = new File(tempFilePath, UUID.randomUUID().toString() + ".pdf").getPath();
        FileUtils.writeByte(sourceTempPath, source);
        byte[] pdfBytes = null;
        if (officeFileConverterToPdf(sourceTempPath, targetTempPath)) {
            pdfBytes = FileUtils.readByte(targetTempPath);
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
                return doc2pdf(argInputFilePath, argPdfPath);
            case "xls":
            case "xlsx":
                return excelToPdf(argInputFilePath, argPdfPath);
            case "ppt":
            case "pptx":
                return ppt2Img(argInputFilePath, argPdfPath);
        }
        return false;
    }

    public static boolean doc2pdf(String srcFilePath, String pdfFilePath) {
        ActiveXComponent app = null;
        Dispatch doc = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("Word.Application");
            app.setProperty("Visible", false);
            Dispatch docs = app.getProperty("Documents").toDispatch();
            doc = Dispatch.invoke(docs, "Open", Dispatch.Method,
                    new Object[]{srcFilePath,
                            new Variant(false),
                            new Variant(true),//是否只读
                            new Variant(false),
                            new Variant("pwd")},
                    new int[1]).toDispatch();
//          Dispatch.put(doc, "Compatibility", false);  //兼容性检查,为特定值false不正确
            Dispatch.put(doc, "RemovePersonalInformation", false);
            Dispatch.call(doc, "ExportAsFixedFormat", pdfFilePath, WDFO_RMATPDF); // word保存为pdf格式宏，值为17

            return true; // set flag true;
        } catch (ComFailException e) {
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (doc != null) {
                Dispatch.call(doc, "Close", false);
            }
            if (app != null) {
                app.invoke("Quit", 0);
            }
            ComThread.Release();
        }
    }

    /**
     * excel to pdf
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    static boolean excelToPdf(String inputFile, String pdfFile) {
        ActiveXComponent activeXComponent = null;
        Dispatch excel = null;
        try {
            ComThread.InitSTA();
            activeXComponent = new ActiveXComponent("Excel.Application");
            activeXComponent.setProperty("Visible", false);

            deletePdf(pdfFile);

            Dispatch excels = activeXComponent.getProperty("Workbooks").toDispatch();
            excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();

            Dispatch.call(excel, "ExportAsFixedFormat", XLTYPE_PDF, pdfFile);
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (activeXComponent != null) activeXComponent.invoke("Quit", new Variant[]{});
            if (excel != null) Dispatch.call(excel, "Close", false);
            ComThread.Release();
        }

    }


    public static boolean ppt2Img(String srcFilePath, String pdfFilePath) {
        ActiveXComponent app = null;
        Dispatch ppt = null;
        try {
            ComThread.InitSTA();
            app = new ActiveXComponent("PowerPoint.Application");
            Dispatch ppts = app.getProperty("Presentations").toDispatch();

            // 因POWER.EXE的发布规则为同步，所以设置为同步发布
            ppt = Dispatch.call(ppts, "Open", srcFilePath, true,// ReadOnly
                    true,// Untitled指定文件是否有标题
                    false// WithWindow指定文件是否可见
            ).toDispatch();

            Dispatch.call(ppt, "SaveAs", pdfFilePath, PPT_SAVEAS_JPG); //ppSaveAsPDF为特定值32

            return true; // set flag true;
        } catch (ComFailException e) {
            return false;
        } catch (Exception e) {
            return false;
        } finally {
            if (ppt != null) {
                Dispatch.call(ppt, "Close");
            }
            if (app != null) {
                app.invoke("Quit");
            }
            ComThread.Release();
        }
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
