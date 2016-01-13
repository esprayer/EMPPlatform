package jservice.jbof.classes.GenerQueryObject.action.util;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

import com.f1j.swing.JBook;
import java.io.*;

public class ExportXmlFile {
	JBook book = null;
	private String fileName = "";
	private String fileSavePath = "";
    public ExportXmlFile(){

    }

    public ExportXmlFile(JBook jbook,String pPath,String pFileName) throws Exception{
    	this.book = jbook;
    	this.fileSavePath = pPath;
    	this.fileName = pFileName;
    	ExportXml();
    }

    private void ExportXml() throws Exception{
    String encoding = "UTF-8"; // "UTF-8"字符编码(可解决中文乱码问题 )
    StringBuffer sb = new StringBuffer();
    //DataOutputStream rafs = null;
    FileWriter fw = null;
    BufferedWriter bw = null;
	try {
		//rafs = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(new File(fileSavePath))));
//		fw = new FileWriter(fileSavePath);
//		bw = new BufferedWriter(fw);
        bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
                fileSavePath), encoding));

		sb.append("<?xml version=\"1.0\"?>");
		sb.append("\n");
		sb.append("<?mso-application progid=\"Excel.Sheet\"?>");
		sb.append("\n");
		sb.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"");
		sb.append("\n");
		sb.append(" xmlns:o=\"urn:schemas-microsoft-com:office:office\"");
		sb.append("\n");
		sb.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"");
		sb.append("\n");
		sb.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"");
		sb.append("\n");
		sb.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">");
		sb.append("\n");
		sb.append("<Styles>\n");
		sb.append("<Style ss:ID=\"Default\" ss:Name=\"Normal\">\n");
		sb.append("<Alignment ss:Vertical=\"Center\"/>\n");
		sb.append("<Borders> " );
	    sb.append("<Border ss:Position=\"Bottom\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" ss:Color=\"#9999FF\"/>");
		sb.append("<Border ss:Position=\"Left\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" ss:Color=\"#9999FF\"/>");
		sb.append("<Border ss:Position=\"Right\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" ss:Color=\"#9999FF\"/>");
		sb.append("<Border ss:Position=\"Top\" ss:LineStyle=\"Continuous\" ss:Weight=\"1\" ss:Color=\"#9999FF\"/>");
		sb.append("</Borders\n>");
		sb.append("<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\n");
		sb.append("<Interior ss:Pattern=\"Solid\"/>\n");
		sb.append("<NumberFormat ss:Format=\"#,##0.00;\\-#,##0.00;\"/>\n");
		sb.append("<Protection/>\n");

		sb.append("</Style>\n");
		sb.append("<Style ss:ID=\"title\">");
		sb.append("<Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>");
		sb.append("<Borders/>");
		sb.append("<Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"16\" ss:Color=\"#000000\"/>");
	    sb.append("<Interior/>");
	    sb.append("<NumberFormat/>");
	    sb.append("<Protection/>");
	    sb.append("</Style>");

		sb.append("</Styles>\n");
		int recordcount = 50000;
		int currentRecord = 0;
		int rowTotal = this.book.getLastRow()+1;
		int colTotal = this.book.getLastCol()+1;
		sb.append("<Worksheet ss:Name=\""+this.fileName+"0\">");
		sb.append("\n");
		sb.append("<Table ss:ExpandedColumnCount=\"" + colTotal
				+ "\" ss:ExpandedRowCount=\"" + rowTotal
				+ "\" x:FullColumns=\"1\" x:FullRows=\"1\">");
		sb.append("\n");
		for (int i = 0; i < rowTotal; i++) {
			if ((currentRecord == recordcount|| currentRecord > recordcount || currentRecord == 0)&& i != 0) {// 一个sheet写满
				currentRecord = 0;
				//rafs.write(sb.toString().getBytes());
				bw.write(sb.toString());
				sb.setLength(0);
				sb.append("</Table>");
				sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
				sb.append("\n");
				sb.append("<ProtectObjects>False</ProtectObjects>");
				sb.append("\n");
				sb.append("<ProtectScenarios>False</ProtectScenarios>");
				sb.append("\n");
				sb.append("</WorksheetOptions>");
				sb.append("\n");
				sb.append("</Worksheet>");
				sb.append("<Worksheet ss:Name=\""+this.fileName + i / recordcount
						+ "\">");
				sb.append("\n");
				sb.append("<Table ss:ExpandedColumnCount=\"" + colTotal
						+ "\" ss:ExpandedRowCount=\"" + recordcount
						+ "\" x:FullColumns=\"1\" x:FullRows=\"1\">");
				sb.append("\n");
			}
			sb.append("<Row>");
			for (int j = 0; j < colTotal; j++) {
				if(i==0&&j==0){
					int col = colTotal-1;
				    sb.append("<Cell ss:MergeAcross=\""+col+"\" ss:StyleID=\"title\"><Data ss:Type=\"String\">"+this.book.getText(0, 0)+"</Data><NamedCell ss:Name=\"Print_Area\"/></Cell>");
					break;
				}else{
				String valueFormat =  book.getActiveCellEx().getCellFormat(i,j).getValueFormat();
				sb.append("<Cell>");
				if(valueFormat.indexOf("#")>-1){//数字
					sb.append("<Data ss:Type=\"Number\">"+this.book.getText(i,j)+"</Data>");
				}else{ //设置此单元格中存入的是字符串
            	    sb.append("<Data ss:Type=\"String\">"+this.book.getText(i,j)+"</Data>");
				}
				sb.append("</Cell>");
				sb.append("\n");
				}
			}
			sb.append("</Row>");
			if (i % 50000 == 0) {
				//rafs.write(sb.toString().getBytes());
				//rafs.flush();
				bw.write(sb.toString());
				bw.flush();
				sb.setLength(0);
			}
			sb.append("\n");
			currentRecord++;
		}
		//rafs.write(sb.toString().getBytes());
		bw.write(sb.toString());
		sb.setLength(0);
		sb.append("</Table>");
		sb.append("<WorksheetOptions xmlns=\"urn:schemas-microsoft-com:office:excel\">");
		sb.append("\n");
		sb.append("<ProtectObjects>False</ProtectObjects>");
		sb.append("\n");
		sb.append("<ProtectScenarios>False</ProtectScenarios>");
		sb.append("\n");
		sb.append("</WorksheetOptions>");
		sb.append("\n");
		sb.append("</Worksheet>");
		sb.append("</Workbook>");
		sb.append("\n");
		//rafs.write(sb.toString().getBytes());
		//rafs.flush();
		bw.write(sb.toString());
		bw.flush();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}finally{
		//if(rafs!=null)rafs.close();
		if(bw!=null){ bw.close();}
	}
    }
}
