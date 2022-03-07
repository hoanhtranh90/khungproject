package com.dgtt.business.utils;

public class BitUtils {

  public static int setBit(int v, int n) {
    return v | (1 << n);
  }

  public static int getBit(int v, int n) {
    return (v & (1 << n)) >> n;
  }
  
  public static short setBit(short v, int n) {
    return (short)(v | (1 << n));
  }

  public static short getBit(short v, int n) {
    return (short) ((v & (1 << n)) >> n);
  }

  /**
   * @param callStatus
   * @param index
   * @return
   */
  public static int removeBit(int v, int n) {
    return v & (~(1 << n));
  }
  
  public static boolean hasBit(int v, int n) {
    return getBit(v, n)==1;
  }
  
    public static void main(String[] args) {
        System.out.println(hasBit(7, 2));
    }
}

