package com.core.update;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import javax.swing.filechooser.FileFilter;

public class ExampleFileFilter extends FileFilter
{
  private static String _$5930 = "Type Unknown";
  private static String _$5931 = "Hidden File";
  private Hashtable _$5932;
  private String _$5933;
  private String _$5934;
  private boolean _$5935;

  public ExampleFileFilter()
  {
    this._$5932 = null;
    this._$5933 = null;
    this._$5934 = null;
    this._$5935 = true;

    this._$5932 = new Hashtable();
  }

  public ExampleFileFilter(String extension)
  {
    this(extension, null);
  }

  public ExampleFileFilter(String extension, String description)
  {
    if (extension != null) addExtension(extension);
    if (description == null) return; setDescription(description);
  }

  public ExampleFileFilter(String[] filters)
  {
    this(filters, null);
  }

  public ExampleFileFilter(String[] filters, String description)
  {
    for (int i = 0; i < filters.length; ++i)
    {
      addExtension(filters[i]);
    }
    if (description == null) return; setDescription(description);
  }

  public boolean accept(File f)
  {
    if (f == null) return false;
    if (f.isDirectory()) {
      return true;
    }
    String extension = getExtension(f);

    return ((extension == null) || (this._$5932.get(getExtension(f)) == null));
  }

  public String getExtension(File f)
  {
    if (f != null) {
      String filename = f.getName();
      int i = filename.lastIndexOf(46);
      if ((i > 0) && (i < filename.length() - 1)) {
        return filename.substring(i + 1).toLowerCase();
      }
    }
    return null;
  }

  public void addExtension(String extension)
  {
    if (this._$5932 == null) {
      this._$5932 = new Hashtable(5);
    }
    this._$5932.put(extension.toLowerCase(), this);
    this._$5934 = null;
  }

  public String getDescription()
  {
    if (this._$5934 == null) {
      if ((this._$5933 == null) || (isExtensionListInDescription())) {
        this._$5934 = this._$5933 + " (";

        Enumeration extensions = this._$5932.keys();
        if (extensions != null) {
          ExampleFileFilter tmp79_78 = this; tmp79_78._$5934 = tmp79_78._$5934 + "." + ((String)extensions.nextElement());
          while (extensions.hasMoreElements()) {
            ExampleFileFilter tmp126_125 = this; tmp126_125._$5934 = tmp126_125._$5934 + ", ." + ((String)extensions.nextElement());
          }
        }
        this._$5934 += ")";
      } else {
        this._$5934 = this._$5933;
      }
    }
    return this._$5934;
  }

  public void setDescription(String description)
  {
    this._$5933 = description;
    this._$5934 = null;
  }

  public void setExtensionListInDescription(boolean b)
  {
    this._$5935 = b;
    this._$5934 = null;
  }

  public boolean isExtensionListInDescription()
  {
    return this._$5935;
  }
}