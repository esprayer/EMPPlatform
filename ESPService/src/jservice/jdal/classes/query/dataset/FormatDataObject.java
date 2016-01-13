package jservice.jdal.classes.query.dataset;

import java.util.zip.ZipOutputStream;
import java.util.zip.GZIPOutputStream;

public class FormatDataObject {
  private StringBuffer contentBuffer=null;
  private Object outObject=null;
  public FormatDataObject() {
    contentBuffer=new StringBuffer();
  }

  public String getFormatedDate(){
    close();
    return getFormatedBufferDate().toString();

  }
  protected StringBuffer getFormatedBufferDate(){
    return contentBuffer;
  }

  private void close(){
    try{
      GZIPOutputStream out=(GZIPOutputStream) getOutObject();
      if(out!=null){
        out.finish();
        out.close();
      }
    }
    catch(Exception ex){
      ex.printStackTrace() ;
    }
  }
  protected Object getOutObject(){
    return outObject;
  }

  protected void setOutObject(Object obj){
    this.outObject=obj;
  }
}
