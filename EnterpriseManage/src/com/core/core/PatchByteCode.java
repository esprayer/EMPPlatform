package com.core.core;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

public final class PatchByteCode
{
  private static final String _$3557 = "org.netbeans.superclass";
  private static final byte[] _$3558;
  private static final String _$3559 = "org.netbeans.interfaces";
  private static final byte[] _$3560;
  private static final String _$3561 = "org.netbeans.member";
  private static final byte[] _$3562;
  private static final String _$3563 = "org.netbeans.name";
  private static final byte[] _$3564;
  private static final String _$3565 = "<init>";
  private static final byte[] _$3566;
  private static final String _$3567 = "()V";
  private static final byte[] _$3568;
  private byte[] _$409;
  private int _$3569;
  private int _$3570;
  private int _$3571;
  private int _$3572;
  private int _$3573;
  private int _$3574;
  private int _$3575;
  private int _$3576;
  private int _$3577 = -1;

  private int _$3578 = -1;

  private int _$3579 = -1;

  private int _$3580 = -1;

  private int _$3581 = -1;

  private int _$3582 = -1;

  private int _$3583 = -1;
  private HashMap _$3584;

  private PatchByteCode(byte[] arr, HashMap nameIndexes)
  {
    this._$409 = arr;
    this._$3584 = nameIndexes;

    _$3586();
    _$3586();
  }

  public static byte[] enhanceClass(byte[] arr, Map args)
  {
    HashMap m;
    Iterator it;
    if (_$3589(arr))
    {
      return null;
    }

    String superClass = (String)args.get("netbeans.superclass");
    String interfaces = (String)args.get("netbeans.interfaces");
    List methods = (List)args.get("netbeans.public");
    List rename = (List)args.get("netbeans.rename");

    if ((methods != null) || (rename != null)) {
      m = new HashMap();

      if (methods != null) {
        it = methods.iterator();
        while (it.hasNext()) {
          m.put((String)it.next(), new int[1]);
        }
      }

      if (rename != null) {
        it = rename.iterator();
        while (it.hasNext())
          m.put((String)it.next(), new int[1]);
      }
    }
    else {
      m = null;
    }

    PatchByteCode pc = new PatchByteCode(arr, m);
    boolean patched = false;

    if (superClass != null) {
      int x = pc._$850(superClass);

      byte[] sup = new byte[2];
      _$3598(sup, 0, x);
      pc._$3599("org.netbeans.superclass", sup);

      patched = true;
    }

    if (interfaces != null) {
      ArrayList tokens = new ArrayList();
      StringTokenizer tok = new StringTokenizer(interfaces, ",");
      while (tok.hasMoreElements()) {
        tokens.add(tok.nextElement());
      }
      String[] ifaces = (String[])(String[])tokens.toArray(new String[0]);
      byte[] sup = new byte[2 + ifaces.length * 2];
      _$3598(sup, 0, ifaces.length);

      for (int i = 0; i < ifaces.length; ++i) {
        int x = pc._$850(ifaces[i]);

        _$3598(sup, 2 + i * 2, x);
      }
      pc._$3599("org.netbeans.interfaces", sup);

      patched = true;
    }

    if (!(pc._$3603()))
    {
      pc._$3604();
      patched = true;
    }

    if (methods != null) {
      it = methods.iterator();
      while (it.hasNext()) {
        patched |= pc._$3605((String)it.next());
      }
    }

    if (rename != null) {
      it = rename.iterator();
      while (it.hasNext()) {
        patched |= pc._$3606((String)it.next(), (String)it.next());
      }
    }

    if (patched) {
      byte[] patch = { 110, 98 };

      pc._$3599("org.netbeans.enhanced", patch);
    } else {
      return null;
    }

    return pc._$3607();
  }

