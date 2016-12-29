//
//  JSMapControl.m
//  rnTest
//
//  Created by imobile-xzy on 16/7/11.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSMapControl.h"
#import "SuperMap/MapControl.h"
#import "SuperMap/Action.h"
#import "JSObjManager.h"

#import "RCTEventDispatcher.h"

#import "RCTConvert.h"
#import "UIView+React.h"
#import <objc/runtime.h>
#import "RCTUIManager.h"
#import "RCTWebView.h"

@interface MapControl(ReactCategory)

@property (nonatomic, strong) JSMapControl *jsMapControl;
@property (nonatomic, copy) RCTBubblingEventBlock onChange;
@end

@implementation MapControl (ReactCategory)


-(void)setJsMapControl:(JSMapControl *)jsMapControl{
  objc_setAssociatedObject(self, @selector(jsMapControl), jsMapControl, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
}
-(JSMapControl*)jsMapControl{
  return (JSMapControl *)objc_getAssociatedObject(self, @selector(jsMapControl));
}

-(void)setOnChange:(RCTBubblingEventBlock)onChange{
  objc_setAssociatedObject(self, @selector(onChange), onChange, OBJC_ASSOCIATION_RETAIN_NONATOMIC);
  if(self.onChange)
  {
    self.onChange(@{@"mapViewId":@((NSUInteger)self).stringValue});
  }
}
-(RCTBubblingEventBlock)onChange{
  return (RCTBubblingEventBlock)objc_getAssociatedObject(self, @selector(onChange));
}
@end

@implementation JSMapControl
//注册为Native模块
//RCT_EXPORT_MODULE(RCTMapView)
RCT_EXPORT_MODULE()
RCT_EXPORT_VIEW_PROPERTY(onChange, RCTBubblingEventBlock)

- (instancetype)init
{
  self = [super init];
  if (self) {
  }
  return self;
}
- (UIView *)view
{
  MapControl* mapControl = [[MapControl alloc]init];
  NSLog(@"%@",NSHomeDirectory());
//  [mapControl mapControlInit];
  mapControl.jsMapControl = self;
  [JSObjManager addObj:mapControl];

  return mapControl;
}
////////////////////////
RCT_REMAP_METHOD(getMap,mapControlId:(NSString*)Id resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  MapControl* mapControl = [JSObjManager getObjWithKey:Id];
  Map* map = mapControl.map;
  if(map){
    NSInteger key = (NSInteger)map;
    [JSObjManager addObj:map];
    resolve(@{@"mapId":@(key).stringValue});
  }else{
    reject(@"mapControl",@"get map failed!!!",nil);
  }
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

//RCT_CUSTOM_VIEW_PROPERTY(onChange, RCTBubblingEventBlock, RCTMapView)
@end
