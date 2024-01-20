package com.odk.odktemplatemanager.util;

import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.ooxml.extractor.POIXMLTextExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;

/**
 * FileUtil
 * 处理docx：https://blog.csdn.net/m0_63270887/article/details/132871594
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/16
 */
public class FileUtil {

    private static final Logger logger = LoggerFactory.getLogger(FileUtil.class);

    /**
     * 删除本地文件
     *
     * @param filePath
     * @return
     */
    public static void deleteFile(String filePath) {
        //根据路径创建文件对象
        File file = new File(filePath);
        try {
            //路径是个文件且不为空时删除文件
            if(file.isFile()&&file.exists()){
                file.delete();
            }
        } catch (Exception e) {
            logger.error("删除本地文件失败，文件地址：{}", filePath);
        }
    }

    /**
     * 保存图片到服务器
     *
     * @param filePath
     * @param input
     * @return
     * @throws IOException
     */
    public static String saveFile(String filePath, String fileId, String fileName, InputStream input) throws IOException {

        File file = new File(filePath);
        if (!file.getParentFile().exists()) {
            file.mkdirs();
        }
        filePath = filePath + generateFileName(fileId, fileName);
        OutputStream out = new FileOutputStream(filePath);
        byte[] data = new byte[2048];
        int len = 0;
        while ((len = input.read(data)) != -1) {
            out.write(data, 0, len);
        }
        if (input != null) {
            input.close();
        }
        out.close();
        return filePath;
    }

    /**
     * 获取文件内容
     *
     * @param inputStream
     * @return
     * @throws IOException
     */
    public static String getDocContents(String filePath, String fileId, String fileName) throws IOException {

        filePath = filePath + generateFileName(fileId, fileName);
        String buffer = "";
        try {
            if (filePath.endsWith(".doc")) {
                InputStream is = Files.newInputStream(new File(filePath).toPath());
                WordExtractor ex = new WordExtractor(is);
                buffer = ex.getText();
                ex.close();
            } else if (filePath.endsWith(".docx")) {
                OPCPackage opcPackage = POIXMLDocument.openPackage(filePath);
                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);
                buffer = extractor.getText();
                extractor.close();
            }
        } catch (Exception e) {
            logger.error("获取文件内容发生异常：", e);

        }
        return buffer;
    }

    /**
     * 文件名
     *
     * @param fileId
     * @param fileName
     * @return
     */
    private static String generateFileName(String fileId, String fileName) {
        return fileId + "_" + fileName;
    }
}
