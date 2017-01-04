//
//  JSAdoptMapView.m
//  GeometryInfo
//
//  Created by 王子豪 on 2017/1/4.
//  Copyright © 2017年 Facebook. All rights reserved.
//

#import "JSAdoptMapView.h"
#import "JSObjManager.h"
@implementation JSAdoptMapView
RCT_EXPORT_MODULE(JSMapControl);
RCT_REMAP_METHOD(getMap,geMapKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapcontrol = [JSObjManager getObjWithKey:key];
  if(mapcontrol){
    Map* map = mapcontrol.map;
    [JSObjManager addObj:map];
    NSInteger key = (NSInteger)map;
    resolve(@{@"mapId":@(key).stringValue});
  }else
    reject(@"MapControl",@"getMap:mapcontrol not exeist!!!",nil);
}
RCT_REMAP_METHOD(getNavigation2,getNavigation2BymapControlId:(NSString*)Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapControl = [JSObjManager getObjWithKey:Id];
  Navigation2* navi2 = [mapControl getNavigation2];
  if(navi2){
    NSInteger key = (NSInteger)navi2;
    [JSObjManager addObj:navi2];
    resolve(@{@"navigation2Id":@(key).stringValue});
  }else{
    reject(@"mapControl",@"get navi2 failed!!!",nil);
  }
}

RCT_REMAP_METHOD(setAction,mapControlId:(NSString*)Id actionType:(int)type resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapControl = [JSObjManager getObjWithKey:Id];
  if (mapControl) {
    mapControl.action = type;
    resolve(@"1");
  }else{
    reject(@"mapControl",@"setAction failed!!!",nil);
  }
}

RCT_REMAP_METHOD(getCurrentGeometry,getCurrentGeometryById:(NSString*)Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapControl = [JSObjManager getObjWithKey:Id];
  Geometry *geo = [mapControl getCurrentGeometry];
  if (geo) {
    NSInteger key = (NSInteger)geo;
    [JSObjManager addObj:geo];
    resolve(@{@"geometryId":@(key).stringValue});
  }else{
    reject(@"mapControl",@"getCurrentGeometry failed!!!",nil);
  }
}
@end
