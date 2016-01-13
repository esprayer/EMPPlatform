package com.core.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

public class ProxyClassLoader extends ClassLoader
{
  private static final boolean _$612 = Boolean.getBoolean("org.netbeans.do_not_warn_default_package");

  private static final Enumeration _$613 = new ArrayEnumeration(new Object[0]);
  private final Map _$615;
  private final Map _$616;
  private ClassLoader[] _$500;
  private boolean _$617;
  private final boolean _$618;

  public ProxyClassLoader(ClassLoader[] parents)
  {
    this(parents, true);
  }
  
  public ProxyClassLoader(ClassLoader[] parents, URL url)
  {
    this(parents, true);
  }

  public ProxyClassLoader(ClassLoader[] parents, File file)
  {
    this(parents, true);
  }
  
  public ProxyClassLoader(ClassLoader[] parents, boolean transitive)
  {
    this._$615 = new HashMap();

    this._$616 = new HashMap();

    this._$617 = false;

    if (parents.length == 0) {
      throw new IllegalArgumentException("ProxyClassLoader must have a parent");
    }

    this._$618 = transitive;

    Set check = new HashSet(Arrays.asList(parents));
    if (check.size() < parents.length) throw new IllegalArgumentException("duplicate parents");
    if (check.contains(null)) throw new IllegalArgumentException("null parent in " + check);

    this._$500 = _$621(parents);
  }

  public synchronized void append(ClassLoader[] nueparents)
    throws IllegalArgumentException
  {
    if (nueparents == null) throw new IllegalArgumentException("null parents array");
    for (int i = 0; i < nueparents.length; ++i) {
      if (nueparents[i] != null) continue; throw new IllegalArgumentException("null parent");
    }

    this._$500 = _$623(this._$500, nueparents);
  }

  public void destroy()
  {
    this._$617 = true;
  }

  private void _$625(String hint)
  {
  }

  protected final synchronized Class loadClass(String name, boolean resolve)
    throws ClassNotFoundException
  {
    _$625(name);
    String filename = name.replace('.', '/').concat(".class");
    int idx = filename.lastIndexOf(47);
    if (idx == -1) {
      throw new ClassNotFoundException("Will not load classes from default package (" + name + ")");
    }
    String pkg = filename.substring(0, idx + 1);
    Class c = _$639(name, filename, pkg);
    if (c == null) {
      throw new ClassNotFoundException(name);
    }
    if (resolve) resolveClass(c);
    return c;
  }

  protected Class simpleFindClass(String name, String fileName, String pkg)
  {
    return null;
  }

  public final URL getResource(String name)
  {
    if (name.indexOf("Separator") > -1)
      System.out.println(name);
    _$625(name);

    int slashIdx = name.lastIndexOf(47);
    if (slashIdx == -1);
    String pkg = name.substring(0, slashIdx + 1);

    if (isSpecialResource(pkg))
    {
      for (int i = 0; i < this._$500.length; ++i)
      {
        URL u;
        if (!(shouldDelegateResource(pkg, this._$500[i])))
          continue;
        if (this._$500[i] instanceof ProxyClassLoader)
          u = ((ProxyClassLoader)this._$500[i]).findResource(name);
        else {
          u = this._$500[i].getResource(name);
        }
        if (u != null) return u;
      }
      return findResource(name);
    }

    ClassLoader owner = null;

    if (owner != null)
    {
      if (owner instanceof ProxyClassLoader) {
        return ((ProxyClassLoader)owner).findResource(name);
      }
      return owner.getResource(name);
    }

    URL retVal = null;
    for (int i = 0; i < this._$500.length; ++i) {
      owner = this._$500[i];
      if (shouldDelegateResource(pkg, owner)) {
        if (owner instanceof ProxyClassLoader)
          retVal = ((ProxyClassLoader)owner).findResource(name);
        else {
          retVal = owner.getResource(name);
        }
        if (retVal != null) {
          this._$615.put(new String(pkg).intern(), owner);
          return retVal;
        }
      }
    }

    retVal = findResource(name);
    if (retVal != null) {
      this._$615.put(new String(pkg).intern(), this);
    }
    return retVal;
  }

  protected URL findResource(String name)
  {
    return null;
  }

  protected final synchronized Enumeration findResources(String name)
    throws IOException
  {
    _$625(name);
    int slashIdx = name.lastIndexOf(47);
    if (slashIdx == -1);
    String pkg = name.substring(0, slashIdx + 1);

    Enumeration[] es = new Enumeration[this._$500.length + 1];
    for (int i = 0; i < this._$500.length; ++i) {
      if (!(shouldDelegateResource(pkg, this._$500[i]))) {
        es[i] = _$613;
      }
      else if (this._$500[i] instanceof ProxyClassLoader)
        es[i] = ((ProxyClassLoader)this._$500[i]).simpleFindResources(name);
      else {
        es[i] = this._$500[i].getResources(name);
      }
    }
    es[this._$500.length] = simpleFindResources(name);

    return new AAEnum(es);
  }

