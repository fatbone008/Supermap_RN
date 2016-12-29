//
//  JSServiceQueryParameter.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/24.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSServiceQueryParameter.h"
#import "SuperMap/ServiceQueryParameter.h"
#import "SuperMap/Rectangle2D.h"
#import "JSObjManager.h"
@implementation JSServiceQueryParameter
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  ServiceQueryParameter* para = [[ServiceQueryParameter alloc]init];
  if(para){
    NSInteger key = (NSInteger)para;
    [JSObjManager addObj:para];
    resolve(@{@"_serviceQueryParameterId_":@(key).stringValue});
  }else{
    reject(@"serviceQueryParameter",@"create serviceQueryParameter failed!!!",nil);
  }
}

//RCT_REMAP_METHOD(setQueryBounds,setQueryBoundsById:(NSString*)paraId rectangle2DId:(NSString*)rectangle2DId resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
//  ServiceQueryParameter* para = [JSObjManager getObjWithKey:paraId];
//  Rectangle2D* rectangle = [JSObjManager getObjWithKey:rectangle2DId];
//  
//  Feature* feature = [[Feature alloc]initWithFeature:fieldNames fieldValue:fieldValues geometry:geo];
//  if(feature){
//    NSInteger key = (NSInteger)feature;
//    [JSObjManager addObj:feature];
//    resolve(@{@"_featureId_":@(key).stringValue});
//  }else{
//    reject(@"feature",@"create feature failed!!!",nil);
//  }
//}


@end
