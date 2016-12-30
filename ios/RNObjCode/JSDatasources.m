//
//  JSDatasources.m
//  rnTest
//
//  Created by imobile-xzy on 16/7/5.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSDatasources.h"
#import "SuperMap/Datasources.h"
#import "JSObjManager.h"

@implementation JSDatasources
//注册为Native模块
RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(open,userKey:(NSString*)key  infoKey:(NSString*)infoKey resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Datasources* datasources = [JSObjManager getObjWithKey:key];
  DatasourceConnectionInfo* info = [JSObjManager getObjWithKey:infoKey];
  if(datasources && info){
    Datasource*  datasource = [datasources open:info];
    if(datasource){
      [JSObjManager addObj:datasource];
      resolve(@{@"datasourceId":@( (NSInteger)datasource).stringValue});
    }else{
      NSLog(@"open datasource failed");
      resolve(@"0");
    }
 
  }else
    reject(@"datasources",@"open:datasources or DatasourceConnectionInfo not exeist!!!",nil);
}

RCT_REMAP_METHOD(get,userKey:(NSString*)key index:(NSInteger)index resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Datasources* datasources = [JSObjManager getObjWithKey:key];
  Datasource* gettingDS = [datasources get:index];
  if(gettingDS){
    NSInteger DSkey = (NSInteger)gettingDS;
    [JSObjManager addObj:gettingDS];
    resolve(@{@"datasourceId":@(DSkey).stringValue});
  }else{
    reject(@"datasource",@"get Datasource failed",nil);
  }
}

RCT_REMAP_METHOD(getByName,userKey:(NSString*)key alias:(NSString*)alias resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Datasources* datasources = [JSObjManager getObjWithKey:key];
  Datasource* gettingDS = [datasources getAlias:alias];
  if(gettingDS){
    NSInteger DSkey = (NSInteger)gettingDS;
    [JSObjManager addObj:gettingDS];
    resolve(@{@"datasourceId":@(DSkey).stringValue});
  }else{
    reject(@"datasource",@"get Datasource by alias failed",nil);
  }
}


@end