  protected Enumeration simpleFindResources(String name)
    throws IOException
  {
    return super.findResources(name);
  }

  protected Package getPackage(String name)
  {
    _$625(name);
    return getPackageFast(name, name.replace('.', '/') + '/', true);
  }

  protected Package getPackageFast(String name, String sname, boolean recurse)
  {
    synchronized (this._$616) {
      Package pkg = (Package)this._$616.get(name);
      if (pkg != null) {
        return pkg;
      }
      if (!(recurse)) {
        return null;
      }
      for (int i = 0; i < this._$500.length; ++i) {
        ClassLoader par = this._$500[i];
        if ((par instanceof ProxyClassLoader) && (shouldDelegateResource(sname, par))) {
          pkg = ((ProxyClassLoader)par).getPackageFast(name, sname, false);
          if (pkg != null) {
            break;
          }
        }
      }
      if ((pkg == null) && (shouldDelegateResource(sname, getParent())))
      {
        pkg = super.getPackage(name);
      }
      if (pkg != null) {
        this._$616.put(name, pkg);
      }
      return pkg;
    }
  }

  protected Package definePackage(String name, String specTitle, String specVersion, String specVendor, String implTitle, String implVersion, String implVendor, URL sealBase)
    throws IllegalArgumentException
  {
    synchronized (this._$616) {
      Package pkg = null;
      try {
        pkg = super.definePackage(name, specTitle, specVersion, specVendor, implTitle, implVersion, implVendor, sealBase);

        this._$616.put(name, pkg);
      } catch (Exception e) {
        e.printStackTrace();
      }
      return pkg;
    }
  }

  protected synchronized Package[] getPackages()
  {
    return _$669(new HashSet());
  }

  private synchronized Package[] _$669(Set addedParents)
  {
    _$625(null);
    Map all = new HashMap();

    _$672(all, super.getPackages());
    for (int i = 0; i < this._$500.length; ++i) {
      ClassLoader par = this._$500[i];
      if ((!(par instanceof ProxyClassLoader)) || (!(addedParents.add(par))))
        continue;
      _$672(all, ((ProxyClassLoader)par)._$669(addedParents));
    }

    synchronized (this._$616) {
      all.keySet().removeAll(this._$616.keySet());
      this._$616.putAll(all);
    }
    return ((Package[])(Package[])this._$616.values().toArray(new Package[this._$616.size()]));
  }

  public Package getPackageAccessibly(String name) {
    return getPackage(name);
  }

  public Package[] getPackagesAccessibly() {
    return getPackages();
  }

  private static void _$679(String name)
  {
    if (!(_$612)) {
      System.err.println("You are trying to access file: " + name + " from the default package.");
      System.err.println("Please see http://www.netbeans.org/download/dev/javadoc/OpenAPIs/org/openide/doc-files/classpath.html#default_package");
    }
  }

  private ClassLoader[] _$621(ClassLoader[] loaders)
    throws IllegalArgumentException
  {
    int likelySize = loaders.length * 5 + 10;
    Set resultingUnique = new HashSet(likelySize);
    List resulting = new ArrayList(likelySize);
    for (int i = 0; i < loaders.length; ++i) {
      _$684(resultingUnique, resulting, loaders[i]);
    }
    ClassLoader[] ret = (ClassLoader[])(ClassLoader[])resulting.toArray(new ClassLoader[resulting.size()]);
    return ret;
  }

  private ClassLoader[] _$623(ClassLoader[] existing, ClassLoader[] appended)
    throws IllegalArgumentException
  {
    int likelySize = existing.length + 3;
    Set resultingUnique = new HashSet(likelySize);
    List existingL = Arrays.asList(existing);
    resultingUnique.addAll(existingL);
    if (resultingUnique.containsAll(Arrays.asList(appended)))
    {
      return existing;
    }
    List resulting = new ArrayList(likelySize);
    resulting.addAll(existingL);
    for (int i = 0; i < appended.length; ++i) {
      _$684(resultingUnique, resulting, appended[i]);
    }
    ClassLoader[] ret = (ClassLoader[])(ClassLoader[])resulting.toArray(new ClassLoader[resulting.size()]);
    return ret;
  }

