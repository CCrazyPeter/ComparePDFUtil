package com.compare.pdf;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;

import com.testautomationguru.utility.CompareMode;
import com.testautomationguru.utility.PDFUtil;


public class PDFCompare {
	private static int reportStatusCode;
	private static String reportContentType;
	public static String outPDFPathNameA;
	public static String outPDFPathNameB;
	
	public void pdfTextCompare(String file1, String file2, String imgPath, int pageNumbers){
		PDFUtil pdfUtil = new PDFUtil();
		
		file1="D:/WorkSpace/PDFRearchByPersonal/PDFUtilDemo-master/PDFUtilAutomation/AllProd.pdf";
		file2="D:/WorkSpace/PDFRearchByPersonal/PDFUtilDemo-master/PDFUtilAutomation/ALLRC.pdf";
		
		// compares the pdf documents &amp; returns a boolean
		// true if both files have same content. false otherwise.
		// Default is CompareMode.TEXT_MODE
		pdfUtil.setCompareMode(CompareMode.TEXT_MODE);
		//if you need to store the result
		pdfUtil.highlightPdfDifference(true);
		pdfUtil.setImageDestinationPath(imgPath);
		
		for(int i=1;i<=pageNumbers;i++){
			try {
				if(!pdfUtil.compare(file1, file2, i)){
					System.out.println("Page Number Mismatch: " +i);
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pdfVisualCompare(String file1, String file2, String imgPath, int pageNumbers){
		PDFUtil pdfUtil = new PDFUtil();
		
		// compares the pdf documents &amp; returns a boolean
		// true if both files have same content. false otherwise.
		// Default is CompareMode.TEXT_MODE
		pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
		//if you need to store the result
		pdfUtil.highlightPdfDifference(true);
		pdfUtil.setImageDestinationPath(imgPath);
		
		for(int i=1;i<=pageNumbers;i++){
			try {
				pdfUtil.compare(file1, file2, i);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void pdfVisualCompare(String urlA, String urlB, String imgPath, String outPath) throws IOException, Exception{
		createdGetReport(urlA, urlB, outPath);
		
		/*get PDF doc all pages*/
		File file = new File(outPDFPathNameA);
		PDDocument pdfReader = PDDocument.load(file);             
		int pageNumbers = pdfReader.getNumberOfPages();
		
		PDFUtil pdfUtil = new PDFUtil();
		pdfUtil.setCompareMode(CompareMode.VISUAL_MODE);
		pdfUtil.highlightPdfDifference(true);
		pdfUtil.setImageDestinationPath(imgPath);
		
		for(int i=1;i<=pageNumbers;i++)
		{
			try {
				pdfUtil.compare(outPDFPathNameA, outPDFPathNameB, i);				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void createdGetReport(String urlA,String urlB, String outPDFPath) {
		outPDFPathNameA = outPDFPath+"ComparePDF_A.pdf";
		HttpGet(urlA, outPDFPathNameA);
		
		outPDFPathNameB = outPDFPath+"ComparePDF_B.pdf";
		HttpGet(urlB, outPDFPathNameB);
	}
	public static void HttpGet(String url, String outPDFPathName) {
		HttpGet get = new HttpGet(url);
		try (FileOutputStream pdfStream = new FileOutputStream(outPDFPathName);
				CloseableHttpClient httpClient = HttpClients.createDefault();
				CloseableHttpResponse httpRsp = httpClient.execute(get)) {			
			httpRsp.getEntity().writeTo(pdfStream);
			reportStatusCode = httpRsp.getStatusLine().getStatusCode();
			reportContentType = httpRsp.getFirstHeader("Content-Type").getValue();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void AddImageToWord(String imgPath) throws InvalidFormatException, FileNotFoundException, IOException{
		 XWPFDocument doc = new XWPFDocument();
	        XWPFParagraph p = doc.createParagraph();
	        XWPFRun xwpfRun = p.createRun();
	        
	        File folder = new File(imgPath);
	        File[] listOfFiles = folder.listFiles();

	            for (int i = 0; i < listOfFiles.length; i++) {
	              if (listOfFiles[i].isFile()) {
	                System.out.println("File " + listOfFiles[i].getName());
	                String imgFile = imgPath +listOfFiles[i].getName();
	                int format=XWPFDocument.PICTURE_TYPE_PNG;
		            xwpfRun.setText(listOfFiles[i].getName());
		            xwpfRun.addBreak();
		            xwpfRun.addPicture (new FileInputStream(imgFile), format, imgFile, Units.toEMU(500), Units.toEMU(500)); // 200x200 pixels
		            xwpfRun.addBreak();
		            xwpfRun.addBreak();
	              } else if (listOfFiles[i].isDirectory()) {
	                System.out.println("Directory " + listOfFiles[i].getName());
	              }
	            }
	            	       
	        FileOutputStream out = new FileOutputStream(imgPath + "PdfDiffConsolidated.docx");
	        doc.write(out);
	        out.close();
	        
	}
	
	public static File[] getFileList(String strPath)
	{
		File folder = new File(strPath);
		File[] listOfFiles = folder.listFiles();
		
		return listOfFiles;
	}
	
			
}
