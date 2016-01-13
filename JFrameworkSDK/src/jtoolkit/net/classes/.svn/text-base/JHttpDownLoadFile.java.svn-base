package jtoolkit.net.classes;
import java.io.*;
import java.net.*;
import java.util.zip.*;
/**
 * <p>Title: Java Framework</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2002</p>
 * <p>Company: </p>
 * @author unascribed
 * @version 1.0
 */
//--------------------------------------------------------------------------------------------------
//描述:
//设计: Skyline(2001.12.29)
//实现: Skyline
//修改:
//--------------------------------------------------------------------------------------------------
  public class JHttpDownLoadFile {
      //protected String URI;
      //----------------------------------------------------------------------------------------------
      //描述: 构造函数
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //----------------------------------------------------------------------------------------------
      public JHttpDownLoadFile() {
      }
      //----------------------------------------------------------------------------------------------
      //描述: 构造函数
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //----------------------------------------------------------------------------------------------
      public JHttpDownLoadFile(String uri) {
          //URI = uri;
      }
      //----------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //----------------------------------------------------------------------------------------------
      static public byte[] getFileData(String URI) {

        byte[] Data=null;int FileLength;int nRead=0;
          //FileLength = getFileSize();
          //if ( FileLength == 0 ) return null;
          //Data = new byte[FileLength];
              try {
                  URL url = new URL(URI);/*
                  InputStream input = url.openStream();
                  FileLength = input.available();
                  Data = new byte[FileLength];
                  input.read(Data);*/
                  HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection ();
                  httpConnection.connect();
                  //FileLength   = httpConnection.getContentLength();
                  InputStream input = httpConnection.getInputStream();
                  FileLength = input.available();
                  Data = new byte[FileLength];
                  nRead = input.read(Data);
                  if ( Data != null ) return Data;
                  try {
                    ZipInputStream zipInput = new ZipInputStream(input);
                    ZipEntry zipEntry = zipInput.getNextEntry();
                    if ( zipEntry != null ) {
                      // 读压缩数据
                      FileLength = (int)zipEntry.getSize();//zipInput.available();
                      Data = new byte[FileLength];
                      nRead = 0;
                      while ( nRead < FileLength ) {
                        nRead += zipInput.read(Data,nRead,FileLength-nRead);
                        //zipInput.skip(nRead);
                      }
                    } else {
                      FileLength = input.available();
                      Data = new byte[FileLength];
                      nRead = input.read(Data);
                    }
                    //nRead = Data.length;
                    //nRead = zipInput.read(Data);
                  } catch(Exception e) {
                    FileLength = 0;Data = null;
                    e.printStackTrace();
                  }
//                  FileLength = httpConnection.getContentLength();
//                  Data = new byte[FileLength];
//                  nRead=input.read(Data,0,FileLength);
//                  System.out.println("解压后的大小:"+String.valueOf(nRead)+"/"+String.valueOf(FileLength));
                  if ( nRead != FileLength ) {
                    Data = null;
                  }
              } catch ( Exception e ){
                  FileLength = 0;Data = null;
                  e.printStackTrace ();
              }
              return Data;

        //byte[] Data=null;int FileLength;int nRead;
          ////FileLength = getFileSize();
          ////if ( FileLength == 0 ) return null;
          ////Data = new byte[FileLength];
              //try {
                  //URL url = new URL(URI);/*
                  //InputStream input = url.openStream();
                  //FileLength = input.available();
                  //Data = new byte[FileLength];
                  //input.read(Data);*/
                  //HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection ();
                  //httpConnection.connect();
                  ////FileLength   = httpConnection.getContentLength();
                  //InputStream input = httpConnection.getInputStream();
                  //FileLength = input.available();
                  //Data = new byte[FileLength];
                  //nRead = input.read(Data);
                  ///*
                  //FileLength = httpConnection.getContentLength();
                  //Data = new byte[FileLength];
                  //nRead=input.read(Data,0,FileLength);*/
                  //if ( nRead != FileLength ) Data = null;
              //} catch ( Exception e ){
                  ////e.printStackTrace ();
              //}
          //return Data;
      }
      //----------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //----------------------------------------------------------------------------------------------
      static public int getFileSize(String URI) {
        int nFileLength = 0;
          try {
              URL url = new URL(URI);
              HttpURLConnection httpConnection = (HttpURLConnection)url.openConnection();
              httpConnection.setRequestProperty("User-Agent","JFramework");
              int responseCode=httpConnection.getResponseCode();
              if(responseCode>=400) {
                  processErrorCode(responseCode);
                  return 0;
              }
            String sHeader;
              for(int i=1;;i++) {
                  sHeader=httpConnection.getHeaderFieldKey(i);
              }
          } catch ( IOException e ) {
              e.printStackTrace ();
          } catch ( Exception e ) {
              e.printStackTrace ();
          }
          //System.out.println("文件长度:"+nFileLength);
          return nFileLength;
      }
      //----------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //----------------------------------------------------------------------------------------------
      static private void processErrorCode(int nErrorCode) {
          System.err.println("Error Code : " + nErrorCode);
      }
      //----------------------------------------------------------------------------------------------
      //描述:
      //设计: Skyline(2001.12.29)
      //实现: Skyline
      //修改:
      //----------------------------------------------------------------------------------------------
      static public void SetURI(String uri) {
          //URI = uri;
      }
  }
