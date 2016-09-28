package com.supermap.data;

import com.supermap.data.Enum;
class InternalEnum extends Enum{
  protected InternalEnum(int value,int ugcValue){
      super(value,ugcValue);
  }
  public static int getUGCValue(Enum e){
      return Enum.internalGetUGCValue(e);
  }
//  public static Enum parseUGCValue(Class type,int ugcValue){
//      return Enum.internalParseUGCValue(type,ugcValue);
//  }
}

