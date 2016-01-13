package com.core.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Policy;
import java.security.ProtectionDomain;
import java.security.cert.Certificate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;
import java.util.jar.Attributes;
import java.util.jar.Attributes.Name;
import java.util.jar.JarFile;
import java.util.jar.Manifest;
import java.util.zip.ZipEntry;

public class JarClassLoader extends ProxyClassLoader
{
  private Source[] _$720;
  private Set _$721;
  private static final boolean _$722 = Boolean.getBoolean("netbeans.classloader.verbose");

  public JarClassLoader(List files, ClassLoader[] parents)
  {
    this(files, parents, true);
  }

  public JarClassLoader(List files, ClassLoader[] parents, boolean transitive)
  {
    super(parents, transitive);

    this._$721 = null;

    this._$720 = new Source[files.size()];
    try {
      int i = 0;
      for (Iterator it = files.iterator(); it.hasNext(); ++i) {
        Object act = it.next();
        if (act instanceof File)
          this._$720[i] = new DirSource((File)act);
        else
          this._$720[i] = new JarSource((JarFile)act);
      }
    }
    catch (MalformedURLException exc) {
      throw new IllegalArgumentException(exc.getMessage());
    }
  }

  final void addSources(List newSources)
  {
    ArrayList l = new ArrayList(this._$720.length + newSources.size());
    l.addAll(Arrays.asList(this._$720));
    try {
      int i = 0;
      for (Iterator it = newSources.iterator(); it.hasNext(); ++i) {
        Object act = it.next();
        if (act instanceof File)
          l.add(new DirSource((File)act));
        else
          l.add(new JarSource((JarFile)act));
      }
    }
    catch (MalformedURLException exc) {
      throw new IllegalArgumentException(exc.getMessage());
    }
    this._$720 = ((Source[])(Source[])l.toArray(this._$720));
  }

  protected PermissionCollection getPermissions(CodeSource cs)
  {
    return Policy.getPolicy().getPermissions(cs);
  }

