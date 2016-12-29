//
//  JSDatasetVector.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/21.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSDatasetVector.h"
#import "SuperMap/DatasetVector.h"
#import "JSObjManager.h"

@implementation JSDatasetVector
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(query,datasetVectorId:(NSString*)datasetVectorId rectangle2DId:(NSString*)rectangle2DId cursorType:(int)cursorType resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    DatasetVector* datasetVector = [JSObjManager getObjWithKey:datasetVectorId];
    Rectangle2D* rectangle = [JSObjManager getObjWithKey:rectangle2DId];
    Recordset* record = [datasetVector queryWithBounds:rectangle Type:cursorType];
  if(record){
    NSInteger key = (NSInteger)record;
    [JSObjManager addObj:record];
    resolve(@{@"recordsetId":@(key).stringValue});
  }else{
    reject(@"datasetVector",@"quary failed!!!",nil);
  }
}
  
  RCT_REMAP_METHOD(queryWithGeometry,queryWithGeometryByDatasetVectorId:(NSString*)datasetVectorId geometryId:(NSString*)geometryId bufferDistance:(double)distance cursorType:(int)cursorType resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    DatasetVector* datasetVector = [JSObjManager getObjWithKey:datasetVectorId];
    Geometry* geo = [JSObjManager getObjWithKey:geometryId];
    Recordset* record = [datasetVector queryWithGeometry:geo BufferDistance:distance Type:cursorType];
    if(record){
      NSInteger key = (NSInteger)record;
      [JSObjManager addObj:record];
      resolve(@{@"recordsetId":@(key).stringValue});
    }else{
      reject(@"datasetVector",@"quary failed!!!",nil);
    }
  }

RCT_REMAP_METHOD(getRecordset,datasetVectorId:(NSString*)datasetVectorId isEmptyRecordset:(BOOL)isEmptyRecordset cursorType:(CursorType)cursorType resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  DatasetVector* datasetVector = [JSObjManager getObjWithKey:datasetVectorId];
//  Rectangle2D* rectangle = [JSObjManager getObjWithKey:rectangle2DId];
//  Recordset* record = [datasetVector queryWithBounds:rectangle Type:cursorType];
//  if(record){
//    NSInteger key = (NSInteger)record;
//    [JSObjManager addObj:record];
//    resolve(@{@"recordsetId":@(key).stringValue});
//  }else{
//    reject(@"datasetVector",@"quary failed!!!",nil);
//  }
}

//RCT_REMAP_METHOD(getRecordset,datasetVectorId:(NSString*)datasetVectorId isEmptyRecordset:(BOOL)isEmptyRecordset cursorType:(CursorType)cursorType resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  //  DatasetVector* datasetVector = [JSObjManager getObjWithKey:datasetVectorId];
//  //  Rectangle2D* rectangle = [JSObjManager getObjWithKey:rectangle2DId];
//  //  Recordset* record = [datasetVector queryWithBounds:rectangle Type:cursorType];
//  //  if(record){
//  //    NSInteger key = (NSInteger)record;
//  //    [JSObjManager addObj:record];
//  //    resolve(@{@"recordsetId":@(key).stringValue});
//  //  }else{
//  //    reject(@"datasetVector",@"quary failed!!!",nil);
//  //  }
//}
@end
