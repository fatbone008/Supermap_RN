//
//  JSDataset.m
//  rnTest
//
//  Created by imobile-xzy on 16/7/5.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "SuperMap/Dataset.h"
#import "SuperMap/DatasetVector.h"
#import "JSDataset.h"
#import "JSObjManager.h"

@implementation JSDataset
//注册为Native模块
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createJSObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Dataset* dataset = [[Dataset alloc]init];
  if(dataset){
    NSInteger key = (NSInteger)dataset;
    [JSObjManager addObj:dataset];
    resolve(@{@"dataSetId":@(key).stringValue});
  }else{
    reject(@"Dataset",@"create Dataset failed!!!",nil);
  }
}
//RCT_REMAP_METHOD(destroyJSObj,destroyJSObjKey:(NSNumber*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  Dataset* dataset = [JSObjManager getObjWithKey:key];
//  if(dataset){
//    [JSObjManager removeObj:key];
//    resolve(@(1));
//  }else
//    reject(@"Dataset",@"destroy obj failed!!!",nil);
//}

RCT_REMAP_METHOD(toDatasetVector, userKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  DatasetVector* dataset = [JSObjManager getObjWithKey:key];
  if(dataset){
    NSInteger dsVectorKey = (NSInteger)dataset;
    [JSObjManager removeObj:key];
    [JSObjManager addObj:dataset];
    resolve(@{@"datasetVectorId":@(dsVectorKey).stringValue});
  }else{
    reject(@"Dataset",@"create Dataset failed!!!",nil);
  }
}

RCT_REMAP_METHOD(getPrjCoordSys,key:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Dataset* dataset = [JSObjManager getObjWithKey:key];
  PrjCoordSys* proj = dataset.prjCoordSys;
  if (proj) {
    NSInteger projSys = (NSInteger)proj;
    [JSObjManager addObj:proj];
    resolve(@{@"projSysId":@(projSys).stringValue});
  }else{
    reject(@"dataset",@"get dataset projSys failed",nil);
  }
}
@end
