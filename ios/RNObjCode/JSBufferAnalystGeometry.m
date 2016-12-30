//
//  JSBufferAnalystGeometry.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/18.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSBufferAnalystGeometry.h"
#import "SuperMap/BufferAnalystGeometry.h"
#import "JSObjManager.h"

@implementation JSBufferAnalystGeometry
RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(createBuffer,bufferGeoKey:(NSString*)key geometryId:(NSString*)geoId bufferAnalystParaId:(NSString*)paraId projCoorSys:(NSString*)projSys resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    Geometry* sourceGeo = [JSObjManager getObjWithKey:geoId];
    BufferAnalystParameter* para = [JSObjManager getObjWithKey:paraId];
    PrjCoordSys* coorSys = [JSObjManager getObjWithKey:projSys];
    GeoRegion* region = [BufferAnalystGeometry CreateBufferSourceGeometry:sourceGeo BufferParam:para prjCoordSys:coorSys];
  if (region) {
    NSInteger regionKey = (NSInteger)region;
    [JSObjManager addObj:region];
    resolve(@{@"geoRegionId":@(regionKey).stringValue});
  }else{
    reject(@"bufferAnalystGeo",@"bufferAnalyst failed",nil);
  }
}
@end
