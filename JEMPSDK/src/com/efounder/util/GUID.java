package com.efounder.util;

import java.security.*;

/**
 * <p>Title: </p>
 *
 * <p>Description: </p>
 *
 * <p>Copyright: Copyright (c) 2003</p>
 *
 * <p>Company: </p>
 *
 * @author not attributable
 * @version 1.0
 */
public class GUID {

  /**
   * Explicit serialVersionUID for interoperability.
   */
   private static final long serialVersionUID = -4856846361193249489L;

  /*
   * The most significant 64 bits of this GUID.
   *
   * @serial
   */
  private final long mostSigBits;

  /*
   * The least significant 64 bits of this GUID.
   *
   * @serial
   */
  private final long leastSigBits;

  /*
   * The version number associated with this GUID. Computed on demand.
   */
  private transient int version = -1;

  /*
   * The variant number associated with this GUID. Computed on demand.
   */
  private transient int variant = -1;

  /*
   * The timestamp associated with this GUID. Computed on demand.
   */
  private transient volatile long timestamp = -1;

  /*
   * The clock sequence associated with this GUID. Computed on demand.
   */
  private transient int sequence = -1;

  /*
   * The node number associated with this GUID. Computed on demand.
   */
  private transient long node = -1;

  /*
   * The hashcode of this GUID. Computed on demand.
   */
  private transient int hashCode = -1;

  /*
   * The random number generator used by this class to create random
   * based GUIDs.
   */
  private static volatile SecureRandom numberGenerator = null;

  // Constructors and Factories

  /*
   * Private constructor which uses a byte array to construct the new GUID.
   */
  private GUID(byte[] data) {
      long msb = 0;
      long lsb = 0;
      assert data.length == 16;
      for (int i=0; i<8; i++)
          msb = (msb << 8) | (data[i] & 0xff);
      for (int i=8; i<16; i++)
          lsb = (lsb << 8) | (data[i] & 0xff);
      this.mostSigBits = msb;
      this.leastSigBits = lsb;
  }

  /**
   * Constructs a new <tt>GUID</tt> using the specified data.
   * <tt>mostSigBits</tt> is used for the most significant 64 bits
   * of the <tt>GUID</tt> and <tt>leastSigBits</tt> becomes the
   * least significant 64 bits of the <tt>GUID</tt>.
   *
   * @param  mostSigBits
   * @param  leastSigBits
   */
  public GUID(long mostSigBits, long leastSigBits) {
      this.mostSigBits = mostSigBits;
      this.leastSigBits = leastSigBits;
  }

  /**
   * Static factory to retrieve a type 4 (pseudo randomly generated) GUID.
   *
   * The <code>GUID</code> is generated using a cryptographically strong
   * pseudo random number generator.
   *
   * @return  a randomly generated <tt>GUID</tt>.
   */
  public static GUID randomGUID() {
      SecureRandom ng = numberGenerator;
      if (ng == null) {
          numberGenerator = ng = new SecureRandom();
      }

      byte[] randomBytes = new byte[16];
      ng.nextBytes(randomBytes);
      randomBytes[6]  &= 0x0f;  /* clear version        */
      randomBytes[6]  |= 0x40;  /* set to version 4     */
      randomBytes[8]  &= 0x3f;  /* clear variant        */
      randomBytes[8]  |= 0x80;  /* set to IETF variant  */
      GUID result = new GUID(randomBytes);
      return new GUID(randomBytes);
  }

  /**
   * Static factory to retrieve a type 3 (name based) <tt>GUID</tt> based on
   * the specified byte array.
   *
   * @param  name a byte array to be used to construct a <tt>GUID</tt>.
   * @return  a <tt>GUID</tt> generated from the specified array.
   */
  public static GUID nameGUIDFromBytes(byte[] name) {
      MessageDigest md;
      try {
          md = MessageDigest.getInstance("MD5");
      } catch (NoSuchAlgorithmException nsae) {
          throw new InternalError("MD5 not supported");
      }
      byte[] md5Bytes = md.digest(name);
      md5Bytes[6]  &= 0x0f;  /* clear version        */
      md5Bytes[6]  |= 0x30;  /* set to version 3     */
      md5Bytes[8]  &= 0x3f;  /* clear variant        */
      md5Bytes[8]  |= 0x80;  /* set to IETF variant  */
      return new GUID(md5Bytes);
  }

