//
//  JSTrack.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/25.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSTrack.h"
#import "SuperMap/Track.h"
#import "JSObjManager.h"
@implementation JSTrack
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Track* track = [[Track alloc]init];
  if(track){
    NSInteger key = (NSInteger)track;
    [JSObjManager addObj:track];
    resolve(@{@"_trackId_":@(key).stringValue});
  }else{
    reject(@"track",@"create Obj failed!!!",nil);
  }
}

RCT_REMAP_METHOD(createDataset,createDatasetById:(NSString*)trackId dataSourceId:(NSString*)dsId name:(NSString*)name resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Datasource* dataSource = [JSObjManager getObjWithKey:dsId];
  DatasetVector* dsVector = [Track creatDataset:dataSource DatasetName:name];
  if(dsVector){
    NSInteger key = (NSInteger)dsVector;
    [JSObjManager addObj:dsVector];
    resolve(@{@"datasetId":@(key).stringValue});
  }else{
    reject(@"track",@"create dataset failed!!!",nil);
  }
}

//RCT_REMAP_METHOD(getCustomLocation,createDatasetById:(NSString*)trackId dataSourceId:(NSString*)dsId name:(NSString*)name resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  Datasource* dataSource = [JSObjManager getObjWithKey:dsId];
//  DatasetVector* dsVector = [Track creatDataset:dataSource DatasetName:name];
//  if(dsVector){
//    NSInteger key = (NSInteger)dsVector;
//    [JSObjManager addObj:dsVector];
//    resolve(@{@"datasetId":@(key).stringValue});
//  }else{
//    reject(@"track",@"create dataset failed!!!",nil);
//  }
//}

//RCT_REMAP_METHOD(getCustomLocation,createDatasetById:(NSString*)trackId dataSourceId:(NSString*)dsId name:(NSString*)name resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  Datasource* dataSource = [JSObjManager getObjWithKey:dsId];
//  DatasetVector* dsVector = [Track creatDataset:dataSource DatasetName:name];
//  if(dsVector){
//    NSInteger key = (NSInteger)dsVector;
//    [JSObjManager addObj:dsVector];
//    resolve(@{@"datasetId":@(key).stringValue});
//  }else{
//    reject(@"track",@"create dataset failed!!!",nil);
//  }
//}
@end
