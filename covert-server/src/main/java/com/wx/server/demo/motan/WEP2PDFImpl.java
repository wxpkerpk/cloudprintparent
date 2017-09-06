package com.wx.server.demo.motan;

import ch.qos.logback.core.util.FileUtil;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FileSystemUtils;
import utils.FileUtils;
import utils.FileUtils$;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@MotanService
public class WEP2PDFImpl implements WEP2PDF {

    @Override
    public byte[] covert(byte[] data) {
        return new byte[0];
    }

    private static final int WDFO_RMATPDF = 17;
    private static final int XLTYPE_PDF = 0;
    private static final int PPT_SAVEAS_PDF = 32;
    public static final int WORD_HTML = 8;
    public static final int WORD_TXT = 7;
    public static final int EXCEL_HTML = 44;
    public static final int PPT_SAVEAS_JPG = 17;

    // private static final int msoTrue = -1;
    // private static final int msofalse = 0;


    @Value("${covert.tempPath}")
    private String tempPath;
    public static void main(String[] s) {

        String source = "C:\\Users\\wx\\Downloads\\images";
        String target = "C:\\Users\\wx\\Downloads\\toimage";


    }


    public static boolean pdf2Img(String soursePath, String targetPath) throws IOException {

        if (!soursePath.endsWith("pdf")) return false;
        File file = new File(soursePath);
        PDDocument doc = null;
        try {
            doc = PDDocument.load(file);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        PDFRenderer renderer = new PDFRenderer(doc);
        int pageCount = doc.getNumberOfPages();
        for (int i = 0; i < pageCount; i++) {
            BufferedImage image; // Windows native DPI
            File imgFile = new File(targetPath, i + ".png");
            image = renderer.renderImageWithDPI(i, 300);
            ImageIO.write(image, "PNG", imgFile);
        }
        return true;
    }
//    public static boolean pdf2Img(String soursePath, String targetPath, int dpi) throws IOException {
//
//        if (!soursePath.endsWith("pdf")) return false;
//        // ICEpdf document class
//        Document document = null;
//
//        float scale = dpi / 72f;
//        document = new Document();
//        try {
//
//
//            document.setFile(soursePath);
//        } catch (Exception e1) {
//            e1.printStackTrace();
//        }
//        // maxPages = document.getPageTree().getNumberOfPages();
//
//
//        int pages = document.getNumberOfPages();
//        for (int i = 0; i < pages; i++) {
//
//            BufferedImage img = (BufferedImage) document.getPageImage(i,
//                    GraphicsRenderingHints.SCREEN, Page.BOUNDARY_CROPBOX, 0,
//                    scale);
//            File imgFile = new File(targetPath, i + ".jpg");
//            ImageIO.write(img, "jpg", imgFile);
//        }
//        return true;
//    }




    public   byte[]officeFile2PdfBytes(byte[] source)
    {
        String sourceTempPath= UUID.randomUUID().toString();
        String targetTempPath=UUID.randomUUID().toString();
        FileUtils.writeFile(source,sourceTempPath);
        byte []pdfBytes=null;
        if(officeFileConverterToPdf(sourceTempPath,targetTempPath)){
           pdfBytes= FileUtils.readFile(targetTempPath);
        }
        return pdfBytes;
    }
    public static boolean officeFileConverterToPdf(String argInputFilePath, String argPdfPath) {
        if (argInputFilePath.isEmpty() || argPdfPath.isEmpty() || getFileSufix(argInputFilePath).isEmpty()) {
            return false;
        }

        String suffix = getFileSufix(argInputFilePath);

        File file = new File(argInputFilePath);
        if (!file.exists()) {
            return false;
        }

        // PDF如果不存在则创建文件夹
        file = new File(getFilePath(argPdfPath));
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
    public static boolean wordToPDF(String wordPath, String pdfPath) {
        ActiveXComponent msWordApp = new ActiveXComponent("Word.Application");
        msWordApp.setProperty("Visible", new Variant(false));

        Dispatch docs = Dispatch.get(msWordApp, "Documents").toDispatch();
        // long pdfStart = System.currentTimeMillis();
        Dispatch doc = Dispatch.invoke(docs, "Open", Dispatch.Method, new Object[]{wordPath, new Variant(false), new Variant(true)}, new int[1]).toDispatch();

        deletePdf(pdfPath);

        Dispatch.invoke(doc, "SaveAs", Dispatch.Method, new Object[]{pdfPath, new Variant(WDFO_RMATPDF)}, new int[1]);
        // long pdfEnd = System.currentTimeMillis();
        if (null != doc) {
            Dispatch.call(doc, "Close", false);
        }
        return true;
    }

    /**
     * excel to pdf
     *
     * @param inputFile
     * @param pdfFile
     * @return
     */
    public static boolean excelToPdf(String inputFile, String pdfFile) {
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
    public static boolean pptToImg(String inputFile, String imgFile) {
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
     * subString file path
     *
     * @param argFilePath file path
     * @return filePaths
     */
    public static String getFilePath(String argFilePath) {
        int pathIndex = argFilePath.lastIndexOf("/");
        return argFilePath.substring(0, pathIndex);
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