  /**
   * Creates a <tt>GUID</tt> from the string standard representation as
   * described in the {@link #toString} method.
   *
   * @param  name a string that specifies a <tt>GUID</tt>.
   * @return  a <tt>GUID</tt> with the specified value.
   * @throws IllegalArgumentException if name does not conform to the
   *         string representation as described in {@link #toString}.
   */
  public static GUID fromString(String name) {
      String[] components = name.split("-");
      if (components.length != 5)
          throw new IllegalArgumentException("Invalid GUID string: "+name);
      for (int i=0; i<5; i++)
          components[i] = "0x"+components[i];

      long mostSigBits = Long.decode(components[0]).longValue();
      mostSigBits <<= 16;
      mostSigBits |= Long.decode(components[1]).longValue();
      mostSigBits <<= 16;
      mostSigBits |= Long.decode(components[2]).longValue();

      long leastSigBits = Long.decode(components[3]).longValue();
      leastSigBits <<= 48;
      leastSigBits |= Long.decode(components[4]).longValue();

      return new GUID(mostSigBits, leastSigBits);
  }

  // Field Accessor Methods

  /**
   * Returns the least significant 64 bits of this GUID's 128 bit value.
   *
   * @return the least significant 64 bits of this GUID's 128 bit value.
   */
  public long getLeastSignificantBits() {
      return leastSigBits;
  }

  /**
   * Returns the most significant 64 bits of this GUID's 128 bit value.
   *
   * @return the most significant 64 bits of this GUID's 128 bit value.
   */
  public long getMostSignificantBits() {
      return mostSigBits;
  }

  /**
   * The version number associated with this <tt>GUID</tt>. The version
   * number describes how this <tt>GUID</tt> was generated.
   *
   * The version number has the following meaning:<p>
   * <ul>
   * <li>1    Time-based GUID
   * <li>2    DCE security GUID
   * <li>3    Name-based GUID
   * <li>4    Randomly generated GUID
   * </ul>
   *
   * @return  the version number of this <tt>GUID</tt>.
   */
  public int version() {
      if (version < 0) {
          // Version is bits masked by 0x000000000000F000 in MS long
          version = (int)((mostSigBits >> 12) & 0x0f);
      }
      return version;
  }

  /**
   * The variant number associated with this <tt>GUID</tt>. The variant
   * number describes the layout of the <tt>GUID</tt>.
   *
   * The variant number has the following meaning:<p>
   * <ul>
   * <li>0    Reserved for NCS backward compatibility
   * <li>2    The Leach-Salz variant (used by this class)
   * <li>6    Reserved, Microsoft Corporation backward compatibility
   * <li>7    Reserved for future definition
   * </ul>
   *
   * @return  the variant number of this <tt>GUID</tt>.
   */
  public int variant() {
      if (variant < 0) {
          // This field is composed of a varying number of bits
          if ((leastSigBits >>> 63) == 0) {
              variant = 0;
          } else if ((leastSigBits >>> 62) == 2) {
              variant = 2;
          } else {
              variant = (int)(leastSigBits >>> 61);
          }
      }
      return variant;
  }

  /**
   * The timestamp value associated with this GUID.
   *
   * <p>The 60 bit timestamp value is constructed from the time_low,
   * time_mid, and time_hi fields of this <tt>GUID</tt>. The resulting
   * timestamp is measured in 100-nanosecond units since midnight,
   * October 15, 1582 UTC.<p>
   *
   * The timestamp value is only meaningful in a time-based GUID, which
   * has version type 1. If this <tt>GUID</tt> is not a time-based GUID then
   * this method throws UnsupportedOperationException.
   *
   * @throws UnsupportedOperationException if this GUID is not a
   *         version 1 GUID.
   */
  public long timestamp() {
      if (version() != 1) {
          throw new UnsupportedOperationException("Not a time-based GUID");
      }
      long result = timestamp;
      if (result < 0) {
          result = (mostSigBits & 0x0000000000000FFFL) << 48;
          result |= ((mostSigBits >> 16) & 0xFFFFL) << 32;
          result |= mostSigBits >>> 32;
          timestamp = result;
      }
      return result;
  }

  /**
   * The clock sequence value associated with this GUID.
   *
   * <p>The 14 bit clock sequence value is constructed from the clock
   * sequence field of this GUID. The clock sequence field is used to
   * guarantee temporal uniqueness in a time-based GUID.<p>
   *
   * The  clockSequence value is only meaningful in a time-based GUID, which
   * has version type 1. If this GUID is not a time-based GUID then
   * this method throws UnsupportedOperationException.
   *
   * @return  the clock sequence of this <tt>GUID</tt>.
   * @throws UnsupportedOperationException if this GUID is not a
   *         version 1 GUID.
   */
  public int clockSequence() {
      if (version() != 1) {
          throw new UnsupportedOperationException("Not a time-based GUID");
      }
      if (sequence < 0) {
          sequence = (int)((leastSigBits & 0x3FFF000000000000L) >>> 48);
      }
      return sequence;
  }

