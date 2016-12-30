//
//  JSObjManager.m
//  iMobileRnIos
//
//  Created by imobile-xzy on 16/5/12.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSObjManager.h"

//#define mDict [[NSMutableDictionary alloc]initWithCapacity:30]
static NSMutableDictionary* mDict=nil;// = //[[NSMutableDictionary alloc]initWithCapacity:30];
@implementation JSObjManager

//-(id)init{
//  
//  if(self=[super init]){
//    mDict = [[NSMutableDictionary alloc]initWithCapacity:3];
//  }
//  return self;
//}


+(void)removeObj:(id)key{
  if(mDict[key]!=nil){
    [mDict removeObjectForKey:key];
  }
}
+(id)getObjWithKey:(id)key{
  id obj = mDict[key];
  
  if(obj==nil){
    @throw [[NSException alloc]initWithName:@"RN ERROR" reason:@"native obj is not exeist" userInfo:nil];
  }
  
  return obj;
}
+(void)addObj:(id)obj{
  if(!mDict)
    mDict = [[NSMutableDictionary alloc]initWithCapacity:30];
  NSNumber* key = @((NSInteger)obj);
  if(mDict[key.stringValue]==nil){
    mDict[key.stringValue]=obj;
  }else{
    //@throw [[NSException alloc]initWithName:@"RN ERROR" reason:@"native has exeisted" userInfo:nil];
    NSLog(@"native has exeisted");
  }
}
@end