  protected Package definePackage(String name, Manifest man, URL url)
    throws IllegalArgumentException
  {
    if (man == null) {
      return definePackage(name, null, null, null, null, null, null, null);
    }

    String path = name.replace('.', '/').concat("/");
    Attributes spec = man.getAttributes(path);
    Attributes main = man.getMainAttributes();

    String specTitle = _$738(spec, main, Attributes.Name.SPECIFICATION_TITLE);
    String implTitle = _$738(spec, main, Attributes.Name.IMPLEMENTATION_TITLE);
    String specVersion = _$738(spec, main, Attributes.Name.SPECIFICATION_VERSION);
    String implVersion = _$738(spec, main, Attributes.Name.IMPLEMENTATION_VERSION);
    String specVendor = _$738(spec, main, Attributes.Name.SPECIFICATION_VENDOR);
    String implVendor = _$738(spec, main, Attributes.Name.IMPLEMENTATION_VENDOR);
    String sealed = _$738(spec, main, Attributes.Name.SEALED);

    URL sealBase = ("true".equalsIgnoreCase(sealed)) ? url : null;
    return definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);
  }

  private static String _$738(Attributes spec, Attributes main, Attributes.Name name)
  {
    String val = null;
    if (spec != null) val = spec.getValue(name);
    if ((val == null) && (main != null)) val = main.getValue(name);
    return val;
  }

  protected Class simpleFindClass(String name, String path, String pkgnameSlashes)
  {
    for (int i = 0; i < this._$720.length; ++i) {
      Source src = this._$720[i];
      byte[] data = src.getClassData(name, path);
      if (data == null) {
        continue;
      }
      byte[] d = PatchByteCode.patch(data, name);
      data = d;

      int j = name.lastIndexOf(46);
      String pkgName = name.substring(0, j);

      Package pkg = getPackageFast(pkgName, pkgnameSlashes, isSpecialResource(pkgnameSlashes));
      if (pkg != null)
      {
        if ((!(pkg.isSealed())) || (pkg.isSealed(src.getURL()))) {
        	return defineClass(name, data, 0, data.length, src.getProtectionDomain());
        }
      }
      Manifest man = src.getManifest();
      definePackage(pkgName, man, src.getURL());
    }
    return null;
  }

  protected URL findResource(String name) {
    for (int i = 0; i < this._$720.length; ++i) {
      URL item = this._$720[i].getResource(name);
      if (item != null) return item;
    }
    return null;
  }

  protected Enumeration simpleFindResources(String name) {
    Vector v = new Vector(3);

    for (int i = 0; i < this._$720.length; ++i) {
      URL item = this._$720[i].getResource(name);
      if (item == null) continue; v.add(item);
    }
    return v.elements();
  }

  public final void releaseLocks()
  {
    if (this._$721 != null) throw new IllegalStateException("Already had dead JARs: " + this._$721);
    this._$721 = new HashSet();
    try {
      for (int i = 0; i < this._$720.length; ++i)
        if (this._$720[i] instanceof JarSource) {
          String prefix;
          String suffix;
          JarFile origJar = ((JarSource)this._$720[i]).getJarFile();
          File orig = new File(origJar.getName());
          if (!(orig.isFile()))
          {
            continue;
          }

          String name = orig.getName();

          int idx = name.lastIndexOf(46);
          if (idx == -1) {
            prefix = name;
            suffix = null;
          } else {
            prefix = name.substring(0, idx);
            suffix = name.substring(idx);
          }
          for (; prefix.length() < 3; prefix = prefix + "x");
          File temp = File.createTempFile(prefix, suffix);
          temp.deleteOnExit();

          InputStream is = new FileInputStream(orig);
          try {
            OutputStream os = new FileOutputStream(temp);
            try {
              byte[] buf = new byte[4096];
              int j;
              while ((j = is.read(buf)) != -1)
              {
                
                os.write(buf, 0, j);
              }
            } finally {
              os.close();
            }
          } finally {
            is.close();
          }

          JarFile tempJar = new JarFile(temp);
          origJar.close();
          this._$721.add(tempJar);
          this._$720[i] = new JarSource(tempJar);
          log("#21114: replacing " + orig + " with " + temp);
        }
    }
    catch (IOException ioe) {
      notify(0, ioe);
    }
  }

  protected void finalize()
    throws Throwable
  {
    super.finalize();
    for (int i = 0; i < this._$720.length; ++i)
      if (this._$720[i] instanceof JarSource) {
        JarFile j = ((JarSource)this._$720[i]).getJarFile();
        File f = new File(j.getName());
        j.close();
        if ((this._$721 != null) && (this._$721.contains(j))) {
          log("#21114: closing and deleting temporary JAR " + f);
          if ((f.isFile()) && (!(f.delete())))
            log("(but failed to delete it)");
        }
      }
  }

  static void log(String msg)
  {
    if ("0".equals(System.getProperty("org.netbeans.core.modules")))
      System.err.println(msg);
  }

  static Throwable annotate(Throwable t, int x, String s, Object o1, Object o2, Object o3)
  {
    System.err.println("annotated: " + t.getMessage() + " - " + s);
    return t;
  }

  static void notify(int x, Throwable t) {
    t.printStackTrace();
  }

  class DirSource extends JarClassLoader.Source
  {
    File dir;

    public DirSource(File paramFile)
      throws MalformedURLException
    {
      super(paramFile.toURI().toURL());
      this.dir = paramFile;
    }

    protected URL doGetResource(String name) throws MalformedURLException {
      File resFile = new File(this.dir, name);
      return ((resFile.exists()) ? resFile.toURI().toURL() : null);
    }

    protected byte[] readClass(String name, String path) throws IOException {
      File clsFile = new File(this.dir, path.replace('/', File.separatorChar));
      if (!(clsFile.exists())) return null;

      int len = (int)clsFile.length();
      byte[] data = new byte[len];
      InputStream is = new FileInputStream(clsFile);
      int count = 0;
      while (count < len) {
        count += is.read(data, count, len - count);
      }
      return data;
    }
  }

  class JarSource extends JarClassLoader.Source
  {
    JarFile src;
    private String _$783;

    public JarSource(JarFile paramJarFile)
      throws MalformedURLException
    {
      super(new File(paramJarFile.getName()).toURI().toURL());
      this.src = paramJarFile;
      this._$783 = "jar:" + new File(this.src.getName()).toURI() + "!/";
    }

    public Manifest getManifest() {
      try {
        return this.src.getManifest(); } catch (IOException e) {
      }
      return null;
    }

    JarFile getJarFile()
    {
      return this.src;
    }

    protected URL doGetResource(String name) throws MalformedURLException {
      ZipEntry ze;
      try {
        ze = this.src.getEntry(name);
      }
      catch (IllegalStateException ex)
      {
        return null;
      }
      if ((JarClassLoader._$722) && 
        (ze != null)) {
        System.err.println("Loading " + name + " from " + this.src.getName());
      }
      return new URL(this._$783 + ze.getName());
    }

    protected byte[] readClass(String name, String path) throws IOException {
      ZipEntry ze;
      try {
        ze = this.src.getEntry(path);
      }
      catch (IllegalStateException ex)
      {
        return null;
      }
      if (ze == null) return null;
      if (JarClassLoader._$722) {
        System.err.println("Loading " + path + " from " + this.src.getName());
      }

      int len = (int)ze.getSize();
      byte[] data = new byte[len];
      InputStream is = this.src.getInputStream(ze);
      int count = 0;
      while (count < len) {
        count += is.read(data, count, len - count);
      }
      return data;
    }
  }

  abstract class Source
  {
    private URL _$372;
    private ProtectionDomain _$780;

    public Source(URL paramURL)
    {
      this._$372 = paramURL;
    }

    public final URL getURL() {
      return this._$372;
    }

    public final ProtectionDomain getProtectionDomain() {
      if (this._$780 == null) {
        CodeSource cs = new CodeSource(this._$372, new Certificate[0]);
        this._$780 = new ProtectionDomain(cs, JarClassLoader.this.getPermissions(cs));
      }
      return this._$780;
    }

    public final URL getResource(String name) {
      try {
        return doGetResource(name);
      }
      catch (Exception e) {
        JarClassLoader.log(e.toString());
      }
      return null;
    }

    protected abstract URL doGetResource(String paramString) throws MalformedURLException;

    public final byte[] getClassData(String name, String path) {
      try {
        return readClass(name, path);
      } catch (IOException e) {
        JarClassLoader.log(e.toString());
      }
      return null;
    }

    protected abstract byte[] readClass(String paramString1, String paramString2) throws IOException;

    public Manifest getManifest() {
      return null;
    }
  }
}