package com.core.core;

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.security.AllPermission;
import java.security.CodeSource;
import java.security.PermissionCollection;
import java.security.Permissions;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Set;
import java.util.Vector;
import java.util.jar.JarFile;

public class BootClassLoader extends JarClassLoader
{
  protected static PermissionCollection modulePermissions;

  public BootClassLoader(List cp, ClassLoader[] parents)
  {
    super(cp, parents);
  }

  protected PermissionCollection getPermissions(CodeSource cs) {
    return _$503();
  }

  private static synchronized PermissionCollection _$503()
  {
    if (modulePermissions == null) {
      modulePermissions = new Permissions();
      modulePermissions.add(new AllPermission());
      modulePermissions.setReadOnly();
    }
    return modulePermissions;
  }

  public static BootClassLoader initClassLoader(Vector pathList, boolean isClient)
    throws Exception
  {
    BootClassLoader loader = null;

    new URLConnection(BootClassLoader.class.getResource("BootClassLoader.class")) { public void connect() throws IOException {  } }
    .setDefaultUseCaches(false);

    ArrayList Filelist = new ArrayList();
    HashSet processedDirs = new HashSet();

    for (int i = 0; i < pathList.size(); ++i) {
      String path = (String)pathList.get(i);
      build_cp(new File(path), Filelist, isClient, processedDirs);
    }

    ListIterator it2 = Filelist.listIterator();
    while (it2.hasNext()) {
      File f = (File)it2.next();
      if (f.isFile()) {
        try {
          it2.set(new JarFile(f, false));
        } catch (Exception e) {
          System.out.println(f.toURL().toString());
          e.printStackTrace();
        }
      }

    }

    loader = new BootClassLoader(Filelist, new ClassLoader[] { BootClassLoader.class.getClassLoader() });

    return loader;
  }

  private static void _$528(File dir, Collection toAdd)
  {
    if (!(dir.isDirectory())) return;
    File[] arr = dir.listFiles();
    for (int i = 0; i < arr.length; ++i)
      if (arr[i].isDirectory()) {
        _$528(arr[i], toAdd);
      }
      else {
        String n = arr[i].getName();

        if ((n.endsWith("jar")) || (n.endsWith("zip")) || (n.endsWith("eam")))
          toAdd.add(arr[i]);
      }
  }

  public static void build_cp(File base, Collection toAdd, boolean isClient, Set processedDirs)
    throws IOException
  {
    base = base.getCanonicalFile();
    if (!(processedDirs.add(base)))
    {
      return;
    }

    if (isClient) {
      _$528(new File(base, "client"), toAdd);

      _$528(new File(base, "public"), toAdd);

      _$528(new File(base, "server"), toAdd);
    } else {
      _$528(base, toAdd);
    }
  }
}