package com.compare.pdf.test;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.compare.pdf.PDFCompare;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

/**
 * Created on 18/4/2019.
 * @author pzhang7
 *
 */
public class PDFCompareTest {
	
	
    /* Demo
     * Comapre AllProd.pdf and AllRC.pdf, the PDF in local
     */
    @Test
    public void pdfCompareFilePath_Demo_Test() throws IOException, Exception {
        Properties props=new Properties();

        props.load(PDFCompare.class.getResourceAsStream("/application.properties"));
        String file1 = props.getProperty("File_Demo_Source");
        String file2 = props.getProperty("File_Demo_Dest");
        String imgPath = props.getProperty("Demo_Img_Path");
        
		File file = new File(file1);
		PDDocument pdfReader = PDDocument.load(file);             
		int pageNumbers = pdfReader.getNumberOfPages();
        
        PDFCompare pdfCompare = new PDFCompare();
        pdfCompare.pdfVisualCompare(file1, file2, imgPath, pageNumbers);
        pdfCompare.AddImageToWord(imgPath);
    }
    
    /*
     * Use URL download PDF to local and compare it.
     */
    @Test
    public void pdfCompareUrlTest() throws IOException, Exception {
        Properties props=new Properties();

        props.load(PDFCompare.class.getResourceAsStream("/application.properties"));
        String urlA = props.getProperty("urlA");
        String urlB = props.getProperty("urlB");
        String imgPath = props.getProperty("Url_Img_Path");
        String outPath = "dataSource/outputReport/";
        
        PDFCompare pdfCompare = new PDFCompare();
        pdfCompare.pdfVisualCompare(urlA, urlB, imgPath, outPath);
        pdfCompare.AddImageToWord(imgPath);
    }
    
    @Test
    public void pdfCompareFilePathTest() throws IOException, Exception {
        Properties props=new Properties();

        props.load(PDFCompare.class.getResourceAsStream("/application.properties"));
        String file1 = props.getProperty("File_Source");
        String file2 = props.getProperty("File_Dest");
        String imgPath = props.getProperty("File_Img_Path");
        
		File file = new File(file1);
		PDDocument pdfReader = PDDocument.load(file);             
		int pageNumbers = pdfReader.getNumberOfPages();
        
        PDFCompare pdfCompare = new PDFCompare();
        pdfCompare.pdfVisualCompare(file1, file2, imgPath, pageNumbers);
        pdfCompare.AddImageToWord(imgPath);
    }
    
    @AfterClass
    public void deleteFolder() {
    	/**
    	 * Delete all image folder after run end.
    	 * Make sure our folder is clearly.
    	 */
    }
}
