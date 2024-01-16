package com.odk.template.util.common;

import java.io.*;

/**
 * FileUtil
 *
 * @description:
 * @version: 1.0
 * @author: oubin on 2024/1/16
 */
public class FileUtil {

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