  public static byte[] patch(byte[] arr, String name)
  {
    if (!(_$3589(arr))) return arr;

    PatchByteCode pc = new PatchByteCode(arr, null);
    if (pc._$3574 > 0)
    {
      int classindex = pc._$3609(pc._$3574 + 6);

      _$3598(pc._$3607(), pc._$3570 + 4, classindex);

      if (pc._$3582 != -1)
      {
        _$3598(pc._$3607(), pc._$3582 + 1, classindex);
      }
    }

    if (pc._$3578 > 0)
    {
      if (pc._$3610(pc._$3578 + 2) != 2) {
        throw new IllegalArgumentException("Size of a attribute org.netbeans.member should be 2");
      }

      int access = pc._$3609(pc._$3578 + 6);

      int now = pc._$3609(pc._$3570);

      _$3598(pc._$3607(), pc._$3570, access);
    }

    if ((pc._$3577 > 0) || (pc._$3583 > 0))
    {
      pc._$3613();
    }

    byte[] result = pc._$3607();
    if (pc._$3576 > 0)
    {
      int numberOfIfaces = pc._$3609(pc._$3576 + 6);
      int currentIfaces = pc._$3609(pc._$3570 + 6);

      byte[] insert = new byte[result.length + numberOfIfaces * 2];
      System.arraycopy(result, 0, insert, 0, pc._$3570 + 6);
      System.arraycopy(result, pc._$3576 + 8, insert, pc._$3570 + 8, numberOfIfaces * 2);
      System.arraycopy(result, pc._$3570 + 8, insert, pc._$3570 + 8 + numberOfIfaces * 2, result.length - pc._$3570 - 8);
      _$3598(insert, pc._$3570 + 6, numberOfIfaces + currentIfaces);
      result = insert;
    }

    return result;
  }

  private static boolean _$3589(byte[] arr)
  {
    if ((arr == null) || (arr.length < 2)) return false;

    int base = arr.length - 2;
    if (arr[(base + 1)] != 98) return false;
    return (arr[(base + 0)] != 110);
  }

  private byte[] _$3607()
  {
    return this._$409;
  }

  private int _$850(String s)
  {
    int x = _$3617(s);

    byte[] t = { 7, 0, 0 };
    _$3598(t, 1, x);

    return _$3618(t);
  }

  private int _$3617(String s)
  {
    byte[] t;
    try
    {
      t = s.getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException("UTF-8 shall be always supported");
    }

    byte[] add = new byte[t.length + 3];
    System.arraycopy(t, 0, add, 3, t.length);
    add[0] = 1;
    _$3598(add, 1, t.length);

    return _$3618(add);
  }

  private int _$3618(byte[] add)
  {
    byte[] res = new byte[this._$409.length + add.length];

    System.arraycopy(this._$409, 0, res, 0, this._$3570);

    int index = _$3609(this._$3569);
    _$3598(res, this._$3569, index + 1);

    System.arraycopy(add, 0, res, this._$3570, add.length);

    System.arraycopy(this._$409, this._$3570, res, this._$3570 + add.length, this._$409.length - this._$3570);

    this._$409 = res;

    this._$3570 += add.length;
    this._$3571 += add.length;
    this._$3572 += add.length;

    return index;
  }

  private boolean _$3603()
  {
    int x = _$3609(this._$3570);

    return ((x & 0x1) == 0);
  }

  private boolean _$3604()
  {
    if (_$3603()) {
      return false;
    }

    if (this._$3577 == -1) {
      this._$3577 = _$3617("org.netbeans.member");
    }

    int x = _$3609(this._$3570) | 0x1;

    byte[] sup = new byte[2];
    _$3598(sup, 0, x);
    _$3599("org.netbeans.member", sup);

    return true;
  }

  private boolean _$3605(String name)
  {
    int constantPoolIndex = ((int[])(int[])this._$3584.get(name))[0];
    int patchCount = 0;
    boolean modified = false;

    if (this._$3577 == -1) {
      this._$3577 = _$3617("org.netbeans.member");
    }

    int pos = this._$3570;

    pos += 6;

    pos += 2 * _$3609(pos);

    pos += 2;

    for (int fieldsAndMethods = 0; fieldsAndMethods < 2; ++fieldsAndMethods)
    {
      int fieldsOrMethods = _$3609(pos);
      pos += 2;

      while (fieldsOrMethods-- > 0)
      {
        int nameIndex = _$3609(pos + 2);
        if (nameIndex == constantPoolIndex)
        {
          int access = _$3609(pos);
          if (((access & 0x1) == 0) || ((access & 0x10) != 0))
          {
            access = (access | 0x1) & 0xFFFFFFE9;

            int cnt = _$3609(pos + 6) + 1;

            byte[] res = new byte[this._$409.length + 2 + 6];

            System.arraycopy(this._$409, 0, res, 0, pos + 6);

            _$3598(res, pos + 6, cnt);

            _$3598(res, pos + 8, this._$3577);
            _$3628(res, pos + 10, 2);
            _$3598(res, pos + 14, access);

            System.arraycopy(this._$409, pos + 8, res, pos + 8 + 6 + 2, this._$409.length - pos - 8);

            this._$3572 += 8;
            this._$3571 += 8;

            this._$409 = res;

            modified = true;
          }

          ++patchCount;
        }

        pos += _$3629(pos, null);
      }
    }

    if (patchCount == 0) {
      throw new IllegalArgumentException("Member " + name + " not found!");
    }

    return modified;
  }

