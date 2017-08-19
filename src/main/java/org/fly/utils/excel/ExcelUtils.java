package org.fly.utils.excel;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.jxls.common.Context;
import org.jxls.util.JxlsHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;
import net.sf.jxls.reader.ReaderBuilder;
import net.sf.jxls.reader.XLSReader;
/**
 * excel导入导出的工具类
 * 
 *  liangzheng
 * @param <T>
 * 
 */
public class ExcelUtils {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

	public boolean writeExcel(String templateFile,Context context,String destPath) {	
		
		try(InputStream is= new FileInputStream(new File(templateFile));
				OutputStream os = new FileOutputStream(new File(destPath));) {
			JxlsHelper.getInstance().processTemplate(is, os, context);
			os.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
			return false;
		}
        return true;
	}

	public <T> List<T> readExcel(String xmlFile,String xslfilePath,Class<T> clazz,String var,String items) {	
		List<T> array = new ArrayList<T>();
		try(InputStream inputXML = new BufferedInputStream(this.getClass().getResourceAsStream(xmlFile));
				InputStream inputXLS = new BufferedInputStream(new FileInputStream(xslfilePath));) {
			XLSReader mainReader = ReaderBuilder.buildFromXML(inputXML);
			Map beans = new HashMap(); 
			Object obj=clazz.newInstance();
			beans.put(var,obj);
			beans.put(items,array);
			mainReader.read(inputXLS, beans);
		} catch (IOException | SAXException | InvalidFormatException | InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage(), e);
		} 
		return array;
	}
}
