package com.core.net;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class BasicDownloadLayer
  implements HttpDownload
{
  private static final int _$11923 = 32768;
  private HttpRequest _$11924;

  public BasicDownloadLayer()
  {
  }

  public BasicDownloadLayer(HttpRequest httprequest)
  {
    this._$11924 = httprequest;
  }

  public void download(HttpResponse httpresponse, File file, HttpDownloadListener dl)
    throws CanceledDownloadException, IOException
  {
    int i = httpresponse.getContentLength();
    if (dl != null) {
      dl.downloadProgress(0, i, httpresponse, this._$11924);
    }
    BufferedInputStream bufferedinputstream = httpresponse.getInputStream();
    BufferedOutputStream bufferedoutputstream = new BufferedOutputStream(new FileOutputStream(file));
    String s = httpresponse.getContentEncoding();
    try
    {
      boolean flag = false;
      int k = 0;
      int j;
      byte[] abyte0 = new byte[32768];

      while ((j = bufferedinputstream.read(abyte0)) != -1)
      {
        
        bufferedoutputstream.write(abyte0, 0, j);
        k += j;
        if ((k > i) && (i != 0))
          k = i;
        if (dl != null) {
          dl.downloadProgress(k, i, null, null);
        }
      }
      bufferedinputstream.close();
      bufferedinputstream = null;
      bufferedoutputstream.close();
      bufferedoutputstream = null;
    }
    catch (IOException ioexception)
    {
      if (bufferedinputstream != null)
      {
        bufferedinputstream.close();
        bufferedinputstream = null;
      }
      if (bufferedoutputstream != null)
      {
        bufferedoutputstream.close();
        bufferedoutputstream = null;
      }
      if (file != null)
        file.delete();
      throw ioexception;
    }
    if (dl != null)
      dl.downloadProgress(i, i, file, null);
  }

  public void download(URL url, File file, HttpDownloadListener httpdownloadlistener)
    throws CanceledDownloadException, IOException
  {
    HttpResponse httpresponse = this._$11924.doGetRequest(url);
    download(httpresponse, file, httpdownloadlistener);
    file.setLastModified(httpresponse.getLastModified());
    httpresponse.disconnect();
  }

  class PropertyChangeListenerTask
    implements PropertyChangeListener
  {
    HttpDownloadListener _dl;

    public void propertyChange(PropertyChangeEvent propertychangeevent)
    {
      if (propertychangeevent.getPropertyName().compareTo("unpack.progress") != 0)
        return;
      String s = (String)propertychangeevent.getNewValue();
      if ((this._dl != null) && (s != null))
        this._dl.downloadProgress(Integer.parseInt(s), 100, null, null);
    }

    PropertyChangeListenerTask(HttpDownloadListener paramHttpDownloadListener)
    {
      this._dl = null;
      this._dl = paramHttpDownloadListener;
    }
  }
}