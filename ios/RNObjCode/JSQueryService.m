//
//  JSQueryService.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/23.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSQueryService.h"
#import "SuperMap/QueryService.h"
#import "JSObjManager.h"

@implementation JSQueryService
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObj,createObjWithURL:(NSString*)url resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  QueryService* queryService = [[QueryService alloc]init];
  if (queryService) {
    NSInteger key = (NSInteger)queryService;
    [JSObjManager addObj:queryService];
    resolve(@{@"_queryServiceId_":@(key).stringValue});
  }else{
    reject(@"QueryService",@"create Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(query,queryById:(NSString*)QSId withServiceParam:(ServiceQueryParameter*)para mode:(QueryMode)mode resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    QueryService* queryService = [JSObjManager getObjWithKey:QSId];
    FeatureSet* featureSet = [queryService queryWithServiceParam:para QueryMode:mode];
  if (featureSet) {
    NSInteger key = (NSInteger)featureSet;
    [JSObjManager addObj:featureSet];
    resolve(@{@"_queryResultId_":@(key).stringValue});
  }else{
    reject(@"QueryService",@"create Obj failed!!!",nil);
  }
}
@end