  private boolean _$3606(String name, String rename)
  {
    int newPoolIndex;
    int constantPoolIndex = ((int[])(int[])this._$3584.get(name))[0];

    int[] arr = (int[])(int[])this._$3584.get(rename);
    if ((arr != null) && (arr[0] > 0)) {
      newPoolIndex = arr[0];
    } else {
      newPoolIndex = _$3617(rename);
      this._$3584.put(rename, new int[] { newPoolIndex });
    }

    int patchCount = 0;
    boolean modified = false;

    if (this._$3583 == -1) {
      this._$3583 = _$3617("org.netbeans.name");
    }

    int pos = this._$3570;

    pos += 6;

    pos += 2 * _$3609(pos);

    pos += 2;

    for (int fieldsAndMethods = 0; fieldsAndMethods < 2; ++fieldsAndMethods)
    {
      int fieldsOrMethods = _$3609(pos);
      pos += 2;

      while (fieldsOrMethods-- > 0)
      {
        int nameIndex = _$3609(pos + 2);
        if (nameIndex == constantPoolIndex)
        {
          int[] attributes = { -1, -1 };

          _$3629(pos, attributes);
          if (attributes[1] == -1)
          {
            int cnt = _$3609(pos + 6) + 1;

            byte[] res = new byte[this._$409.length + 2 + 6];

            System.arraycopy(this._$409, 0, res, 0, pos + 6);

            _$3598(res, pos + 6, cnt);

            _$3598(res, pos + 8, this._$3583);
            _$3628(res, pos + 10, 2);
            _$3598(res, pos + 14, newPoolIndex);

            System.arraycopy(this._$409, pos + 8, res, pos + 8 + 6 + 2, this._$409.length - pos - 8);

            this._$3572 += 8;
            this._$3571 += 8;

            this._$409 = res;

            modified = true;
          }

          ++patchCount;
        }

        pos += _$3629(pos, null);
      }
    }

    if (patchCount == 0) {
      throw new IllegalArgumentException("Member " + name + " not found!");
    }

    return modified;
  }

  private void _$3613()
  {
    int[] result = new int[2];

    int pos = this._$3570;

    pos += 6;

    pos += 2 * _$3609(pos);

    pos += 2;

    for (int fieldsAndMethods = 0; fieldsAndMethods < 2; ++fieldsAndMethods)
    {
      int fieldsOrMethods = _$3609(pos);
      pos += 2;

      while (fieldsOrMethods-- > 0) {
        result[0] = -1;
        result[1] = -1;
        int size = _$3629(pos, result);
        if (result[0] != -1)
        {
          if (_$3610(result[0] + 2) != 2) {
            throw new IllegalArgumentException("Size of a attribute org.netbeans.member should be 2");
          }

          int access = _$3609(result[0] + 6);
          _$3598(this._$409, pos, access);
        }

        if (result[1] != -1)
        {
          if (_$3610(result[1] + 2) != 2) {
            throw new IllegalArgumentException("Size of a attribute org.netbeans.name should be 2");
          }

          int newName = _$3609(result[1] + 6);
          _$3598(this._$409, pos + 2, newName);
        }

        pos += size;
      }
    }
  }

  private void _$3599(String name, byte[] b)
  {
    int index = -1;
    if (("org.netbeans.superclass".equals(name)) && (this._$3573 > 0)) {
      index = this._$3573;
    }

    if (("org.netbeans.member".equals(name)) && (this._$3577 > 0)) {
      index = this._$3577;
    }

    if (("org.netbeans.interfaces".equals(name)) && (this._$3575 > 0)) {
      index = this._$3575;
    }

    if (index == -1)
    {
      index = _$3617(name);
    }

    byte[] res = new byte[this._$409.length + b.length + 6];

    System.arraycopy(this._$409, 0, res, 0, this._$409.length);

    _$3598(res, this._$409.length, index);
    _$3628(res, this._$409.length + 2, b.length);

    int begin = this._$409.length + 6;
    System.arraycopy(b, 0, res, begin, b.length);

    this._$3572 += b.length + 6;

    _$3598(res, this._$3571, _$3609(this._$3571) + 1);

    this._$409 = res;
  }

