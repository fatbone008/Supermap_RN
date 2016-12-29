//
//  JSRectangle2D.m
//  HelloWorldDemo
//
//  Created by 王子豪 on 2016/11/24.
//  Copyright © 2016年 Facebook. All rights reserved.
//

#import "JSRectangle2D.h"
#import "SuperMap/Rectangle2D.h"
#import "JSObjManager.h"
@implementation JSRectangle2D
RCT_EXPORT_MODULE();
RCT_REMAP_METHOD(createObjBy2Pt,createObjBy2Pt:(NSString*)point2DId1 point2:(NSString*)point2DID2 resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
  Point2D* point1 = [JSObjManager getObjWithKey:point2DId1];
  Point2D* point2 = [JSObjManager getObjWithKey:point2DID2];
  if (point1&&point2) {
    Rectangle2D* rectangle = [[Rectangle2D alloc]initWithLeftBottom:point1 RightTop:point2];
    NSInteger key = (NSInteger)rectangle;
    [JSObjManager addObj:rectangle];
    resolve(@{@"rectangle2DId":@(key).stringValue});
  }else{
    reject(@"rectangle2D",@"create failed!!!",nil);
  }
}

RCT_REMAP_METHOD(createObj,resolver:(RCTPromiseResolveBlock)resolve rejecter:(RCTPromiseRejectBlock)reject){
    Rectangle2D* rectangle = [[Rectangle2D alloc]init];
  if (rectangle) {
    NSInteger key = (NSInteger)rectangle;
    [JSObjManager addObj:rectangle];
    resolve(@{@"rectangle2DId":@(key).stringValue});
  }else{
    reject(@"rectangle2D",@"create failed!!!",nil);
  }
}
@end
