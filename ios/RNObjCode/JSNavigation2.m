//
//  JSNavigation2.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/23.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSNavigation2.h"
#import "SuperMap/Navigation2.h"
#import "SuperMap/Point2D.h"
#import "JSObjManager.h"

@implementation JSNavigation2
RCT_EXPORT_MODULE();

//RCT_REMAP_METHOD(createObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  
//  Navigation2* nav = [[Navigation2 alloc]init];
//  if(nav){
//    NSInteger key = (NSInteger)nav;
//    [JSObjManager addObj:nav];
//    resolve(@{@"navigation2Id":@(key).stringValue});
//  }else{
//    reject(@"Nav2",@"create Obj failed!!!",nil);
//  }
//}

RCT_REMAP_METHOD(setPathVisible,setPathVisibleById:(NSString*)nav2Id visible:(BOOL)visible resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    [nav setPathVisible:visible];
    resolve(@"1");
  }else{
    reject(@"Nav2",@"setPathVisible failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setNetworkDataset,setNetworkDatasetById:(NSString*)nav2Id datasetId:(NSString*)datasetId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    DatasetVector* dsVector = [JSObjManager getObjWithKey:datasetId];
    [nav setNetworkDataset:dsVector];
    resolve(@"1");
  }else{
    reject(@"Nav2",@"setNetworkDataset failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setTurnDataset,setTurnDatasetById:(NSString*)nav2Id datasetId:(NSString*)datasetId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    DatasetVector* dsVector = [JSObjManager getObjWithKey:datasetId];
    [nav setTurnDataset:dsVector];
    resolve(@"1");
  }else{
    reject(@"Nav2",@"setTurnDataset failed!!!",nil);
  }
}

RCT_REMAP_METHOD(loadModel,loadModelById:(NSString*)nav2Id filePath:(NSString*)path resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  [nav loadModel:path];
}

RCT_REMAP_METHOD(loadModelByFilePath,loadModelByFilePathId:(NSString*)nav2Id filePath:(NSString*)path resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    NSArray *paths = NSSearchPathForDirectoriesInDomains(NSDocumentDirectory, NSUserDomainMask, YES);
    NSString *filesnm = [[paths objectAtIndex:0] stringByAppendingPathComponent:path];
    BOOL isLoad = [nav loadModel:filesnm];
    NSNumber* num = [NSNumber numberWithBool:isLoad];
    resolve(num);
  }else{
    reject(@"Nav2",@"loadModelByFilePath failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setStartPoint,setStartPointById:(NSString*)nav2Id point2DId:(NSString*)point2DId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  Point2D* point = [JSObjManager getObjWithKey:point2DId];
  if (nav) {
     [nav setStartPoint:point.x sPointY:point.y];
     resolve(@"1");
  }else{
    reject(@"Nav2",@"set startPoint failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setDestPoint,setDestPointById:(NSString*)nav2Id point2DId:(NSString*)point2DId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  Point2D* point = [JSObjManager getObjWithKey:point2DId];
  if (nav) {
    [nav setDestinationPoint:point.x dPointY:point.y];
    resolve(@"1");
  }else{
    reject(@"Nav2",@"set DestPoint failed!!!",nil);
  }
}

RCT_REMAP_METHOD(isGuiding,isGuidingById:(NSString*)nav2Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    BOOL isGuiding = [nav isGuiding];
    NSNumber *num = [NSNumber numberWithBool:isGuiding];
    resolve(num);
  }else{
    reject(@"Nav2",@"isGuiding failed!!!",nil);
  }
}

RCT_REMAP_METHOD(cleanPath,cleanPathById:(NSString*)nav2Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    [nav cleanPath];
    resolve(@"1");
  }else{
    reject(@"Nav2",@"cleanPath failed!!!",nil);
  }
}

RCT_REMAP_METHOD(stopGuide,stopGuideById:(NSString*)nav2Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    BOOL isStop = [nav stopGuide];
    NSNumber *num = [NSNumber numberWithBool:isStop];
    resolve(num);
  }else{
    reject(@"Nav2",@"stopGuide failed!!!",nil);
  }
}

RCT_REMAP_METHOD(routeAnalyst,routeAnalystById:(NSString*)nav2Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Navigation2* nav = [JSObjManager getObjWithKey:nav2Id];
  if (nav) {
    BOOL analystBit = [nav routeAnalyst];
    NSNumber *num = [NSNumber numberWithBool:analystBit];
    resolve(num);
  }else{
    reject(@"Nav2",@"routeAnalyst failed!!!",nil);
  }
}
@end