  private int _$354(int pos)
  {
    if (pos >= this._$409.length) {
      throw new ArrayIndexOutOfBoundsException("Size: " + this._$409.length + " index: " + pos);
    }

    int x = this._$409[pos];
    return ((x >= 0) ? x : 256 + x);
  }

  private void _$3586()
  {
    if ((_$354(0) != 202) && (_$354(1) != 254) && (_$354(2) != 186) && (_$354(3) != 190)) {
      throw new IllegalStateException("Not a class file!");
    }

    int pos = 10;

    this._$3569 = 8;

    int cp = _$3609(8);
    for (int[] i = { 1 }; i[0] < cp; i[0] += 1)
    {
      int len = _$3635(pos, i);
      pos += len;
    }

    this._$3570 = pos;

    pos += 6;

    pos += 2 * _$3609(pos);

    pos += 2;

    int fields = _$3609(pos);
    pos += 2;
    while (fields-- > 0) {
      pos += _$3629(pos, null);
    }

    int methods = _$3609(pos);
    pos += 2;
    while (methods-- > 0) {
      pos += _$3629(pos, null);
    }

    int[] memberAccess = { -1, -1 };

    this._$3571 = pos;
    int attrs = _$3609(pos);
    pos += 2;
    while (attrs-- > 0) {
      pos += _$3639(pos, memberAccess);
    }

    if (memberAccess[0] != -1)
    {
      this._$3578 = memberAccess[0];
    }

    this._$3572 = pos;
  }

  private int _$3609(int pos) {
    int b1 = _$354(pos);
    int b2 = _$354(pos + 1);

    return (b1 * 256 + b2);
  }

  private int _$3610(int pos) {
    return (_$3609(pos) * 256 * 256 + _$3609(pos + 2));
  }

  private static void _$3598(byte[] arr, int pos, int value) {
    int v1 = (value & 0xFF00) >> 8;
    int v2 = value & 0xFF;

    if (v1 < 0) v1 += 256;
    if (v2 < 0) v2 += 256;

    arr[pos] = (byte)v1;
    arr[(pos + 1)] = (byte)v2;
  }

  private static void _$3628(byte[] arr, int pos, int value) {
    _$3598(arr, pos, (value & 0xFF00) >> 16);
    _$3598(arr, pos + 2, value & 0xFF);
  }

