package com.core.net;

public class CanceledDownloadException extends Exception
{
  public CanceledDownloadException()
  {
    super("cancled download");
  }
}