//
//  JSDatasource.m
//  rnTest
//
//  Created by imobile-xzy on 16/7/5.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSDatasource.h"
#import "SuperMap/Datasource.h"
#import "JSObjManager.h"

@implementation JSDatasource
//注册为Native模块
RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(getDatasets,getUserKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Datasource* datasource = [JSObjManager getObjWithKey:key];
  Datasets* datasets = datasource.datasets;
  if(datasets){
    NSInteger key = (NSInteger)datasets;
    [JSObjManager addObj:datasets];
    //resolve(@(key).stringValue);
    resolve(@{@"datasetsId":@(key).stringValue});
  }else
    reject(@"datasets",@"getDatasets:datasets not exeist!!!",nil);
}

@end