  /**
   * The node value associated with this GUID.
   *
   * <p>The 48 bit node value is constructed from the node field of
   * this GUID. This field is intended to hold the IEEE 802 address
   * of the machine that generated this GUID to guarantee spatial
   * uniqueness.<p>
   *
   * The node value is only meaningful in a time-based GUID, which
   * has version type 1. If this GUID is not a time-based GUID then
   * this method throws UnsupportedOperationException.
   *
   * @return  the node value of this <tt>GUID</tt>.
   * @throws UnsupportedOperationException if this GUID is not a
   *         version 1 GUID.
   */
  public long node() {
      if (version() != 1) {
          throw new UnsupportedOperationException("Not a time-based GUID");
      }
      if (node < 0) {
          node = leastSigBits & 0x0000FFFFFFFFFFFFL;
      }
      return node;
  }

  // Object Inherited Methods

  /**
   * Returns a <code>String</code> object representing this
   * <code>GUID</code>.
   *
   * <p>The GUID string representation is as described by this BNF :
   * <pre>
   *  GUID                   = <time_low> "-" <time_mid> "-"
   *                           <time_high_and_version> "-"
   *                           <variant_and_sequence> "-"
   *                           <node>
   *  time_low               = 4*<hexOctet>
   *  time_mid               = 2*<hexOctet>
   *  time_high_and_version  = 2*<hexOctet>
   *  variant_and_sequence   = 2*<hexOctet>
   *  node                   = 6*<hexOctet>
   *  hexOctet               = <hexDigit><hexDigit>
   *  hexDigit               =
   *        "0" | "1" | "2" | "3" | "4" | "5" | "6" | "7" | "8" | "9"
   *        | "a" | "b" | "c" | "d" | "e" | "f"
   *        | "A" | "B" | "C" | "D" | "E" | "F"
   * </pre>
   *
   * @return  a string representation of this <tt>GUID</tt>.
   */
  public String toString() {
      return (digits(mostSigBits >> 32, 8) + "-" +
              digits(mostSigBits >> 16, 4) + "-" +
              digits(mostSigBits, 4) + "-" +
              digits(leastSigBits >> 48, 4) + "-" +
              digits(leastSigBits, 12));
  }

  /** Returns val represented by the specified number of hex digits. */
  private static String digits(long val, int digits) {
      long hi = 1L << (digits * 4);
      return Long.toHexString(hi | (val & (hi - 1))).substring(1);
  }

  /**
   * Returns a hash code for this <code>GUID</code>.
   *
   * @return  a hash code value for this <tt>GUID</tt>.
   */
  public int hashCode() {
      if (hashCode == -1) {
          hashCode = (int)((mostSigBits >> 32) ^
                           mostSigBits ^
                           (leastSigBits >> 32) ^
                           leastSigBits);
      }
      return hashCode;
  }

  /**
   * Compares this object to the specified object.  The result is
   * <tt>true</tt> if and only if the argument is not
   * <tt>null</tt>, is a <tt>GUID</tt> object, has the same variant,
   * and contains the same value, bit for bit, as this <tt>GUID</tt>.
   *
   * @param   obj   the object to compare with.
   * @return  <code>true</code> if the objects are the same;
   *          <code>false</code> otherwise.
   */
  public boolean equals(Object obj) {
      if (!(obj instanceof GUID))
          return false;
      if (((GUID)obj).variant() != this.variant())
          return false;
      GUID id = (GUID)obj;
      return (mostSigBits == id.mostSigBits &&
              leastSigBits == id.leastSigBits);
  }

  // Comparison Operations

  /**
   * Compares this GUID with the specified GUID.
   *
   * <p>The first of two GUIDs follows the second if the most significant
   * field in which the GUIDs differ is greater for the first GUID.
   *
   * @param  val <tt>GUID</tt> to which this <tt>GUID</tt> is to be compared.
   * @return -1, 0 or 1 as this <tt>GUID</tt> is less than, equal
   *         to, or greater than <tt>val</tt>.
   */
  public int compareTo(GUID val) {
      // The ordering is intentionally set up so that the GUIDs
      // can simply be numerically compared as two numbers
      return (this.mostSigBits < val.mostSigBits ? -1 :
              (this.mostSigBits > val.mostSigBits ? 1 :
               (this.leastSigBits < val.leastSigBits ? -1 :
                (this.leastSigBits > val.leastSigBits ? 1 :
                 0))));
  }

  /**
   * Reconstitute the <tt>GUID</tt> instance from a stream (that is,
   * deserialize it). This is necessary to set the transient fields
   * to their correct uninitialized value so they will be recomputed
   * on demand.
   */
  private void readObject(java.io.ObjectInputStream in)
      throws java.io.IOException, ClassNotFoundException {

      in.defaultReadObject();

      // Set "cached computation" fields to their initial values
      version = -1;
      variant = -1;
      timestamp = -1;
      sequence = -1;
      node = -1;
      hashCode = -1;
    }
}
