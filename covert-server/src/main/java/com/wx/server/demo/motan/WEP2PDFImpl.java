package com.wx.server.demo.motan;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.weibo.api.motan.config.springsupport.annotation.MotanService;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
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
    public static final int A4_width = 210;
    public static final int A4_height = 297;
    public static final int A3_width = 297;
    public static final int A3_height = 420;
    public static int scala = 4;
    // private static final int msoTrue = -1;
    // private static final int msofalse = 0;


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

    public static BufferedImage scalaImage(BufferedImage sourceImg, int toWidth, int toHeight) {
        BufferedImage result = new BufferedImage(toWidth, toHeight,
                BufferedImage.TYPE_INT_RGB);

        result.getGraphics().drawImage(
                sourceImg.getScaledInstance(toWidth, toHeight,
                        java.awt.Image.SCALE_SMOOTH), 0, 0, null);
        return result;
    }

    public static byte[][] combineImg(String sourceFile, int row, int col, String paperType, Integer page) {

        if (page == null) page = 0;
        int width = 0;
        int height = 0;
        if (paperType.equals("A4")) {
            width = A4_width * scala;
            height = A4_height * scala;
        } else if (paperType.equals("A3")) {
            width = A3_width * scala;
            height = A3_height * scala;
        }
        if (col >= row) {
            int temp = width;
            width = height;
            height = temp;
        }
        File file = new File(sourceFile);
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            assert files != null;
            int len = files.length;
            int perLen = row * col;
            int destLen = (int) Math.ceil(((float) len) / perLen);

            int count = 0;
            Arrays.sort(files, (o1, o2) -> {
                int i1 = Integer.parseInt(o1.getName().split("\\.")[0]);
                int i2 = Integer.parseInt(o2.getName().split("\\.")[0]);
                return Integer.compare(i1, i2);
            });
            destLen = page == 0 ? destLen : page + 1;

            byte[][]images=new byte[destLen-page][];
            for (int i = page; i < destLen; i++) {

                BufferedImage result = new BufferedImage(width, height,
                        BufferedImage.TYPE_INT_RGB);
                Graphics graphics = result.getGraphics();
                graphics.setColor(Color.WHITE);
                graphics.fillRect(0, 0, width, height);
                for (int j = 0; j < perLen && count < len; j++) {
                    BufferedImage image = null;
                    try {
                        image = ImageIO.read(files[count]);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (image != null) {
                        int toWidth = width / col;
                        int toHeight = height / row;
                        BufferedImage bufferedImage = scalaImage(image, toWidth, toHeight);
                        graphics.drawImage(bufferedImage, ((j % col) * toWidth), (j / col) * toHeight, null);

                    }
                    count++;

                }
                try {
                    ByteArrayOutputStream out = new ByteArrayOutputStream();
                    ImageIO.write(result, "jpg",out);
                    images[i-page]=out.toByteArray();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

            return images;

        }
        return null;


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
