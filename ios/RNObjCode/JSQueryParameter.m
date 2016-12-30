//
//  JSQueryParameter.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/23.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSQueryParameter.h"
#import "SuperMap/QueryParameter.h"
#import "JSObjManager.h"

@implementation JSQueryParameter
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryParameter* para = [[QueryParameter alloc]init];
  if (para) {
    NSInteger key = (NSInteger)para;
    [JSObjManager addObj:para];
    resolve(@{@"queryParameterId":@(key).stringValue});
  }else{
    reject(@"queryParameter",@"create Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setAttributeFilter,setAttributeFilterById:(NSString*)paraId attributeFilter:(NSString*)attributeFilter resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    QueryParameter* para = [JSObjManager getObjWithKey:paraId];
  if (para) {
    para.attriButeFilter = attributeFilter;
    resolve(@"done");
  }else{
    reject(@"queryParameter",@"get Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setGroupBy,setGroupById:(NSString*)paraId groups:(NSArray*)groups resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryParameter* para = [JSObjManager getObjWithKey:paraId];
  if (para) {
    para.groupBy = groups;
    resolve(@"done");
  }else{
    reject(@"queryParameter",@"get Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setHasGeometry,setHasGeometryById:(NSString*)paraId has:(BOOL)isHas resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryParameter* para = [JSObjManager getObjWithKey:paraId];
  if (para) {
    para.hasGeometry = isHas;
    resolve(@"done");
  }else{
    reject(@"queryParameter",@"get Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setResultFields,setResultFieldsById:(NSString*)paraId fields:(NSArray*)fields resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryParameter* para = [JSObjManager getObjWithKey:paraId];
  if (para) {
    para.resultFields = fields;
    resolve(@"done");
  }else{
    reject(@"queryParameter",@"get Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setOrderBy,setOrderById:(NSString*)paraId fields:(NSArray*)fields resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryParameter* para = [JSObjManager getObjWithKey:paraId];
  if (para) {
    para.orderBy = fields;
    resolve(@"done");
  }else{
    reject(@"queryParameter",@"get Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setSpatialQueryMode,setSpatialQueryModeById:(NSString*)paraId mode:(SpatialQueryMode)mode resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryParameter* para = [JSObjManager getObjWithKey:paraId];
  if (para) {
    para.spatialQueryMode = mode;
    resolve(@"done");
  }else{
    reject(@"queryParameter",@"get Obj failed!!!",nil);
  }
}
@end
