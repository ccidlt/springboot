package com.ds.utils;

import com.ds.entity.Result;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Slf4j
public class FileUtils {

    /**
     * 单文件上传
     *
     * @param file
     * @param path
     * @return
     */
    public static Result singleFileupload(MultipartFile file, String path) {
        /*ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest)request;
        MultipartFile file = multipartHttpServletRequest.getFile("file");*/
        Result result = Result.ok();
        String originalFilename = file.getOriginalFilename();
        String hz = originalFilename.substring(originalFilename.lastIndexOf("."));
        File filedir = new File(path);
        if (!filedir.exists()) {
            filedir.mkdirs();
        }
        String filename = UUID.randomUUID().toString().replace("-", "") + hz;
        try {
            file.transferTo(new File(filedir, filename));
            result = Result.ok(path + "/" + filename);
        } catch (IOException e) {
            e.printStackTrace();
            result = Result.fail();
        }
        return result;
    }

    /**
     * 多文件上传
     *
     * @param path
     * @return
     */
    public static Result multipleFileUpload(String path) {
        Result result = Result.ok();
        List<Map<String, String>> fileList = new ArrayList<>();
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        /*MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
        Iterator<String> fileNames = multipartRequest.getFileNames();
        if (fileNames.hasNext()) {
            List<MultipartFile> files = multipartRequest.getFiles("files");
        }*/
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
        //判断 request 是否有文件上传,即多部分请求
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            MultiValueMap<String, MultipartFile> multiFileMap = multiRequest.getMultiFileMap();
            List<MultipartFile> fileSet = new LinkedList<>();
            for (Map.Entry<String, List<MultipartFile>> temp : multiFileMap.entrySet()) {
                fileSet = temp.getValue();
            }
            for (MultipartFile file : fileSet) {
                String originalFilename = file.getOriginalFilename();
                String hz = originalFilename.substring(originalFilename.lastIndexOf("."));
                File filedir = new File(path);
                if (!filedir.exists()) {
                    filedir.mkdirs();
                }
                String filename = UUID.randomUUID().toString().replace("-", "") + hz;
                try {
                    file.transferTo(new File(filedir, filename));
                    Map<String, String> fileMap = new HashMap<>();
                    fileMap.put(originalFilename, path + "/" + filename);
                    fileList.add(fileMap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        result = Result.ok(fileList);
        return result;
    }

    /**
     * 将文件以流的形式一次性读取到内存，通过响应输出流输出到前端
     *
     * @param path     想要下载的文件的路径
     * @param response
     * @功能描述 预览文件:文件预览仅适用文件格式为jpg、pdf、txt等文件格式，其他格式文件直接以附件形式下载。
     */
    public static void preview(String path, HttpServletResponse response) {
        try {
            // path是指想要下载的文件的路径
            File file = new File(path);
            log.info(file.getPath());
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            log.info("文件后缀名：" + ext);

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "inline;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            response.setHeader("Access-Control-Expose-Headers", "content-disposition");
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 将文件以流的形式一次性读取到内存，通过响应输出流输出到前端
     *
     * @param path     想要下载的文件的路径
     * @param response
     * @功能描述 下载文件:
     */
    public static void download(String path, HttpServletResponse response) {
        try {
            // path是指想要下载的文件的路径
            File file = new File(path);
            log.info(file.getPath());
            // 获取文件名
            String filename = file.getName();
            // 获取文件后缀名
            String ext = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
            log.info("文件后缀名：" + ext);

            // 将文件写入输入流
            FileInputStream fileInputStream = new FileInputStream(file);
            InputStream fis = new BufferedInputStream(fileInputStream);
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();

            // 清空response
            response.reset();
            // 设置response的Header
            response.setCharacterEncoding("UTF-8");
            //Content-Disposition的作用：告知浏览器以何种方式显示响应返回的文件，用浏览器打开还是以附件的形式下载到本地保存
            //attachment表示以附件方式下载 inline表示在线打开 "Content-Disposition: inline; filename=文件名.mp3"
            // filename表示文件的默认名称，因为网络传输只支持URL编码的相关支付，因此需要将文件名URL编码后进行传输,前端收到后需要反编码才能获取到真正的名称
            response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(filename, "UTF-8"));
            // 告知浏览器文件的大小
            response.addHeader("Content-Length", "" + file.length());
            OutputStream outputStream = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            response.setHeader("Access-Control-Expose-Headers", "content-disposition");
            outputStream.write(buffer);
            outputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * 将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存，通过响应输出流输出到前端
     *
     * @param path     指想要下载的文件的路径
     * @param response
     * @功能描述 下载文件:将输入流中的数据循环写入到响应输出流中，而不是一次性读取到内存
     */
    public static void downloadLocal(String path, HttpServletResponse response) throws IOException {
        // 读到流中
        InputStream inputStream = new FileInputStream(path);// 文件的存放路径
        response.reset();
        response.setContentType("application/octet-stream");
        String filename = new File(path).getName();
        response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        response.setHeader("Access-Control-Expose-Headers", "content-disposition");
        ServletOutputStream outputStream = response.getOutputStream();
        byte[] b = new byte[1024];
        int len;
        //从输入流中读取一定数量的字节，并将其存储在缓冲区字节数组中，读到末尾返回-1
        while ((len = inputStream.read(b)) > 0) {
            outputStream.write(b, 0, len);
        }
        inputStream.close();
    }

    /**
     * 下载网络文件到本地
     *
     * @param path       下载后的文件路径和名称
     * @param netAddress 文件所在网络地址
     * @功能描述 网络文件下载到服务器本地
     */
    public static void downloadNet(String netAddress, String path) throws IOException {
        URL url = new URL(netAddress);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();
        FileOutputStream fileOutputStream = new FileOutputStream(path);

        int bytesum = 0;
        int byteread;
        byte[] buffer = new byte[1024];
        while ((byteread = inputStream.read(buffer)) != -1) {
            bytesum += byteread;
            System.out.println(bytesum);
            fileOutputStream.write(buffer, 0, byteread);
        }
        fileOutputStream.close();
    }

    /**
     * 网络文件获取到服务器后，经服务器处理后响应给前端
     *
     * @param netAddress
     * @param filename
     * @param isOnLine
     * @param response
     * @功能描述 网络文件获取到服务器后，经服务器处理后响应给前端
     */
    public static void netDownLoadNet(String netAddress, String filename, boolean isOnLine, HttpServletResponse response) throws Exception {

        URL url = new URL(netAddress);
        URLConnection conn = url.openConnection();
        InputStream inputStream = conn.getInputStream();

        response.reset();
        response.setContentType(conn.getContentType());
        if (isOnLine) {
            // 在线打开方式 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "inline; filename=" + URLEncoder.encode(filename, "UTF-8"));
        } else {
            //纯下载方式 文件名应该编码成UTF-8
            response.setHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(filename, "UTF-8"));
        }
        response.setHeader("Access-Control-Expose-Headers", "content-disposition");

        byte[] buffer = new byte[1024];
        int len;
        OutputStream outputStream = response.getOutputStream();
        while ((len = inputStream.read(buffer)) > 0) {
            outputStream.write(buffer, 0, len);
        }
        inputStream.close();
    }

    /**
     * 功能描述:  将文件打包的方法，需要传一个压缩路径，和一个文件，一次只将一个文件写入压缩包
     *
     * @param filePath     文件路径
     * @param zipOut       压缩流：new ZipOutputStream(new FileOutputStream(zipFilePath));传给前端new ZipOutputStream(response.getOutputStream());
     * @param realFileName 真实的文件名
     * @return void
     */
    public static void fileToZip(String filePath, ZipOutputStream zipOut, String realFileName) throws IOException {
        // 需要压缩的文件
        File file = new File(filePath);
        //创建文件输入流
        FileInputStream fileInput = new FileInputStream(filePath);
        // 缓冲
        byte[] bufferArea = new byte[1024 * 10];
        BufferedInputStream bufferStream = new BufferedInputStream(fileInput, 1024 * 10);
        // 将当前文件作为一个zip实体写入压缩流,realFileName代表压缩文件中的文件名称
        zipOut.putNextEntry(new ZipEntry(realFileName));
        int length = 0;
        // 写操作
        while ((length = bufferStream.read(bufferArea, 0, 1024 * 10)) != -1) {
            zipOut.write(bufferArea, 0, length);
        }
        //关闭流
        fileInput.close();
        // 需要注意的是缓冲流必须要关闭流,否则输出无效
        bufferStream.close();
        // 压缩流不必关闭,使用完后再关
    }

    /**
     * 表格导成pdf文件
     *
     * @param path     保存文件路径
     * @param fileName 文件名
     * @param titles   表格头
     * @param list     表格数据
     * @param fields   表格字段
     */
    public static void exportPdfTable(String path, String fileName, List<String> titles, List<Map<String, String>> list, List<String> fields) {
        Document document = new Document(PageSize.A4, 5, 5, 30, 30);
        //横向
        Rectangle pageSize = new Rectangle(PageSize.A4.getHeight(), PageSize.A4.getWidth());
        pageSize.rotate();
        document.setPageSize(pageSize);
        try {
            File fileDir = new File(path);
            if (!fileDir.exists()) {
                fileDir.mkdirs();
            }
            String filePath = path + "/" + System.currentTimeMillis() + "_" + RandomStringUtils.random(12, true, true) + ".pdf";
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(filePath));
            document.open();

            //表格高度
            int high = 20;
            //表格宽度
            int widthPercentage = 100;
            //创建字体
            BaseFont baseFont = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            //字体对象,这里可以创建一个方法
            Font size14font = new Font(baseFont, 14, Font.BOLD);  //大小为14的正常字体
            Font size10font = new Font(baseFont, 10, Font.NORMAL); //大小为10的正常字体

            Paragraph paragraph1 = new Paragraph(fileName, size14font);
            paragraph1.setAlignment(Paragraph.ALIGN_CENTER);
            document.add(paragraph1);
            Paragraph paragraph2 = new Paragraph(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), size10font);
            paragraph2.setAlignment(Paragraph.ALIGN_RIGHT);
            document.add(paragraph2);
            document.add(Chunk.NEWLINE);

            String[] name = titles.toArray(new String[0]);
            PdfPTable titleTable = new PdfPTable(name.length);//生成一个两列的表格
            titleTable.setWidthPercentage(widthPercentage);
            //该数组是每个表格的宽度
            float[] floats = new float[name.length];
            //循环将表头数据添加到第二行表格中
            for (int i = 0; i < name.length; i++) {
                titleTable.addCell(pdfTableStyle(name[i], size10font, high, true, true));
                floats[i] = 0.1f;
            }
            //设置表格的宽度
            titleTable.setTotalWidth(floats);
            document.add(titleTable);

            //将数据放入表格中
            for (int i = 0; i < list.size(); i++) {
                Map<String, String> map = list.get(i);
                PdfPTable dataTable = new PdfPTable(name.length);
                dataTable.setWidthPercentage(widthPercentage);
                dataTable.setTotalWidth(floats);
                //将数组中的数据按照顺序添加
                for (int j = 0; j < fields.size(); j++) {
                    dataTable.addCell(pdfTableStyle(map.get(fields.get(j)), size10font, high, true, true));
                }
                document.add(dataTable);
            }

            document.close();
            writer.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static PdfPCell pdfTableStyle(String content, Font font, int high, boolean isAlignCenter, boolean isAlignMidde) {
        PdfPCell pdfPCell = new PdfPCell(new Phrase(content, font));
        pdfPCell.setMinimumHeight(high);
        pdfPCell.setUseAscender(true); // 设置可以居中
        if (isAlignCenter) {
            pdfPCell.setHorizontalAlignment(PdfPCell.ALIGN_CENTER); // 设置水平居中
        }
        if (isAlignMidde) {
            pdfPCell.setVerticalAlignment(PdfPCell.ALIGN_MIDDLE); // 设置垂直居中
        }
        return pdfPCell;
    }

}
