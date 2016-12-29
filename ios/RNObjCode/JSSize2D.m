//
//  JSSize2D.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/24.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSSize2D.h"
#import "SuperMap/Size2D.h"
#import "JSObjManager.h"
@implementation JSSize2D
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObj,createObjByWidth:(double)width height:(double)height resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Size2D* size = [[Size2D alloc]initWithWidth:width Height:height];
  if(size){
    NSInteger key = (NSInteger)size;
    [JSObjManager addObj:size];
    resolve(@{@"size2DId":@(key).stringValue});
  }else{
    reject(@"size2D",@"create size2D failed!!!",nil);
  }
}
@end
