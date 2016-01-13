package com.core.update;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Date;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.JFrame;
import javax.swing.JProgressBar;

public class ZipSelfExtractor
{
  public static void main(String[] args)
  {
    JFrame frame = new JFrame();
    frame.setSize(400, 300);
    frame.setVisible(true);
  }

  public static void extract(JProgressBar jpb, File currentArchive, File outputDir)
  {
    byte[] buf = new byte[1024];

    ZipFile zf = null;
    FileOutputStream out = null;
    InputStream in = null;
    try {
      zf = new ZipFile(currentArchive);
      int size = zf.size();
      int extracted = 0;
      jpb.setMaximum(size - 4);

      Enumeration entries = zf.entries();
      for (int i = 0; i < size; ++i) {
        ZipEntry entry = (ZipEntry)entries.nextElement();
        if (entry.isDirectory())
          continue;
        String pathname = entry.getName();

        ++extracted;
        jpb.setValue(i);
        jpb.setString(pathname);
        in = zf.getInputStream(entry);
        File outFile = new File(outputDir, pathname);
        Date archiveTime = new Date(entry.getTime());

        File parent = new File(outFile.getParent());
        if ((parent != null) && (!(parent.exists()))) {
          parent.mkdirs();
        }
        out = new FileOutputStream(outFile);
        while (true) {
          int nRead = in.read(buf, 0, buf.length);
          if (nRead <= 0)
            break;
          out.write(buf, 0, nRead);
        }
        out.close();
        outFile.setLastModified(archiveTime.getTime());
      }
      zf.close();
    }
    catch (Exception e)
    {
      System.out.println(e);
      if (zf != null) {
        try {
          zf.close();
        }
        catch (IOException ioe)
        {
        }
      }
      if (out != null) {
        try {
          out.close();
        }
        catch (IOException ioe)
        {
        }
      }
      if (in == null) return;
      try {
        in.close();
      }
      catch (IOException ioe)
      {
      }
    }
  }
}