  private int _$3635(int pos, int[] cnt)
  {
    switch (_$354(pos))
    {
    case 7:
    case 8:
      return 3;
    case 12:
      int nameIndex = _$3609(pos + 1);
      if (nameIndex == this._$3579) {
        int descriptorIndex = _$3609(pos + 3);
        if (descriptorIndex == this._$3580) {
          if ((this._$3581 > 0) && (this._$3581 != cnt[0])) {
            throw new IllegalArgumentException("Second initialization of name type index");
          }
          this._$3581 = cnt[0];
        }
      }
      return 5;
    case 10:
      int classname = _$3609(pos + 1);
      int nameAndType = _$3609(pos + 3);
      if (nameAndType == this._$3581)
      {
        int superclass = _$3609(this._$3570 + 4);

        if (superclass == classname)
        {
          if ((this._$3582 > 0) && (this._$3582 != pos)) {
            throw new IllegalStateException("Second initialization of position of <init> invocation");
          }
          this._$3582 = pos;
        }
      }
      return 5;
    case 3:
    case 4:
    case 9:
    case 11:
      return 5;
    case 5:
    case 6:
      cnt[0] += 1;
      return 9;
    case 1:
      int len = _$3609(pos + 1);

      if (_$3648(_$3566, pos)) {
        if ((this._$3579 > 0) && (this._$3579 != cnt[0])) {
          throw new IllegalArgumentException("Second initialization of <init>");
        }
        this._$3579 = cnt[0];
      }

      if (_$3648(_$3568, pos)) {
        if ((this._$3580 > 0) && (this._$3580 != cnt[0])) {
          throw new IllegalArgumentException("Second initialization of ()V");
        }
        this._$3580 = cnt[0];
      }

      if (_$3648(_$3558, pos))
      {
        if ((this._$3573 > 0) && (this._$3573 != cnt[0])) {
          throw new IllegalStateException("org.netbeans.superclass registered for the second time!");
        }

        this._$3573 = cnt[0];
      }

      if (_$3648(_$3560, pos))
      {
        if ((this._$3575 > 0) && (this._$3575 != cnt[0])) {
          throw new IllegalStateException("org.netbeans.interfaces registered for the second time!");
        }

        this._$3575 = cnt[0];
      }

      if (_$3648(_$3562, pos))
      {
        if ((this._$3577 > 0) && (this._$3577 != cnt[0])) {
          throw new IllegalStateException("org.netbeans.member registered for the second time!");
        }

        this._$3577 = cnt[0];
      }
      if (_$3648(_$3564, pos))
      {
        if ((this._$3583 > 0) && (this._$3583 != cnt[0])) {
          throw new IllegalStateException("org.netbeans.name registered for the second time!");
        }

        this._$3583 = cnt[0];
      }

      if (this._$3584 != null)
      {
        String s;
        try {
          s = new String(this._$409, pos + 3, len, "utf-8");
        } catch (UnsupportedEncodingException ex) {
          throw new IllegalStateException("utf-8 is always supported");
        }

        int[] index = (int[])(int[])this._$3584.get(s);
        if (index != null) {
          index[0] = cnt[0]; }

      }

      return (len + 3);
    case 2:
    }
    throw new IllegalStateException("Unknown pool type: " + _$354(pos));
  }

  private int _$3629(int pos, int[] containsPatchAttributeAndRename)
  {
    int s = 8;
    int name = _$3609(pos + 2);

    int cnt = _$3609(pos + 6);

    while (cnt-- > 0) {
      s += _$3639(pos + s, containsPatchAttributeAndRename);
    }
    return s;
  }

  private int _$3639(int pos, int[] containsPatchAttributeAndRename)
  {
    int name = _$3609(pos);

    if (name == this._$3573) {
      if ((this._$3574 > 0) && (this._$3574 != pos)) {
        throw new IllegalStateException("Two attributes with name org.netbeans.superclass");
      }

      this._$3574 = pos;
    }

    if (name == this._$3575) {
      if ((this._$3576 > 0) && (this._$3576 != pos)) {
        throw new IllegalStateException("Two attributes with name org.netbeans.interfaces");
      }

      this._$3576 = pos;
    }

    if ((name == this._$3577) && (containsPatchAttributeAndRename != null)) {
      if (containsPatchAttributeAndRename[0] != -1) {
        throw new IllegalStateException("Second attribute org.netbeans.member");
      }
      containsPatchAttributeAndRename[0] = pos;
    }

    if ((name == this._$3583) && (containsPatchAttributeAndRename != null)) {
      if (containsPatchAttributeAndRename[1] != -1) {
        throw new IllegalStateException("Second attribute org.netbeans.name");
      }
      containsPatchAttributeAndRename[1] = pos;
    }

    int len = _$3610(pos + 2);
    return (len + 6);
  }

  private boolean _$3648(byte[] pattern, int pos)
  {
    int len = _$3609(pos + 1);

    if (pattern.length != len) {
      return false;
    }

    int base = pos + 3;

    for (int i = 0; i < len; ++i) {
      if (pattern[i] != this._$409[(base + i)])
      {
        return false;
      }
    }

    return true;
  }

  static
  {
    try
    {
      _$3558 = "org.netbeans.superclass".getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException(ex.getMessage());
    }

    try
    {
      _$3560 = "org.netbeans.interfaces".getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException(ex.getMessage());
    }

    try
    {
      _$3562 = "org.netbeans.member".getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException(ex.getMessage());
    }

    try
    {
      _$3564 = "org.netbeans.name".getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException(ex.getMessage());
    }

    try
    {
      _$3566 = "<init>".getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException(ex.getMessage());
    }

    try
    {
      _$3568 = "()V".getBytes("utf-8");
    } catch (UnsupportedEncodingException ex) {
      throw new IllegalStateException(ex.getMessage());
    }
  }
}