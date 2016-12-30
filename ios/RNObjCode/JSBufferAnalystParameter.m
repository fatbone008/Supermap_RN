//
//  BufferAnalystParameter.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/21.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSBufferAnalystParameter.h"
#import "SuperMap/BufferAnalystParameter.h"
#import "JSObjManager.h"

@implementation BufferAnalystParameter
RCT_EXPORT_MODULE();

//RCT_REMAP_METHOD(createObj,createWithResolver:(RCTPromiseResolveBlock)resolve andRejecter:(RCTPromiseRejectBlock)reject){
//  BufferAnalystParameter* para = [[BufferAnalystParameter alloc]init];
//  if (para) {
//    NSInteger bufferAnalystParaKey = (NSInteger)para;
//    [JSObjManager addObj:para];
//    resolve(@{@"bufferAnalystParameterId":@(bufferAnalystParaKey).stringValue});
//  }else{
//    reject(@"bufferAnalystPara",@"bufferAnalystPara create failed",nil);
//  }
//}
//
//RCT_REMAP_METHOD(setEndType,bufferAnalystParameterId:(NSString*) bufferAnalystParaId endType:(int)endType resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//    BufferAnalystParameter* bufferanalystPara = [JSObjManager getObjWithKey:bufferAnalystParaId];
//    bufferanalystPara.bufferEndType = (BufferEndType)endType;
//}
//
//RCT_REMAP_METHOD(setLeftDistance,bufferAnalystParameterId:(NSString*) bufferAnalystParaId leftDistance:(NSString*)distance resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  BufferAnalystParameter* bufferanalystPara = [JSObjManager getObjWithKey:bufferAnalystParaId];
//  bufferanalystPara.leftDistance = [NSString stringWithString:distance];
//}
//
//RCT_REMAP_METHOD(setRightDistance,bufferAnalystParameterId:(NSString*) bufferAnalystParaId rightDistance:(NSString*)distance resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  BufferAnalystParameter* bufferanalystPara = [JSObjManager getObjWithKey:bufferAnalystParaId];
//  bufferanalystPara.rightDistance = [NSString stringWithString:distance];
//}
@end