  private void _$684(Set resultingUnique, List resulting, ClassLoader loader) throws IllegalArgumentException {
    if (loader == this) throw new IllegalArgumentException("cycle in parents");
    if (resultingUnique.contains(loader)) return;
    if ((loader instanceof ProxyClassLoader) && (((ProxyClassLoader)loader)._$618)) {
      ClassLoader[] parents = ((ProxyClassLoader)loader)._$500;
      for (int i = 0; i < parents.length; ++i) {
        _$684(resultingUnique, resulting, parents[i]);
      }
    }
    resultingUnique.add(loader);
    resulting.add(loader);
  }

  private final Class _$639(String name, String fileName, String pkg)
    throws ClassNotFoundException
  {
    Class c = findLoadedClass(name);
    if (c != null) return c;

    ClassLoader owner = (isSpecialResource(pkg)) ? null : (ClassLoader)this._$615.get(pkg);
    if (owner == this) {
      return simpleFindClass(name, fileName, pkg);
    }
    if (owner != null)
    {
      if (owner instanceof ProxyClassLoader) {
        return ((ProxyClassLoader)owner)._$692(name, fileName, pkg);
      }
      return owner.loadClass(name);
    }

    c = _$693(name, fileName, pkg);

    if (c != null) {
      ClassLoader owner2 = _$695(c);
      this._$615.put(new String(pkg).intern(), owner2);
    }
    return c;
  }

  private static ClassLoader _$695(Class c)
  {
	  return c.getClassLoader();
//    return ((ClassLoader)AccessController.doPrivileged(new PrivilegedAction(c) {
//      public Object run() {
//        return c.getClassLoader();
//      }
//    }));
  }

  private final Class _$693(String name, String fileName, String pkg) throws ClassNotFoundException {
    ClassNotFoundException cached = null;
    for (int i = 0; i < this._$500.length; ) {
      ClassLoader par = this._$500[i];
      if (shouldDelegateResource(pkg, par))
        if (par instanceof ProxyClassLoader) {
          ProxyClassLoader pcl = (ProxyClassLoader)par;
          Class c = pcl._$692(name, fileName, pkg);

          if ((c != null) && (((pcl._$618) || (_$695(c) == pcl)))) return c;
        }
        else {
          boolean skip = false;
          if ((((name.startsWith("org.netbeans.")) || (name.startsWith("org.openide.")) || (name.endsWith(".Bundle")) || (name.endsWith("BeanInfo")) || (name.endsWith("Editor")))) && 
            (par.getResource(fileName) == null))
          {
            skip = true; }

          if (skip);
        }
      try {
        return par.loadClass(name);
      } catch (ClassNotFoundException cnfe) {
        cached = cnfe;

        ++i;
      }

    }

    Class c = simpleFindClass(name, fileName, pkg);
    if (c != null) return c;
    if (cached != null) throw cached;
    return null;
  }

  private synchronized Class _$692(String name, String fileName, String pkg) {
    Class c = findLoadedClass(name);
    return ((c == null) ? simpleFindClass(name, fileName, pkg) : c);
  }

  private void _$672(Map all, Package[] pkgs)
  {
    for (int i = 0; i < pkgs.length; ++i)
      all.put(pkgs[i].getName(), pkgs[i]);
  }

  protected boolean isSpecialResource(String pkg)
  {
    if (pkg.startsWith("META-INF/")) return true;

    return (pkg.length() != 0);
  }

  protected boolean shouldDelegateResource(String pkg, ClassLoader parent)
  {
    return true;
  }

  private static final class AAEnum
    implements Enumeration
  {
    private Enumeration[] _$702;
    private int _$552 = 0;

    public AAEnum(Enumeration[] array)
    {
      this._$702 = array;
    }

    public boolean hasMoreElements()
    {
      while (true)
      {
        if (this._$552 == this._$702.length) {
          return false;
        }

        if (this._$702[this._$552].hasMoreElements()) {
          return true;
        }

        this._$552 += 1;
      }
    }

    public Object nextElement()
    {
      try
      {
        return this._$702[this._$552].nextElement();
      } catch (NoSuchElementException ex) {
        if (hasMoreElements())
        {
          return nextElement();
        }
        throw ex;
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new NoSuchElementException();
      }
    }
  }

  private static final class ArrayEnumeration
    implements Enumeration
  {
    private Object[] _$702;
    private int _$552 = 0;

    public ArrayEnumeration(Object[] array)
    {
      this._$702 = array;
    }

    public boolean hasMoreElements()
    {
      return (this._$552 < this._$702.length);
    }

    public Object nextElement()
    {
      try
      {
        return this._$702[(this._$552++)];
      } catch (ArrayIndexOutOfBoundsException e) {
        throw new NoSuchElementException();
      }
    }
  }
}