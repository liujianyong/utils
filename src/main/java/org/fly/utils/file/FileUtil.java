package org.fly.utils.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.apache.commons.io.output.FileWriterWithEncoding;
/**
 * 
 *  
 *
 */
public class FileUtil {
    /**
     * @param fileName
     *            the name of the file to write to, not null
     * @param encoding
     *            the encoding to use, not null
     * @throws IOException
     *             in case of an I/O error
     */
    public static void writeToFile(String fileName, String content, String encoding) throws IOException {

        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new FileWriterWithEncoding(fileName, encoding)));
            out.print(content);
        } catch (IOException e) {
            throw e;
        } finally {
            if (out != null) {
                out.close();
            }
        }

    }
}
