//
//  JSPoint.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/23.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSPoint.h"
#import "JSObjManager.h"
@implementation JSPoint
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObj,createObjByX:(double)xNum Y:(double)yNum resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  NSNumber* xObj = [NSNumber numberWithDouble:xNum];
  NSNumber* yObj = [NSNumber numberWithDouble:yNum];
  NSArray* arr = [NSArray arrayWithObjects:xObj,yObj, nil];
  if (arr) {
    NSInteger key = (NSInteger)arr;
    [JSObjManager addObj:arr];
    resolve(@{@"pointId":@(key).stringValue});
  }else{
    reject(@"point",@"create point failed!!!",nil);
  }
}
@end
