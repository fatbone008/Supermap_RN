//
//  JSLayerSetting.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/22.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSLayerSetting.h"
#import "SuperMap/LayerSetting.h"
#import "SuperMap/LayerSettingVector.h"
//#import "SuperMap/LayerSettingGrid.h"
//#import "SuperMap/LayerSettingImage.h"
#import "JSObjManager.h"
@implementation JSLayerSetting
RCT_EXPORT_MODULE();
//RCT_REMAP_METHOD(getType,getTypeByKey:(NSString*)key resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  id layerSetting = [JSObjManager getObjWithKey:key];
//  if ([layerSetting isKindOfClass:[LayerSettingVector class]]) {
//    resolve(@"VECTOR");
//  }else if([layerSetting isKindOfClass:[LayerSettingGrid class]]){
//    resolve(@"GRID");
//  }else if([layerSetting isKindOfClass:[LayerSettingImage class]]){
//    resolve(@"RASTER");
//  }else{
//    reject(@"LayerSetting",@"get type failed!!!",nil);
//  }
//}
@end
