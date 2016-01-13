package com.core.net;

import java.io.IOException;
import java.net.URL;

public abstract interface HttpRequest
{
  public static final String JNLP_MIME_TYPE = "application/x-java-jnlp-file";
  public static final String ERROR_MIME_TYPE = "application/x-java-jnlp-error";
  public static final String JAR_MIME_TYPE = "application/x-java-archive";
  public static final String PACK200_MIME_TYPE = "application/x-java-pack200";
  public static final String JARDIFF_MIME_TYPE = "application/x-java-archive-diff";
  public static final String GIF_MIME_TYPE = "image/gif";
  public static final String JPEG_MIME_TYPE = "image/jpeg";
  public static final String GZIP_ENCODING = "gzip";
  public static final String PACK200_GZIP_ENCODING = "pack200-gzip";
  public static final String CONTENT_ENCODING = "content-encoding";
  public static final String ACCEPT_ENCODING = "accept-encoding";
  public static final String CONTENT_TYPE = "content-type";

  public abstract HttpResponse doHeadRequest(URL paramURL)
    throws IOException;

  public abstract HttpResponse doGetRequest(URL paramURL)
    throws IOException;

  public abstract HttpResponse doHeadRequest(URL paramURL, boolean paramBoolean)
    throws IOException;

  public abstract HttpResponse doGetRequest(URL paramURL, boolean paramBoolean)
    throws IOException;

  public abstract HttpResponse doHeadRequest(URL paramURL, String[] paramArrayOfString1, String[] paramArrayOfString2)
    throws IOException;

  public abstract HttpResponse doGetRequest(URL paramURL, String[] paramArrayOfString1, String[] paramArrayOfString2)
    throws IOException;

  public abstract HttpResponse doHeadRequest(URL paramURL, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean)
    throws IOException;

  public abstract HttpResponse doGetRequest(URL paramURL, String[] paramArrayOfString1, String[] paramArrayOfString2, boolean paramBoolean)
    throws IOException;

  public abstract URL getURL();
}