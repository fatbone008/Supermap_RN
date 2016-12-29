//
//  JSMapView.m
//  rnTest
//
//  Created by imobile-xzy on 16/7/12.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "RCTEventDispatcher.h"

#import "JSMapView.h"
#import "JSObjManager.h"
#import "SuperMap/Point2D.h"

@implementation JSMapView

@synthesize bridge = _bridge;

RCT_EXPORT_MODULE();

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

RCT_REMAP_METHOD(getMapControl,getMapControlKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapcontrol = [JSObjManager getObjWithKey:key];
  if(mapcontrol){
      mapcontrol.mapMeasureDelegate = self;
      mapcontrol.delegate = self;
      mapcontrol.geometrySelectedDelegate = self;
      NSInteger key = (NSInteger)mapcontrol;
      resolve(@{@"mapControlId":@(key).stringValue});
  }else
      reject(@"MapControl",@"getMapControl:mapcontrol init faild!!!",nil);
}

RCT_REMAP_METHOD(setAction,setActionKey:(NSString*)key actionType:(int)actionType resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapcontrol = [JSObjManager getObjWithKey:key];
  if(mapcontrol){
    mapcontrol.action = (Action)actionType;
      resolve(@"1");
  }else
    reject(@"MapControl",@"setAction:mapcontrol not exeist!!!",nil);
}

RCT_REMAP_METHOD(addActionChangedListener,addActionChangedListenerKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  //ios not imp action change callback
//  MapControl* mapcontrol = [JSObjManager getObjWithKey:key];
//  if(mapcontrol){
//    
//    resolve(@"1");
//  }else
//  reject(@"MapControl",@"setAction:mapcontrol not exeist!!!",nil);
}

RCT_REMAP_METHOD(addMeasureResultListener,addMeasureResultListenerKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  //ios not imp action change callback
  //  MapControl* mapcontrol = [JSObjManager getObjWithKey:key];
  //  if(mapcontrol){
  //
  //    resolve(@"1");
  //  }else
  //  reject(@"MapControl",@"setAction:mapcontrol not exeist!!!",nil);
}


-(double)getMeasureResult:(double)result lastPoint:(Point2D*)lastPoint{
  
//  RCTEventDispatcher
  NSDictionary* body = @{@"measureResult": [NSString stringWithFormat:@"%.14f",result],@"lastPoint":[NSString stringWithFormat:@"x=%.14f y=%.14f",lastPoint.x,lastPoint.y]};
  [self.bridge.eventDispatcher sendDeviceEventWithName:@"Measure" body:body];
  
  return result;
}
@end
