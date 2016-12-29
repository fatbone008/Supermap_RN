//
//  JSMaps.m
//  rnTest
//
//  Created by imobile-xzy on 16/7/5.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSMaps.h"
#import "SuperMap/Maps.h"
#import "JSObjManager.h"

@implementation JSMaps
//注册为Native模块
RCT_EXPORT_MODULE();

RCT_REMAP_METHOD(get,getNameKey:(NSString*)key  index:(int)index resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  
  Maps* maps = [JSObjManager getObjWithKey:key];
  if(maps){
    NSString* mapName = [maps get:index];
    resolve(@{@"mapName":mapName});
  }else
    reject(@"Maps",@"get:Maps not exeist!!!",nil);
}

@end
