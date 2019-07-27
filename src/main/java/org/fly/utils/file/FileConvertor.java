package org.fly.utils.file;


import java.io.*;
import java.nio.charset.Charset;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipOutputStream;

public class FileConvertor {

    /**
     * 使用GBK编码可以避免压缩中文文件名乱码
     */
    private static final String CHINESE_CHARSET = "GBK";

    /**
     * 文件读取缓冲区大小
     */
    private static final int CACHE_SIZE = 1024;

    public static void main(String[] args) {
        FileConvertor convertor = new FileConvertor();
        FileConvertor.unZip("D:\\test\\diamond-client-3.7.0-sopen-sources.jar.src.zip", "D:\\test\\diamond-client-3.7.0-sopen-sources.jar.src\\");
    }

    /**
     * 压缩文件
     *
     * @param sourceFolder 压缩文件夹
     * @param zipFilePath  压缩文件输出路径
     */
    public static void zip(String sourceFolder, String zipFilePath) {
        OutputStream os = null;
        BufferedOutputStream bos = null;
        ZipOutputStream zos = null;
        try {
            os = new FileOutputStream(zipFilePath);
            bos = new BufferedOutputStream(os);
            zos = new ZipOutputStream(bos, Charset.forName("UTF-8"));
            // 解决中文文件名乱码，需要ant包
            File file = new File(sourceFolder);
            String basePath = null;
            if (file.isDirectory()) {// 压缩文件夹
                basePath = file.getPath();
            } else {
                basePath = file.getParent();
            }
            zipFile(file, basePath, zos);

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (zos != null) {
                    zos.closeEntry();
                    zos.close();
                }
                if (bos != null) {
                    bos.close();
                }
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 递归压缩文件
     *
     * @param parentFile
     * @param basePath
     * @param zos
     * @throws Exception
     */
    private static void zipFile(File parentFile, String basePath,
                                ZipOutputStream zos) throws Exception {
        File[] files = new File[0];
        if (parentFile.isDirectory()) {
            files = parentFile.listFiles();
        } else {
            files = new File[1];
            files[0] = parentFile;
        }
        String pathName;
        InputStream is;
        BufferedInputStream bis;
        byte[] cache = new byte[CACHE_SIZE];
        for (File file : files) {
            if (file.isDirectory()) {
                pathName = file.getPath().substring(basePath.length() + 1)
                        + File.separator;
                zos.putNextEntry(new ZipEntry(pathName));
                zipFile(file, basePath, zos);
            } else {
                pathName = file.getPath().substring(basePath.length() + 1);
                is = new FileInputStream(file);
                bis = new BufferedInputStream(is);
                zos.putNextEntry(new ZipEntry(pathName));
                int nRead = 0;
                while ((nRead = bis.read(cache, 0, CACHE_SIZE)) != -1) {
                    zos.write(cache, 0, nRead);
                }
                bis.close();
                is.close();
            }
        }
    }

    /**
     * 解压压缩包
     *
     * @param zipFilePath 压缩文件路径
     * @param destDir     解压目录
     */
    public static void unZip(String zipFilePath, String destDir) {
        ZipFile zipFile = null;
        InputStream is = null;
        InputStreamReader isr = null;
        BufferedReader br = null;

        OutputStream os = null;
        OutputStreamWriter osw = null;
        BufferedWriter bw = null;
        try {

            zipFile = new ZipFile(zipFilePath, Charset.forName(CHINESE_CHARSET));
            Enumeration zipEntries = zipFile.entries();
            File destFile, parentFile;
            ZipEntry entry;
            byte[] cache = new byte[CACHE_SIZE];
            while (zipEntries.hasMoreElements()) {
                entry = (ZipEntry) zipEntries.nextElement();
                if (entry.isDirectory()) {
                    new File(destDir + entry.getName()).mkdirs();
                    continue;
                }
                isr = new InputStreamReader(zipFile.getInputStream(entry), "gb2312");
                br = new BufferedReader(isr);
                destFile = new File(destDir + entry.getName());
                parentFile = destFile.getParentFile();
                if (parentFile != null && (!parentFile.exists())) {
                    parentFile.mkdirs();
                }
                os   = new FileOutputStream(destFile);
                osw   = new OutputStreamWriter(os ,"UTF-8");
                bw = new BufferedWriter(osw);
                String line;
                for (line = br.readLine(); line != null; line = br.readLine()) {
                    bw.write(line + "\n");
                }
                bw.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bw != null) {
                try {
                    bw.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (osw != null) {
                try {
                    osw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (os != null) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
