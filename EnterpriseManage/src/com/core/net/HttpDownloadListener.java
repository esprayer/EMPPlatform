package com.core.net;

public abstract interface HttpDownloadListener
{
  public abstract boolean downloadProgress(int paramInt1, int paramInt2, Object paramObject1, Object paramObject2);
}